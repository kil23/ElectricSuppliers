package com.electric.suppliers.controller;

import com.electric.suppliers.dto.SuppliersDto;
import com.electric.suppliers.model.form.PlanForm;
import com.electric.suppliers.pojo.Suppliers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class GetController {

    @Autowired
    private SuppliersDto dto;

    @RequestMapping(value = "/cheapest-plan", method = RequestMethod.POST)
    public Suppliers getCheapestPlans(@RequestBody PlanForm form) {
        return dto.getCheapestPlan(form.getZipCode(), form.getDuration());
    }

    @RequestMapping(value = "/top-three-cheaper-plan", method = RequestMethod.POST)
    public List<Suppliers> getTop3CheaperPlans(@RequestBody PlanForm form) {
        return dto.getTopThreeCheaperPlans(form.getZipCode(), form.getDuration());
    }

    @RequestMapping(value = "/greenest-plan", method = RequestMethod.POST)
    public Suppliers getGreenestPlans(@RequestBody PlanForm form) {
        return dto.getGreenestPlan(form.getZipCode(), form.getDuration());
    }

    @RequestMapping(value = "/top-three-greener-plan", method = RequestMethod.POST)
    public List<Suppliers> getTop3GreenerPlans(@RequestBody PlanForm form) {
        return dto.getTopThreeGreenerPlan(form.getZipCode(), form.getDuration());
    }
}
