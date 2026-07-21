package com.epiis.hire.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.epiis.hire.generic.ResponseGeneric;

public class ResponseJobOfferGetAll extends ResponseGeneric {
    public List<ItemJobOffer> listJobOffer = new ArrayList<>();

    public static class ItemJobOffer {
        public String idJobOffer;
        public String title;
        public String companyName;
        public String categoryName;
        public BigDecimal salary;
        public String modality;
        public String location;
        public String status;
        public String publishedAt;
    }
}
