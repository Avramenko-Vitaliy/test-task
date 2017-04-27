package com.vitaliy.avramenko.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "rateNbps")
public class Rate {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long rateId;
    private String currency;
    private String code;
    private BigDecimal rate;
    private Date dateRate;
}
