package com.electric.suppliers.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"supplierName","rate","duration"})})
public class Suppliers implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String supplierName;

    @NotNull
    @Column(nullable = false)
    private Double rate;

    @NotNull
    @Column(nullable = false)
    private Long duration;

    private String planName;

    @NotNull
    @Column(nullable = false)
    private Double cancellationFee;

    @NotNull
    @Column(nullable = false)
    private Double renewableContent;

    @NotNull
    @Column(nullable = false)
    private Double monthlyPrice;

    private String zipCode;

    private String utilityName;
}
