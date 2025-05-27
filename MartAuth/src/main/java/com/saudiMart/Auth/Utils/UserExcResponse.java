package com.saudiMart.Auth.Utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserExcResponse {
    private String Message;
    private Integer Status;
    private long TimeStamp;
}