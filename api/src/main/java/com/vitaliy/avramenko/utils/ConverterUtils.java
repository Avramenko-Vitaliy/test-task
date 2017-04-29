package com.vitaliy.avramenko.utils;

import com.vitaliy.avramenko.dto.RateDto;
import com.vitaliy.avramenko.entity.Rate;
import com.vitaliy.avramenko.nbp.dto.ExchangeRatesTable;
import com.vitaliy.avramenko.nbp.dto.RateNbp;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConverterUtils {

    public static List<Rate> convertRateFromThirdPartyToEntity(List<ExchangeRatesTable> ratesTable) {
        return ratesTable.stream()
                .reduce(new ArrayList<>(), ConverterUtils::findAndFillRate, ConverterUtils::concatTwoArray);
    }

    private static List<Rate> findAndFillRate(List<Rate> rates, ExchangeRatesTable table) {
        BigDecimal usd = table.getRateNbps().stream()
                .filter(rate -> rate.getCode().equals("USD"))
                .findFirst()
                .map(RateNbp::getMid)
                .orElse(null);
        Rate rate = new Rate();
        rate.setRate(usd);
        rate.setDateRate(DateUtils.truncate(table.getEffectiveDate(), Calendar.DAY_OF_MONTH));
        rates.add(rate);
        return rates;
    }

    public static <T> List<T> concatTwoArray(List<T> list1, List<T> list2) {
        ArrayList<T> list = new ArrayList<>();
        list.addAll(list1);
        list.addAll(list2);
        return list;
    }

    public static Date setEndOfDay(Date date) {
        Date tmp = DateUtils.addDays(date, 1);
        return DateUtils.addSeconds(tmp, -1);
    }

    public static RateDto convertRateEntityToDto(Rate rate) {
        RateDto rateDto = new RateDto();
        rateDto.setRateId(rate.getRateId());
        rateDto.setDateRate(rate.getDateRate());
        rateDto.setRate(rate.getRate());
        return rateDto;
    }
}
