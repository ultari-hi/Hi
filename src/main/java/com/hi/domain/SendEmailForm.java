package com.hi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 로직으로 메세지를 작성해놓으면 메세지가 바뀌어야 할 경우 재배포를 해야한다.
// DB에 저장해놓으면 재배포를 할 필요가 없기 때문에 DB에 저장
public class SendEmailForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "send_email_form_id", columnDefinition = "int", nullable = false)
    private int id;

    @Column(name = "sender", columnDefinition = "varchar(50)")
    private String sender;

    @Column(name = "sender_custom", columnDefinition = "varchar(255)")
    private String senderCustom;

    @Column(name = "title", columnDefinition = "varchar(255)")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    public String getNewContent(String checkNum){
        return content.replace("checkNum", checkNum);
    }
}
