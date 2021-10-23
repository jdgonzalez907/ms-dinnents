package com.jdgonzalez907.msdinnents.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tables")
public class TableController {
    @GetMapping(value = "/calculate")
    String calculateTables() {
        return "Hi";
    }
}
