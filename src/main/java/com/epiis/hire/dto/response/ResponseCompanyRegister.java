package com.epiis.hire.dto.response;

import com.epiis.hire.generic.ResponseGeneric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCompanyRegister extends ResponseGeneric {

    private String idUser;
    private String idCompany;
    private String email;
}
