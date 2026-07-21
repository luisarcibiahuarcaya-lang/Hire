package com.epiis.hire.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epiis.hire.dto.response.ResponseCategoryGetAll;
import com.epiis.hire.entity.EntityCategory;
import com.epiis.hire.repository.RepositoryCategory;

@Service
public class BusinessCategory {

    private final RepositoryCategory repositoryCategory;

    public BusinessCategory(RepositoryCategory repositoryCategory) {
        this.repositoryCategory = repositoryCategory;
    }

    public ResponseCategoryGetAll getAll() {
        ResponseCategoryGetAll response = new ResponseCategoryGetAll();

        List<EntityCategory> listEntity = repositoryCategory.findByActiveTrue();

        for (EntityCategory entity : listEntity) {
            ResponseCategoryGetAll.ItemCategory item = new ResponseCategoryGetAll.ItemCategory();
            item.idCategory = entity.getIdCategory();
            item.name = entity.getName();
            response.listCategory.add(item);
        }

        response.success();

        return response;
    }
}
