package com.vitaliy.avramenko.service;

import com.vitaliy.avramenko.dto.RateDto;
import com.vitaliy.avramenko.nbp.dto.RequestParams;
import com.vitaliy.avramenko.repository.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatesService {

    @Autowired
    private RatesRepository ratesRepository;

    public List<RateDto> getRates(RequestParams params) {
        return ratesRepository.findByCodeAndDateRateBetweenOrderByDateRateDesc("EUR", params.getStartDate(), params.getEndDate()).stream()
                .map(ConverterService::converRateEntityToDto)
                .collect(Collectors.toList());
    }
}
