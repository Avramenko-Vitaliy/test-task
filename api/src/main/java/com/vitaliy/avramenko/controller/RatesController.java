package com.vitaliy.avramenko.controller;

import com.vitaliy.avramenko.dto.RateDto;
import com.vitaliy.avramenko.nbp.dto.RequestParams;
import com.vitaliy.avramenko.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/rates", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatesController {

    @Autowired
    private RatesService ratesService;

    @RequestMapping(method = RequestMethod.GET)
    public List<RateDto> getRates(@ModelAttribute RequestParams params) {
        return ratesService.getRates(params);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/excel")
    public void getRates() {

    }
}
