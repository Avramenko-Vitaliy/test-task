package com.vitaliy.avramenko.nbp;

import com.vitaliy.avramenko.entity.Rate;
import com.vitaliy.avramenko.nbp.dto.ExchangeRatesTable;
import com.vitaliy.avramenko.repository.RatesRepository;
import com.vitaliy.avramenko.service.ConverterService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReadRatesService {
    private static final Log LOG = LogFactory.getLog(ReadRatesService.class);

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${exchange.rates.endpoint}")
    private String ratesEndpoint;

    @Autowired
    private RatesRepository ratesRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    private void init() {
        if (ratesRepository.findAll().isEmpty()) {
            List<Rate> rates = requestRates(DateUtils.addDays(new Date(), -93), DateUtils.addDays(new Date(), -1));
            ratesRepository.save(rates);
        }
    }

    private List<Rate> requestRates(Date startDate, Date endDate) {
        Map<String, String> params = new HashMap<>();
        params.put("startDate", dateFormat.format(startDate));
        params.put("endDate", dateFormat.format(endDate));

        ResponseEntity<ArrayList<ExchangeRatesTable>> response = restTemplate.exchange(
                ratesEndpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<ExchangeRatesTable>>(){},
                params
        );
        return response.getBody().stream()
                .flatMap(item -> ConverterService.converRateFromThirdPartyToEntity(item).stream())
                .collect(Collectors.toList());
    }

    @Scheduled(fixedDelay = DateUtils.MILLIS_PER_HOUR)
    private void readRates() {
        List<Rate> rates = requestRates(DateUtils.addDays(new Date(), -1), DateUtils.addDays(new Date(), -1));
        List<Rate> batch = new ArrayList<>();
        rates.parallelStream().forEach(item -> {
            Rate rate = ratesRepository.findByCodeAndDateRate(item.getCode(), item.getDateRate());
            if (null != rate) {
                if (rate.getRate().compareTo(item.getRate()) != 0) {
                    rate.setRate(item.getRate());
                    batch.add(rate);
                    LOG.info(String.format("Rate of currency %s on date %s will update", rate.getCode(), rate.getDateRate()));
                }
            } else {
                batch.add(item);
                LOG.info(String.format("Rate of currency %s on date %s will add", item.getCode(), item.getDateRate()));
            }
        });
        ratesRepository.save(batch);
    }
}
