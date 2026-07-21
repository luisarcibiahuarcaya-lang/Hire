package com.epiis.hire.dto.response;

import com.epiis.hire.generic.ResponseGeneric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseJobOfferInsert extends ResponseGeneric {

    private String idJobOffer;
}
