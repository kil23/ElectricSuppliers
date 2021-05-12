package com.electric.suppliers.dao;

import com.electric.suppliers.pojo.Suppliers;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SuppliersDao extends AbstractDao<Suppliers> {

    @Autowired
    SessionFactory sessionFactory;

    public SuppliersDao() { }

    public Suppliers getSupplierById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Suppliers> criteria = builder.createQuery(Suppliers.class);
        Root<Suppliers> root = criteria.from(Suppliers.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("id"),id));
        criteria.select(root).where(predicates.toArray(new Predicate[] {}));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Suppliers> getSuppliersById(List<Long> ids) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Suppliers> criteria = builder.createQuery(Suppliers.class);
        Root<Suppliers> root = criteria.from(Suppliers.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.in(root.get("id")).value(ids.get(0)).value(ids.get(1)).value(ids.get(2)));
        criteria.select(root).where(predicates.toArray(new Predicate[] {}));
        return em.createQuery(criteria).getResultList();
    }

    public List< Suppliers > getSuppliers(String zipCode) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Suppliers> criteria = builder.createQuery(Suppliers.class);
        Root<Suppliers> root = criteria.from(Suppliers.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("zipCode"), zipCode));
        criteria.select(root).where(predicates.toArray(new Predicate[] {}));
        return em.createQuery(criteria).getResultList();
    }

    public List< Suppliers > getSuppliersWithZipCodeAndDuration(String zipCode, Long duration) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Suppliers> criteria = builder.createQuery(Suppliers.class);
        Root<Suppliers> root = criteria.from(Suppliers.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("zipCode"), zipCode));
        predicates.add(builder.lessThanOrEqualTo(root.get("duration"), duration));
        criteria.select(root).where(predicates.toArray(new Predicate[] {}));
        return em.createQuery(criteria).getResultList();
    }

    public List<Suppliers> getSuppliersWithZipCodeAndDurationAndContent(String zipCode, Long duration, Double renewableContent) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Suppliers> criteria = builder.createQuery(Suppliers.class);
        Root<Suppliers> root = criteria.from(Suppliers.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("zipCode"), zipCode));
        predicates.add(builder.lessThanOrEqualTo(root.get("duration"), duration));
        predicates.add(builder.equal(root.get("renewableContent"), renewableContent));
        criteria.select(root).where(predicates.toArray(new Predicate[] {}));
        return em.createQuery(criteria).getResultList();
    }
}
