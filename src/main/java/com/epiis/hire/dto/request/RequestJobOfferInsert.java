package com.epiis.hire.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestJobOfferInsert {

    @NotBlank(message = "La empresa es obligatoria")
    private String idCompany;

    @NotBlank(message = "La categoría es obligatoria")
    private String idCategory;

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    private BigDecimal salary;

    @NotBlank(message = "La modalidad es obligatoria")
    private String modality;

    @NotBlank(message = "La ubicación es obligatoria")
    private String location;
}
