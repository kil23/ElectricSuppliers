package com.electric.suppliers.dto.helper;

import com.electric.suppliers.model.form.InsertDataForm;
import com.electric.suppliers.pojo.Suppliers;
import com.google.common.base.CharMatcher;

import java.util.ArrayList;
import java.util.List;

public class SuppliersHelper {

    public static Suppliers convertToSuppliers(String suppliersName, Double rate, Long duration, String planName, Double content,
                                  Double cancellationFee, Double monthlyAmt, String zipCode, String utilityName) {
        Suppliers suppliers = new Suppliers();
        suppliers.setSupplierName(suppliersName);
        suppliers.setRate(rate);
        suppliers.setDuration(duration);
        suppliers.setPlanName(planName);
        suppliers.setRenewableContent(content);
        suppliers.setCancellationFee(cancellationFee);
        suppliers.setMonthlyPrice(monthlyAmt);
        suppliers.setZipCode(zipCode);
        suppliers.setUtilityName(utilityName);
        return suppliers;
    }

    public static List<Suppliers> convertToSuppliersList(List<InsertDataForm> insertDataFormList) {
        List<Suppliers> suppliersList = new ArrayList<>();
        for(InsertDataForm dataForm : insertDataFormList) {
            Suppliers suppliers = new Suppliers();
            suppliers.setSupplierName(dataForm.getSupplierName());
            suppliers.setMonthlyPrice(Double.parseDouble(dataForm.getEstimatedMonthlyCost().trim().substring(1)));
            suppliers.setDuration(Long.parseLong(CharMatcher.inRange('0','9').retainFrom(dataForm.getContractTerm())));
            suppliers.setCancellationFee(checkValue(dataForm.getCancellationFee(), suppliers.getDuration()));
            suppliers.setPlanName("abc");
            suppliers.setRenewableContent(getContent(dataForm.getRenewableEnergy()));
            suppliers.setRate(Double.parseDouble(dataForm.getPrice().substring(0,5).trim()));
            suppliers.setZipCode("02451");
            suppliersList.add(suppliers);
        }
        return suppliersList;
    }

    private static Double getContent(String renewableEnergy) {
        if(renewableEnergy.isEmpty()) {
            return 49.00;
        }
        return Double.parseDouble(renewableEnergy.trim().substring(0,3));
    }

    private static Double checkValue(String cancellationFee, Long duration) {
        if(cancellationFee.isEmpty()) {
            return 0.00;
        }
        Double fee = Double.parseDouble(cancellationFee.trim().substring(1,5));
        if(cancellationFee.trim().contains("remaining")) {
            return fee * duration;
        }
        return fee;
    }
}
