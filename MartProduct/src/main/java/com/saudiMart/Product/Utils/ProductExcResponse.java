package com.saudiMart.Product.Utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductExcResponse {
    private String Message;
    private Integer Status;
    private long TimeStamp;
}