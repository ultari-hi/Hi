package com.hi.dto;

import lombok.Data;

@Data
public class IamPortWebhookDto {
    private String impUid;
    private String merchantUid;
    private String state;

    public IamPortWebhookDto(String impUid, String merchantUid, String state) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.state = state;
    }
}
