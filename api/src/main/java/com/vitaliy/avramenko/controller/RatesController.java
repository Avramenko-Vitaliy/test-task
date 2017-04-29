package com.vitaliy.avramenko.controller;

import com.vitaliy.avramenko.dto.RateDto;
import com.vitaliy.avramenko.nbp.dto.RequestParams;
import com.vitaliy.avramenko.service.RatesService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RatesController {

    @Autowired
    private RatesService ratesService;

    @GetMapping("/rates")
    public List<RateDto> getRates(@ModelAttribute RequestParams params) {
        return ratesService.getRates(params);
    }
}
