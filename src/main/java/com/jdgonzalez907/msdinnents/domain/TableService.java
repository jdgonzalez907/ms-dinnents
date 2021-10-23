package com.jdgonzalez907.msdinnents.domain;

import com.jdgonzalez907.msdinnents.application.TableRequest;
import com.jdgonzalez907.msdinnents.application.TableResponse;
import com.jdgonzalez907.msdinnents.domain.client.Client;
import com.jdgonzalez907.msdinnents.domain.client.ClientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TableService {
    private ClientRepository clientRepository;

    public TableService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Mono<TableResponse> distribute(TableRequest tableRequest) {
        Mono<List<Client>> ordered = this.clientRepository.findAll()
                .sort(Comparator.comparing(Client::getTotalBalance).reversed())
                .collectList();

        return ordered.map(clients -> {
            var x = filter(tableRequest.getFilters(), clients, Collections.emptyList()).stream()
                    .collect(Collectors.groupingBy(Client::getCompany)).entrySet().stream()
                    .flatMap(group -> {
                        var y = group.getValue().stream()
                                .findFirst()
                                .map(Collections::singletonList)
                                .orElse(Collections.emptyList())
                                .stream();
                        return y;
                    })
                    .sorted(Comparator.comparing(Client::getTotalBalance).reversed())
                    .collect(Collectors.groupingBy(Client::getMale)).entrySet().stream()
                    .flatMap(group -> {
                        var y = group.getValue().stream()
                                .limit(4);
                        return y;
                    })
                    .sorted(Comparator.comparing(Client::getTotalBalance).reversed())
                    .collect(Collectors.toList());

            return new TableResponse("Hola", new ArrayList<>());
        });

    }

    private List<Client> filter(Map<TableRequest.Filter, String> filters, List<Client> clientsToProcess, List<Client> accumClientsProcessed) {
        Optional<Map.Entry<TableRequest.Filter, String>> first = filters.entrySet().stream().findFirst();
        if (first.isEmpty()) {
            return clientsToProcess;
        } else {
            Map.Entry<TableRequest.Filter, String> firstFound = first.get();
            Map<TableRequest.Filter, String> othersFilters = filters.entrySet().stream()
                    .filter(filter -> !filter.getKey().equals(first.get().getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            List<Client> filtered = clientsToProcess.stream()
                    .filter(client -> TableRequest.validate(firstFound.getKey(), firstFound.getValue(), client) && !accumClientsProcessed.contains(client))
                    .collect(Collectors.toList());

            return filter(
                    othersFilters,
                    filtered,
                    accumClientsProcessed
            );
        }
    }
}
