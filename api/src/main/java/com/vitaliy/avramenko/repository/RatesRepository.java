package com.vitaliy.avramenko.repository;

import com.vitaliy.avramenko.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RatesRepository extends JpaRepository<Rate, Long> {

    Rate findByCodeAndDateRate(String code, Date date);

    List<Rate> findByCodeAndDateRateBetweenOrderByDateRateDesc(String code, Date start, Date end);
}
