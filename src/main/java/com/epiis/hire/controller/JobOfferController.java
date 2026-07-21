package com.epiis.hire.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.hire.business.BusinessJobOffer;
import com.epiis.hire.dto.request.RequestJobOfferInsert;
import com.epiis.hire.dto.response.ResponseJobOfferGetAll;
import com.epiis.hire.dto.response.ResponseJobOfferGetById;
import com.epiis.hire.dto.response.ResponseJobOfferInsert;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "joboffer")
@Tag(name = "Oferta de trabajo", description = "Endpoints para publicar y consultar vacantes")
public class JobOfferController {

    private final BusinessJobOffer businessJobOffer;

    public JobOfferController(BusinessJobOffer businessJobOffer) {
        this.businessJobOffer = businessJobOffer;
    }

    @PostMapping(path = "insert")
    @Operation(summary = "Publicar oferta", description = "Crea una nueva oferta de trabajo para una empresa")
    public ResponseEntity<ResponseJobOfferInsert> actionInsert(
            @Valid @RequestBody RequestJobOfferInsert request,
            BindingResult bindingResult) {

        try {
            ResponseJobOfferInsert response;

            if (bindingResult.hasErrors()) {
                response = new ResponseJobOfferInsert();
                bindingResult.getAllErrors().forEach(error -> response.listMessage.add(error.getDefaultMessage()));
                return ResponseEntity.ok(response);
            }

            response = businessJobOffer.insert(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "getAll")
    @Operation(summary = "Listar ofertas publicadas", description = "Obtiene todas las ofertas activas visibles para candidatos")
    public ResponseEntity<ResponseJobOfferGetAll> actionGetAll() {
        try {
            return ResponseEntity.ok(businessJobOffer.getAllPublished());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "getByCompany/{idCompany}")
    @Operation(summary = "Listar ofertas de una empresa", description = "Obtiene todas las ofertas publicadas por una empresa, en cualquier estado")
    public ResponseEntity<ResponseJobOfferGetAll> actionGetByCompany(@PathVariable String idCompany) {
        try {
            return ResponseEntity.ok(businessJobOffer.getByCompany(idCompany));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "getById/{idJobOffer}")
    @Operation(summary = "Detalle de oferta", description = "Obtiene el detalle completo de una oferta de trabajo")
    public ResponseEntity<ResponseJobOfferGetById> actionGetById(@PathVariable String idJobOffer) {
        try {
            return ResponseEntity.ok(businessJobOffer.getById(idJobOffer));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(path = "updateStatus/{idJobOffer}/{status}")
    @Operation(summary = "Actualizar estado de oferta", description = "Cambia el estado de una oferta: PUBLISHED, PAUSED o CLOSED")
    public ResponseEntity<ResponseJobOfferInsert> actionUpdateStatus(
            @PathVariable String idJobOffer,
            @PathVariable String status) {
        try {
            return ResponseEntity.ok(businessJobOffer.updateStatus(idJobOffer, status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
