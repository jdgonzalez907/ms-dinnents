package com.jdgonzalez907.msdinnents.application.diner;

import com.jdgonzalez907.msdinnents.domain.diner.Diner;
import com.jdgonzalez907.msdinnents.domain.diner.DinerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tables")
public class DinerController {

    private DinerService dinerService;
    private ReaderDinerRequest readerDinerRequest;

    public DinerController(DinerService dinerService, ReaderDinerRequest readerDinerRequest) {
        this.dinerService = dinerService;
        this.readerDinerRequest = readerDinerRequest;
    }

    @PostMapping(value = "/distribute")
    Flux<String> calculateTables(@RequestBody String dataFilter) {

        return this.dinerService
                .distribute(readerDinerRequest.transform(dataFilter))
                .map(diners -> diners.stream().map(Diner::toString).collect(Collectors.toList()))
                .flatMapMany(Flux::fromIterable);
    }
}
