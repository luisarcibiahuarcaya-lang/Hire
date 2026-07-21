package com.epiis.hire.dto.response;

import java.math.BigDecimal;

import com.epiis.hire.generic.ResponseGeneric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseJobOfferGetById extends ResponseGeneric {

    private String idJobOffer;
    private String title;
    private String description;
    private String companyName;
    private String categoryName;
    private BigDecimal salary;
    private String modality;
    private String location;
    private String status;
    private String publishedAt;
}
