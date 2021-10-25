package com.jdgonzalez907.msdinnents.application.diner;

import com.jdgonzalez907.msdinnents.application.diner.ReaderTableRequest;
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
    private ReaderTableRequest readerTableRequest;

    public DinerController(DinerService dinerService, ReaderTableRequest readerTableRequest) {
        this.dinerService = dinerService;
        this.readerTableRequest = readerTableRequest;
    }

    @PostMapping(value = "/distribute")
    Flux<String> calculateTables(@RequestBody String dataFilter) {

        return this.dinerService
                .distribute(readerTableRequest.transform(dataFilter))
                .map(tableResponses -> tableResponses.stream().map(Diner::toString).collect(Collectors.toList()))
                .flatMapMany(Flux::fromIterable);
    }
}
