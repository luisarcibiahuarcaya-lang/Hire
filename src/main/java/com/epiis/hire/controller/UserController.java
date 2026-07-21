package com.epiis.hire.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.hire.business.BusinessUser;
import com.epiis.hire.dto.request.RequestUserLogin;
import com.epiis.hire.dto.request.RequestUserRegister;
import com.epiis.hire.dto.response.ResponseUserLogin;
import com.epiis.hire.dto.response.ResponseUserRegister;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "user")
@Tag(name = "Usuario", description = "Endpoints para candidatos y autenticación")
public class UserController {

    private final BusinessUser businessUser;

    public UserController(BusinessUser businessUser) {
        this.businessUser = businessUser;
    }

    @PostMapping(path = "register")
    @Operation(summary = "Registrar candidato", description = "Crea una cuenta de tipo candidato")
    public ResponseEntity<ResponseUserRegister> actionRegister(
            @Valid @RequestBody RequestUserRegister request,
            BindingResult bindingResult) {

        try {
            ResponseUserRegister response;

            if (bindingResult.hasErrors()) {
                response = new ResponseUserRegister();
                bindingResult.getAllErrors().forEach(error -> response.listMessage.add(error.getDefaultMessage()));
                return ResponseEntity.ok(response);
            }

            response = businessUser.register(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(path = "login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario (candidato, empresa o admin) con email y contraseña")
    public ResponseEntity<ResponseUserLogin> actionLogin(
            @Valid @RequestBody RequestUserLogin request,
            BindingResult bindingResult) {

        try {
            ResponseUserLogin response;

            if (bindingResult.hasErrors()) {
                response = new ResponseUserLogin();
                bindingResult.getAllErrors().forEach(error -> response.listMessage.add(error.getDefaultMessage()));
                return ResponseEntity.ok(response);
            }

            response = businessUser.login(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
