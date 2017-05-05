package com.vitaliy.avramenko.service;

import com.vitaliy.avramenko.dto.RateDto;
import com.vitaliy.avramenko.entity.Rate;
import com.vitaliy.avramenko.nbp.ReadRatesService;
import com.vitaliy.avramenko.nbp.dto.RequestParams;
import com.vitaliy.avramenko.repository.RatesRepository;
import com.vitaliy.avramenko.utils.ConverterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatesService {
    private static final Log LOG = LogFactory.getLog(RatesService.class);

    @Autowired
    private RatesRepository ratesRepository;

    @Autowired
    private ReadRatesService nbpService;

    public List<RateDto> getRates(RequestParams params) {
        if (params.getStartDate() == null || params.getEndDate() == null) {
            return getRates();
        }
        long startTime = System.currentTimeMillis();
        List<Rate> rates = nbpService.requestRates(params.getStartDate(), params.getEndDate());
        nbpService.saveRates(rates);
        List<RateDto> result = rates.stream()
                .parallel()
                .map(ConverterUtils::convertRateEntityToDto)
                .sorted((o1, o2) -> o2.getDateRate().compareTo(o1.getDateRate()))
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        LOG.info(String.format("Time take to process the API response are %s milliseconds", endTime - startTime));
        return result;
    }

    public List<RateDto> getRates() {
        return ratesRepository.findAll(new Sort(Sort.Direction.DESC, "dateRate")).stream()
                .map(ConverterUtils::convertRateEntityToDto)
                .peek(rate -> LOG.info(String.format("Rate from DB: %s", rate)))
                .collect(Collectors.toList());
    }
}
