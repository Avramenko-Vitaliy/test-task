package com.vitaliy.avramenko.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long rateId;
    private BigDecimal rate;
    private Date dateRate;
}
