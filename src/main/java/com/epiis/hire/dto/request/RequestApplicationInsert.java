package com.epiis.hire.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestApplicationInsert {

    @NotBlank(message = "La oferta de trabajo es obligatoria")
    private String idJobOffer;

    @NotBlank(message = "El usuario es obligatorio")
    private String idUser;
}
