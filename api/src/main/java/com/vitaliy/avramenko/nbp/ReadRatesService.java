package com.vitaliy.avramenko.nbp;

import com.vitaliy.avramenko.entity.Rate;
import com.vitaliy.avramenko.nbp.dto.ExchangeRatesTable;
import com.vitaliy.avramenko.repository.RatesRepository;
import com.vitaliy.avramenko.utils.ConverterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReadRatesService {
    private static final Log LOG = LogFactory.getLog(ReadRatesService.class);

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${exchange.rates.endpoint}")
    private String ratesEndpoint;

    private final RatesRepository ratesRepository;

    private RestTemplate restTemplate = new RestTemplate();

    public ReadRatesService(@Autowired RatesRepository ratesRepository) {
        this.ratesRepository = ratesRepository;
    }

    public List<Rate> requestRates(Date startDate, Date endDate) {
        LOG.info(String.format("[Request to NBP API] date range: %s and %s", dateFormat.format(startDate), dateFormat.format(endDate)));
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
        return ConverterUtils.convertRateFromThirdPartyToEntity(response.getBody());
    }

    @Async
    public void saveRates(List<Rate> rateList) {
        List<Rate> batch = rateList.stream()
                .parallel()
                .reduce(new ArrayList<>(), this::collectRates, ConverterUtils::concatTwoArray);
        ratesRepository.save(batch);
    }

    private List<Rate> collectRates(List<Rate> rates, Rate rate) {
        Rate entity = ratesRepository.findByDateRate(rate.getDateRate());
        if (Objects.nonNull(entity)) {
            if (entity.getRate().compareTo(rate.getRate()) != 0) {
                entity.setRate(rate.getRate());
                rates.add(entity);
                LOG.info(String.format("Rate on date %s will update", rate.getDateRate()));
            }
        } else {
            rates.add(rate);
            LOG.info(String.format("Rate on date %s will add", rate.getDateRate()));
        }
        return rates;
    }
}
