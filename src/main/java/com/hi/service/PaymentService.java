package com.hi.service;

import com.hi.domain.*;
import com.hi.dto.IamPortWebhookDto;
import com.hi.dto.PaymentReqDto;
import com.hi.dto.PaymentResDto;
import com.hi.enums.Status;
import com.hi.repository.*;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.util.StreamUtils.toUnmodifiableList;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final PointRepository pointRepository;
    private final ReservationDateRepository reservationDateRepository;
    private final TmpDateRepository tmpDateRepository;
    // 토큰
    private final IamportClient iamportClient = new IamportClient("${API_KEY}", "${API_SECRET}");

    //결제, 예약 생성
    public PaymentResDto createPayment(User user, PaymentReqDto dto){
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new NoResultException("객실을 찾을 수 없습니다."));

        Accommodation accommodation = room.getAccommodation();

        Payment newPayment = Payment.newPayment(dto, user);
        Payment payment = paymentRepository.save(newPayment);

        Reservation reservation = Reservation.newReservation(user, accommodation, room, payment, dto);
        reservationRepository.save(reservation);

        List<LocalDate> selectDates = dto.getCheckInDate()
                    .datesUntil(dto.getCheckOutDate())
                    .collect(toUnmodifiableList());

        List<TmpDate> tmpDates = selectDates
                .stream()
                .map(date -> new TmpDate(payment, date))
                .collect(toUnmodifiableList());

        tmpDateRepository.saveAll(tmpDates);

        return new PaymentResDto(payment, room, user);
    }

    /**
     * 결제가 승인되었을 때(모든 결제 수단) - (status : paid)
     * 가상계좌가 발급되었을 때 - (status : ready)
     * 가상계좌에 결제 금액이 입금되었을 때 - (status : paid)
     * 예약결제가 시도되었을 때 - (status : paid or failed)
     * 관리자 콘솔에서 결제 취소되었을 때 - (status : cancelled)
     * hi 서비스에서는 카드 결제만 구현 ( 결제 승인 or 관리자 콘솔에서 결제 취소 )
     */
    public Status verifyPayment(IamPortWebhookDto dto) {
        log.info("start");
        log.info("{}", dto.getMerchantUid());
        String paymentState = dto.getState();
        Status result;

        Payment payment = paymentRepository.findById(Long.valueOf(dto.getMerchantUid()))
                .orElseThrow(() -> new NoResultException("결제 정보를 찾을 수 없습니다."));
        Reservation reservation = payment.getReservation();
        if (paymentState.equals("paid")) {
            try {
                // ImpUid 로 아임포트에 단건 조회 요청
                IamportResponse<com.siot.IamportRestClient.response.Payment> paymentResponse = iamportClient.paymentByImpUid(dto.getImpUid());
                if (Objects.nonNull(paymentResponse.getResponse())) {
                    com.siot.IamportRestClient.response.Payment paymentData = paymentResponse.getResponse();
                    result = payment.paid(paymentData);
                    reservation.status(result);

                    // 포인트 결제 금액이 있을 시
                    if (payment.getPointAmount() > 0) {
                        User user = payment.getUser();
                        Point latestPoint;
                        latestPoint = pointRepository.findLatest(user);

                        //포인트 사용
                        Point usePoint = Point.usePoint(user, payment.getPointAmount(), latestPoint, payment, "숙소 예약");
                        latestPoint = pointRepository.save(usePoint);

                        // 포인트 적립
                        Point savePoint = Point.savePoint(user, payment.getCashAmount(), latestPoint, payment, "숙소 예약");
                        pointRepository.save(savePoint);
                    }
                    List<LocalDate> dates = tmpDateRepository.findAllByPayment(payment);
                    List<ReservationDate> reservationDates = dates
                            .stream()
                            .map(date -> new ReservationDate(reservation, reservation.getRoom(), date))
                            .collect(toUnmodifiableList());
                    reservationDateRepository.saveAll(reservationDates);
                } else {
                    result = payment.cancel();
                    reservation.status(result);
                }
            } catch (IamportResponseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            result = payment.cancel();
            reservation.status(result);
        }
        return result;
    }
}
