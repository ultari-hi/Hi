package com.hi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentReqDto {
    private Integer totalAmount;
    private Integer pointPay;
    private Integer cashPay;
    private String method;
}
