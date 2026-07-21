package com.epiis.hire.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.epiis.hire.generic.ResponseGeneric;

public class ResponseApplicationGetAll extends ResponseGeneric {
    public List<ItemApplication> listApplication = new ArrayList<>();

    public static class ItemApplication {
        public String idApplication;
        public String idJobOffer;
        public String jobTitle;
        public String companyName;
        public String candidateName;
        public String candidateEmail;
        public String status;
        public String appliedAt;
    }
}
