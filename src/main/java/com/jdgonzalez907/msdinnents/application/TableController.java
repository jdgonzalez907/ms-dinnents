package com.jdgonzalez907.msdinnents.application;

import com.jdgonzalez907.msdinnents.domain.TableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tables")
public class TableController {

    private TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping(value = "/distribute")
    Flux<String> calculateTables() {
        return this.tableService
                .distribute(
                        List.of(
                                new TableRequest("General",
                                        Map.ofEntries(
                                                Map.entry(TableRequest.Filter.TC, "1")
                                                //Map.entry(TableRequest.Filter.UG, "2"),
                                                //Map.entry(TableRequest.Filter.RI, "500000")
                                                //Map.entry(TableRequest.Filter.RF, "1000000")
                                        )
                                ),
                                new TableRequest("Mesa 2",
                                        Map.ofEntries(
                                                //Map.entry(TableRequest.Filter.TC, "1"),
                                                Map.entry(TableRequest.Filter.UG, "1"),
                                                //Map.entry(TableRequest.Filter.RI, "1600000"),
                                                Map.entry(TableRequest.Filter.RF, "500000")
                                        )
                                ),
                                new TableRequest("Mesa 3",
                                        Map.ofEntries(
                                                Map.entry(TableRequest.Filter.TC, "5"),
                                                Map.entry(TableRequest.Filter.UG, "3"),
                                                //Map.entry(TableRequest.Filter.RI, "1600000"),
                                                Map.entry(TableRequest.Filter.RF, "10000")
                                        )
                                ),
                                new TableRequest("Mesa 4",
                                        Map.ofEntries(
                                                //Map.entry(TableRequest.Filter.TC, "1"),
                                                Map.entry(TableRequest.Filter.UG, "1"),
                                                //Map.entry(TableRequest.Filter.RI, "1600000"),
                                                Map.entry(TableRequest.Filter.RF, "100000")
                                        )
                                ),
                                new TableRequest("Mesa 5",
                                        Map.ofEntries(
                                                //.entry(TableRequest.Filter.TC, "1"),
                                                Map.entry(TableRequest.Filter.UG, "99")
                                                //Map.entry(TableRequest.Filter.RI, "1600000"),
                                                //Map.entry(TableRequest.Filter.RF, "2000000")
                                        )
                                ),
                                new TableRequest("Mesa 6",
                                        Map.ofEntries(
                                                Map.entry(TableRequest.Filter.TC, "11"),
                                                //Map.entry(TableRequest.Filter.UG, "9"),
                                                Map.entry(TableRequest.Filter.RI, "100000")
                                                //Map.entry(TableRequest.Filter.RF, "2000000")
                                        )
                                )
                        )
                ).map(tableResponses -> tableResponses.stream().map(TableResponse::toString).collect(Collectors.toList()))
                .flatMapMany(Flux::fromIterable);
    }
}
