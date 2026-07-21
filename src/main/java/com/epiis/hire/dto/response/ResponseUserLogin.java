package com.epiis.hire.dto.response;

import com.epiis.hire.generic.ResponseGeneric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserLogin extends ResponseGeneric {

    private String idUser;
    private String firstName;
    private String surName;
    private String email;
    private String role;
    private String idCompany;
    private String token;
}
