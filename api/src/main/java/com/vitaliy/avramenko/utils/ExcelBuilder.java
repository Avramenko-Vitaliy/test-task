package com.vitaliy.avramenko.utils;

import com.vitaliy.avramenko.dto.RateDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelBuilder extends AbstractXlsView {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=exchange_rate.xls");
        List<RateDto> rates = (List<RateDto>) model.get("exchangeRates");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("Exchange Rates");
        sheet.setDefaultColumnWidth(30);

        Row header = sheet.createRow(0);
        Cell headerDate = header.createCell(0);
        headerDate.setCellStyle(cellStyle);
        headerDate.setCellValue("Date");

        Cell headerRate = header.createCell(1);
        headerRate.setCellStyle(cellStyle);
        headerRate.setCellValue("Exchange Rates");

        List<RateDto> filteredRates = rates.stream().
                reduce(new ArrayList<>(), this::filterRates, ConverterUtils::concatTwoArray)
                .stream()
                .filter(rateDto -> rateDto.getRowSpan() != 0)
                .collect(Collectors.toList());

        // create data rows
        final int[] rowCount = {1};
        rates.forEach(rate -> {
            Date dateRate = rate.getDateRate();
            RateDto filteredRate = filteredRates.stream()
                    .filter(rateDto -> rateDto.getDateRate().equals(dateRate))
                    .findFirst()
                    .orElse(null);

            Row row = sheet.createRow(rowCount[0]);
            Cell rowCellDate = row.createCell(0, CellType.STRING);
            rowCellDate.setCellValue(simpleDateFormat.format(dateRate));
            if (Objects.nonNull(filteredRate)) {
                sheet.addMergedRegion(new CellRangeAddress(rowCount[0], rowCount[0] + (filteredRate.getRowSpan()), 1, 1));
            }
            Cell rowCellRate = row.createCell(1, CellType.NUMERIC);
            rowCellRate.setCellValue(rate.getRate().doubleValue());

            rowCellDate.setCellStyle(cellStyle);
            rowCellRate.setCellStyle(cellStyle);
            rowCount[0]++;
        });
    }

    private List<RateDto> filterRates(List<RateDto> result, RateDto current) {
        RateDto rateDto = result.isEmpty() ? null : result.get(result.size() - 1);
        if (Objects.nonNull(rateDto) && (rateDto.getRate().compareTo(current.getRate()) == 0 || Objects.isNull(current.getRate()))) {
            rateDto.setRowSpan(rateDto.getRowSpan() + 1);
        } else {
            RateDto newRate = new RateDto();
            newRate.setDateRate(current.getDateRate());
            newRate.setRate(current.getRate());
            result.add(newRate);
        }
        return result;
    }
}
