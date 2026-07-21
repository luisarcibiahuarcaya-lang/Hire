package com.epiis.hire.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.hire.business.BusinessCompany;
import com.epiis.hire.dto.request.RequestCompanyRegister;
import com.epiis.hire.dto.response.ResponseCompanyRegister;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "company")
@Tag(name = "Empresa", description = "Endpoints para empresas")
public class CompanyController {

    private final BusinessCompany businessCompany;

    public CompanyController(BusinessCompany businessCompany) {
        this.businessCompany = businessCompany;
    }

    @PostMapping(path = "register")
    @Operation(summary = "Registrar empresa", description = "Crea la cuenta de usuario y el perfil de empresa asociado")
    public ResponseEntity<ResponseCompanyRegister> actionRegister(
            @Valid @RequestBody RequestCompanyRegister request,
            BindingResult bindingResult) {

        try {
            ResponseCompanyRegister response;

            if (bindingResult.hasErrors()) {
                response = new ResponseCompanyRegister();
                bindingResult.getAllErrors().forEach(error -> response.listMessage.add(error.getDefaultMessage()));
                return ResponseEntity.ok(response);
            }

            response = businessCompany.register(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
