package com.electric.suppliers.controller;

import com.electric.suppliers.dto.SuppliersDto;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scraper {

    private WebDriver driver;
    private final List<String> zipCodeList = new ArrayList<>(Arrays.asList("02451", "02452", "02453", "02493", "10001", "10002", "10003"));

    @Autowired
    private SuppliersDto dto;

    public void website1() {
        driver = dto.setup();
        for(String zipCode : zipCodeList) {
            dto.electricityRatesWebsite(zipCode);
        }
        driver.close();
    }

    public void website2() {
        driver = dto.setup();
        for(String zipCode : zipCodeList) {
            dto.energySwitchMAWebsite(zipCode, driver);
        }
        driver.close();
    }

    public static void main(String[] args) {
        Scraper scraper = new Scraper();
        scraper.website1();
        scraper.website2();
    }
}
