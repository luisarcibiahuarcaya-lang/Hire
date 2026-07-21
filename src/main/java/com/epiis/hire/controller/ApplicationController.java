package com.epiis.hire.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.hire.business.BusinessApplication;
import com.epiis.hire.dto.request.RequestApplicationInsert;
import com.epiis.hire.dto.response.ResponseApplicationGetAll;
import com.epiis.hire.dto.response.ResponseApplicationInsert;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "application")
@Tag(name = "Postulación", description = "Endpoints para postulaciones a ofertas de trabajo")
public class ApplicationController {

    private final BusinessApplication businessApplication;

    public ApplicationController(BusinessApplication businessApplication) {
        this.businessApplication = businessApplication;
    }

    @PostMapping(path = "insert")
    @Operation(summary = "Postularse a una oferta", description = "Registra la postulación de un candidato a una oferta de trabajo")
    public ResponseEntity<ResponseApplicationInsert> actionInsert(
            @Valid @RequestBody RequestApplicationInsert request,
            BindingResult bindingResult) {

        try {
            ResponseApplicationInsert response;

            if (bindingResult.hasErrors()) {
                response = new ResponseApplicationInsert();
                bindingResult.getAllErrors().forEach(error -> response.listMessage.add(error.getDefaultMessage()));
                return ResponseEntity.ok(response);
            }

            response = businessApplication.insert(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "getByUser/{idUser}")
    @Operation(summary = "Mis postulaciones", description = "Lista las postulaciones realizadas por un candidato")
    public ResponseEntity<ResponseApplicationGetAll> actionGetByUser(@PathVariable String idUser) {
        try {
            return ResponseEntity.ok(businessApplication.getByUser(idUser));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "getByJobOffer/{idJobOffer}")
    @Operation(summary = "Postulantes de una oferta", description = "Lista los candidatos que postularon a una oferta, para el panel de la empresa")
    public ResponseEntity<ResponseApplicationGetAll> actionGetByJobOffer(@PathVariable String idJobOffer) {
        try {
            return ResponseEntity.ok(businessApplication.getByJobOffer(idJobOffer));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(path = "updateStatus/{idApplication}/{status}")
    @Operation(summary = "Actualizar estado de postulación", description = "Cambia el estado: PENDING, INTERVIEW, HIRED o REJECTED")
    public ResponseEntity<ResponseApplicationInsert> actionUpdateStatus(
            @PathVariable String idApplication,
            @PathVariable String status) {
        try {
            return ResponseEntity.ok(businessApplication.updateStatus(idApplication, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
