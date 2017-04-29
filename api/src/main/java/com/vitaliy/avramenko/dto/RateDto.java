package com.vitaliy.avramenko.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RateDto {
    private Long rateId;
    private BigDecimal rate;
    private Date dateRate;

    @JsonIgnore
    private int rowSpan;
}
