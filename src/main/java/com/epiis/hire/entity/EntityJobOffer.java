package com.epiis.hire.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tjoboffer")
@Setter
@Getter
public class EntityJobOffer {

    @Id
    @Column(name = "idJobOffer")
    private String idJobOffer;

    @Column(name = "idCompany")
    private String idCompany;

    @Column(name = "idCategory")
    private String idCategory;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "modality")
    private String modality;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    @Column(name = "publishedAt")
    private Date publishedAt;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCompany", insertable = false, updatable = false)
    private EntityCompany parentCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategory", insertable = false, updatable = false)
    private EntityCategory parentCategory;
}
