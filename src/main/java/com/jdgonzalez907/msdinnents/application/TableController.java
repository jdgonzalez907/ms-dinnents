package com.jdgonzalez907.msdinnents.application;

import com.jdgonzalez907.msdinnents.domain.TableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tables")
public class TableController {

    private TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping(value = "/distribute")
    Mono<TableResponse> calculateTables() {
        return this.tableService
                .distribute(new TableRequest("General",
                        Map.ofEntries(
                                //Map.entry(TableRequest.Filter.TC, "1"),
                                //Map.entry(TableRequest.Filter.UG, "3"),
                                //Map.entry(TableRequest.Filter.RI, "100000"),
                                //Map.entry(TableRequest.Filter.RF, "1000000")
                        )
                ));
    }
}
