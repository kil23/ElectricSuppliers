package com.electric.suppliers.api;

import com.electric.suppliers.dao.SuppliersDao;
import com.electric.suppliers.pojo.Suppliers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SuppliersApi {

    @Autowired
    private SuppliersDao dao;

    @Transactional(rollbackFor = Exception.class)
    public void addSuppliers(Suppliers suppliers) {
        dao.persist(suppliers);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addSuppliersInBulk(List<Suppliers> suppliersList) {
        for(Suppliers suppliers : suppliersList) {
            addSuppliers(suppliers);
        }
    }

    @Transactional(readOnly = true)
    public List<Suppliers> getAllSuppliersWithZipCode(String zipCode) {
        return dao.getSuppliers(zipCode);
    }

    @Transactional(readOnly = true)
    public List<Suppliers> getSuppliersWithZipCodeAndDuration(String zipCode, Long duration) {
        return dao.getSuppliersWithZipCodeAndDuration(zipCode, duration);
    }

    @Transactional(readOnly = true)
    public List<Suppliers> getSuppliersWithZipCodeAndDurationAndContent(String zipCode, Long duration, Double renewableContent) {
        return dao.getSuppliersWithZipCodeAndDurationAndContent(zipCode, duration, renewableContent);
    }

    @Transactional(readOnly = true)
    public Suppliers getSupplierById(Long id) {
        return dao.getSupplierById(id);
    }

    @Transactional(readOnly = true)
    public List<Suppliers> getSuppliersById(List<Long> ids) {
        return dao.getSuppliersById(ids);
    }
}
