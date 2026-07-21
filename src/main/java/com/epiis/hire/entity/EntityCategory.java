package com.epiis.hire.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tcategory")
@Setter
@Getter
public class EntityCategory {

    @Id
    @Column(name = "idCategory")
    private String idCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private Boolean active;
}
