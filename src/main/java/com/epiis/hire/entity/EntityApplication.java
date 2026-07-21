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
@Table(name = "tapplication")
@Setter
@Getter
public class EntityApplication {

    @Id
    @Column(name = "idApplication")
    private String idApplication;

    @Column(name = "idJobOffer")
    private String idJobOffer;

    @Column(name = "idUser")
    private String idUser;

    @Column(name = "status")
    private String status;

    @Column(name = "appliedAt")
    private Date appliedAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idJobOffer", insertable = false, updatable = false)
    private EntityJobOffer parentJobOffer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", insertable = false, updatable = false)
    private EntityUser parentUser;
}
