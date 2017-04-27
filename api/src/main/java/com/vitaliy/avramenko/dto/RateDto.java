package com.vitaliy.avramenko.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RateDto {
    private Long rateId;
    private String currency;
    private String code;
    private BigDecimal rate;
    private Date dateRate;
}
