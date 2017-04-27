package com.vitaliy.avramenko.service;

import com.vitaliy.avramenko.dto.RateDto;
import com.vitaliy.avramenko.entity.Rate;
import com.vitaliy.avramenko.nbp.dto.ExchangeRatesTable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConverterService {

    public static List<Rate> converRateFromThirdPartyToEntity(ExchangeRatesTable rateTable) {
        return rateTable.getRateNbps().stream()
                .map(rateItem -> {
                    Rate rate = new Rate();
                    rate.setDateRate(rateTable.getEffectiveDate());
                    rate.setCode(rateItem.getCode());
                    rate.setCurrency(rateItem.getCurrency());
                    rate.setRate(rateItem.getMid());
                    return rate;
                }).collect(Collectors.toList());
    }

    public static RateDto converRateEntityToDto(Rate rate) {
        RateDto rateDto = new RateDto();
        rateDto.setCode(rate.getCode());
        rateDto.setCurrency(rate.getCurrency());
        rateDto.setDateRate(rate.getDateRate());
        rateDto.setRate(rate.getRate());
        return rateDto;
    }
}
