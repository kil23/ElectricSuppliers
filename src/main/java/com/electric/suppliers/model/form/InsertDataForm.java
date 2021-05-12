package com.electric.suppliers.model.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertDataForm {
    public InsertDataForm(String supplierName, String pricingStructure, String pricePerMonth,
                          String price, String introductoryPrice, String enrollmentFee, String contractTerm,
                          String cancellationFee, String automaticRenewal, String renewableEnergy,
                          String newRegionalResources, String additionalProduct, String estimatedMonthlyCost) {
        this.supplierName = supplierName;
        this.pricingStructure = pricingStructure;
        this.pricePerMonth = pricePerMonth;
        this.price = price;
        this.introductoryPrice = introductoryPrice;
        this.enrollmentFee = enrollmentFee;
        this.contractTerm = contractTerm;
        this.cancellationFee = cancellationFee;
        this.automaticRenewal = automaticRenewal;
        this.renewableEnergy = renewableEnergy;
        this.newRegionalResources = newRegionalResources;
        this.additionalProduct = additionalProduct;
        this.estimatedMonthlyCost = estimatedMonthlyCost;
    }

    private String supplierName;
    private String pricingStructure;
    private String pricePerMonth;
    private String price;
    private String introductoryPrice;
    private String enrollmentFee;
    private String contractTerm;
    private String cancellationFee;
    private String automaticRenewal;
    private String renewableEnergy;
    private String newRegionalResources;
    private String additionalProduct;
    private String estimatedMonthlyCost;
}
