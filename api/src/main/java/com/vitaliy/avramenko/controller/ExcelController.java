package com.vitaliy.avramenko.controller;

import com.vitaliy.avramenko.dto.RateDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/vnd.ms-excel")
public class ExcelController {
    private static final Log LOG = LogFactory.getLog(ExcelController.class);

    @PostMapping("/rates/excel")
    @CrossOrigin("http://localhost:8090")
    public ModelAndView downloadExcel(@RequestBody List<RateDto> rates) {
        LOG.info(String.format("Data have been sent for creating Excel file: %s", rates));
        return new ModelAndView("excelView", "exchangeRates", rates);
    }
}
