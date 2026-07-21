package com.epiis.hire.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.epiis.hire.generic.ResponseGeneric;

public class ResponseCategoryGetAll extends ResponseGeneric {
    public List<ItemCategory> listCategory = new ArrayList<>();

    public static class ItemCategory {
        public String idCategory;
        public String name;
    }
}
