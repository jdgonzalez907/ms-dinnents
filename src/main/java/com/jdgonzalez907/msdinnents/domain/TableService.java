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

    public Mono<List<TableResponse>> distribute(List<TableRequest> tableRequests) {
        Mono<List<Client>> ordered = this.clientRepository.findAll()
                .sort(Comparator.comparing(Client::getTotalBalance).reversed())
                .collectList();

        return ordered.map(clients -> this.processRequest(tableRequests, Collections.emptyList(), clients, Collections.emptyList()));

    }

    private List<TableResponse> processRequest(List<TableRequest> tableRequests, List<TableResponse> tableResponses, List<Client> clients, List<Client> accuClientsProccesed) {
        Optional<TableRequest> first = tableRequests.stream().findFirst();
        if (first.isEmpty()) {
            return tableResponses;
        } else {
            TableRequest requestFound = first.get();

            List<TableRequest> othersRequests = getOthersRequests(tableRequests, requestFound);

            List<Client> tableClients = getFilteredTableClients(clients, requestFound);

            TableResponse tableResponse = new TableResponse(
                    requestFound.getTableName(),
                    tableClients.stream()
                            .map(Client::getCode)
                            .collect(Collectors.toList())
            );

            List<TableResponse> responseProccesed = getResponsesProcessed(tableResponses, tableResponse);

            List<Client> clientsToProcces = getClientsToProcess(clients, tableClients);

            List<Client> clientsProccesed = getClientsProccesed(accuClientsProccesed, tableClients);

            return processRequest(othersRequests, responseProccesed, clientsToProcces, clientsProccesed);
        }

    }

    private List<Client> getClientsProccesed(List<Client> accuClientsProccesed, List<Client> tableClients) {
        return Stream.of(accuClientsProccesed, tableClients)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Client> getClientsToProcess(List<Client> clients, List<Client> tableClients) {
        return clients.stream()
                .filter(client -> !tableClients.contains(client))
                .collect(Collectors.toList());
    }

    private List<TableResponse> getResponsesProcessed(List<TableResponse> tableResponses, TableResponse tableResponse) {
        return Stream.of(tableResponses, Collections.singletonList(tableResponse))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Client> getFilteredTableClients(List<Client> clients, TableRequest requestFound) {
        return this.filterByMale(
                filterClients(requestFound.getFilters(), clients, Collections.emptyList()).stream()
                        .collect(Collectors.groupingBy(Client::getCompany)).entrySet().stream()
                        .flatMap(group -> group.getValue().stream()
                                .findFirst()
                                .map(Collections::singletonList)
                                .orElse(Collections.emptyList())
                                .stream())
                        .sorted(Comparator.comparing(Client::getTotalBalance).reversed())
                        .collect(Collectors.groupingBy(Client::getMale)).entrySet().stream()
                        .flatMap(group -> group.getValue().stream()
                                .limit(4))
                        .sorted(Comparator.comparing(Client::getTotalBalance).reversed())
                        .limit(8)
                        .collect(Collectors.toList())
        );
    }

    private List<TableRequest> getOthersRequests(List<TableRequest> tableRequests, TableRequest requestFound) {
        return tableRequests.stream()
                .filter(tableRequest -> !tableRequest.getTableName().equals(requestFound.getTableName()))
                .collect(Collectors.toList());
    }

    private List<Client> filterClients(Map<TableRequest.Filter, String> filters, List<Client> clientsToProcess, List<Client> accumClientsProcessed) {
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

            return filterClients(
                    othersFilters,
                    filtered,
                    accumClientsProcessed
            );
        }
    }

    private List<Client> filterByMale(List<Client> tableClients) {
        return tableClients;
        /*
        Long maleCount = tableClients.stream().filter(Client::getMale).count();
        Long femaleCount = tableClients.stream().filter(client -> !client.getMale()).count();
        maleCount - (maleCount - femaleCount);
        femaleCount - (femaleCount - maleCount);
        */
    }
}
