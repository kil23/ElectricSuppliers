package com.electric.suppliers.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;

public abstract class AbstractDao<T extends Serializable > {

    private Class< T > clazz;

    @PersistenceContext
    EntityManager em;

    public void setClazz( Class< T > clazzToSet ) {
        this.clazz = clazzToSet;
    }

    protected EntityManager em() {
        return this.em;
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return this.em.getCriteriaBuilder();
    }

    @Transactional
    protected TypedQuery<T> createQuery(CriteriaQuery<T> cq) {
        return this.em.createQuery(cq);
    }

    @Transactional(
            readOnly = true
    )
    protected TypedQuery<T> getSelectQuery() {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(this.clazz);
        Root<T> r = cq.from(this.clazz);
        cq.select(r);
        return this.createQuery(cq);
    }

    @Transactional
    public Query createSqlQuery(String q) {
        return this.em.createNativeQuery(q);
    }

    public Query createSqlQueryForEntity(String q) {
        return this.em.createNativeQuery(q, this.clazz);
    }

    @Transactional(
            readOnly = true
    )
    public Serializable getIdentifier(T obj) {
        return (Serializable)this.em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj);
    }

    @Transactional(
            propagation = Propagation.MANDATORY
    )
    public void clear() {
        this.em.clear();
    }

    @Transactional(
            propagation = Propagation.MANDATORY
    )
    public void flush() {
        this.em.flush();
    }

    @Transactional(
            propagation = Propagation.MANDATORY
    )
    public void flushAndClear() {
        this.flush();
        this.clear();
    }

    @Transactional
    public void persist(T entity) {
        this.em.persist(entity);
    }

    @Transactional
    public void update(T entity) {
    }

    @Transactional(
            readOnly = true
    )
    private TypedQuery<T> getEntitySelectCriteriaQuery() {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(this.clazz);
        Root<T> from = cq.from(this.clazz);
        cq.select(from);
        TypedQuery<T> tq = this.em.createQuery(cq);
        return tq;
    }
}
