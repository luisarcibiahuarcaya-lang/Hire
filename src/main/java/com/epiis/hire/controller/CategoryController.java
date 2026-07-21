package com.epiis.hire.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.hire.business.BusinessCategory;
import com.epiis.hire.dto.response.ResponseCategoryGetAll;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "category")
@Tag(name = "Categoría", description = "Endpoints para categorías de empleo")
public class CategoryController {

    private final BusinessCategory businessCategory;

    public CategoryController(BusinessCategory businessCategory) {
        this.businessCategory = businessCategory;
    }

    @GetMapping(path = "getAll")
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías activas")
    public ResponseEntity<ResponseCategoryGetAll> actionGetAll() {
        try {
            return ResponseEntity.ok(businessCategory.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
