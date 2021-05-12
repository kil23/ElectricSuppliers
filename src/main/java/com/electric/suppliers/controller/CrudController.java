package com.electric.suppliers.controller;

import com.electric.suppliers.dto.SuppliersDto;
import com.electric.suppliers.pojo.Suppliers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrudController {

    @Autowired
    private SuppliersDto dto;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insertCSVData() {
        dto.addCsvData("suppliers.csv");
    }

    @RequestMapping(value = "/{zipCode}", method = RequestMethod.GET)
    public List<Suppliers> getSuppliers(@PathVariable String zipCode) {
        return dto.getSuppliers(zipCode);
    }
}
