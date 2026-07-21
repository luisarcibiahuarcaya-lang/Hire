package com.epiis.hire.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "general")
public class GeneralController {

    @GetMapping(path = "index")
    public ResponseEntity<Map<String, String>> actionIndex() {
        Map<String, String> data = new HashMap<>();
        data.put("welcome", "PrimeHire API funcionando correctamente.");
        return ResponseEntity.ok(data);
    }
}
