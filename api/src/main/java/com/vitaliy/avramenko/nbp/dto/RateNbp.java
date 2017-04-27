package com.vitaliy.avramenko.nbp.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JacksonXmlRootElement(localName = "Rate")
public class RateNbp {
    @JacksonXmlProperty(localName = "Currency")
    private String currency;
    @JacksonXmlProperty(localName = "Code")
    private String code;
    @JacksonXmlProperty(localName = "Mid")
    private BigDecimal mid;
}
