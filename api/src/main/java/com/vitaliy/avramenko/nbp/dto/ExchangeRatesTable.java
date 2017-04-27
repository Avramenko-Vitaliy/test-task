package com.vitaliy.avramenko.nbp.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "ExchangeRatesTable")
public class ExchangeRatesTable {
    @JacksonXmlProperty(localName = "EffectiveDate")
    private Date effectiveDate;
    @JacksonXmlProperty(localName = "Rates")
    private List<RateNbp> rateNbps;
}
