package com.epiis.hire.entity;

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
@Table(name = "tcompany")
@Setter
@Getter
public class EntityCompany {

    @Id
    @Column(name = "idCompany")
    private String idCompany;

    @Column(name = "idUser")
    private String idUser;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "sector")
    private String sector;

    @Column(name = "location")
    private String location;

    @Column(name = "website")
    private String website;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", insertable = false, updatable = false)
    private EntityUser parentUser;
}
