package com.jdgonzalez907.msdinnents.domain.diner;

import com.jdgonzalez907.msdinnents.application.diner.DinerRequest;
import com.jdgonzalez907.msdinnents.domain.client.Client;
import com.jdgonzalez907.msdinnents.domain.client.ClientRepository;
import com.jdgonzalez907.msdinnents.infrastructure.clients.CodeDecryptClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DinerService {
    private ClientRepository clientRepository;
    private CodeDecryptClient codeDecryptClient;

    private final Long MAX_CLIENTS_PER_GENDER = 4L;
    private final Long MAX_CLIENTS_PER_COMPANY = 1L;

    public DinerService(ClientRepository clientRepository, CodeDecryptClient codeDecryptClient) {
        this.clientRepository = clientRepository;
        this.codeDecryptClient = codeDecryptClient;
    }

    public Mono<List<Diner>> distribute(List<DinerRequest> dinerRequests) {
        return findAllClients(dinerRequests)
                .map(clients -> this.processRequest(dinerRequests, Collections.emptyList(), clients, Collections.emptyList()));

    }

    private Mono<List<Client>> findAllClients(List<DinerRequest> dinerRequests) {

        return Flux.merge(getClientsWithDecryptedCode(), getClientsWithEncryptedCode())
                .sort(Comparator.comparing(Client::getTotalBalance).reversed()
                        .thenComparing(Client::getDecryptCode))
                .collectList();
    }

    private Flux<Client> getClientsWithDecryptedCode() {
        return this.clientRepository.findByEncrypt(false);
    }

    private Flux<Client> getClientsWithEncryptedCode() {
        return this.clientRepository.findByEncrypt(true)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(client -> this.codeDecryptClient.consume(client)
                        .map(clientCodeDecrypt -> client
                                .toBuilder()
                                .decryptCode(clientCodeDecrypt.getDecryptCode())
                                .build()
                        ))
                .sequential()
                .publishOn(Schedulers.single());
    }

    private List<Diner> processRequest(List<DinerRequest> dinerRequests, List<Diner> tableRespons, List<Client> clients, List<Client> accuClientsProccesed) {
        Optional<DinerRequest> first = dinerRequests.stream().findFirst();
        if (first.isEmpty()) {
            return tableRespons;
        } else {
            DinerRequest requestFound = first.get();

            List<DinerRequest> othersRequests = getOthersRequests(dinerRequests, requestFound);

            List<Client> tableClients = getFilteredTableClients(clients, requestFound);

            Diner diner = new Diner(
                    requestFound.getTableName(),
                    tableClients.stream()
                            .map(Client::getDecryptCode)
                            .collect(Collectors.toList())
            );

            List<Diner> responseProccesed = getResponsesProcessed(tableRespons, diner);

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

    private List<Diner> getResponsesProcessed(List<Diner> tableRespons, Diner diner) {
        return Stream.of(tableRespons, Collections.singletonList(diner))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Client> getFilteredTableClients(List<Client> clients, DinerRequest requestFound) {
        List<Client> filteredClients = applyFilters(requestFound.getFilters(), clients, Collections.emptyList()).stream()
                .collect(Collectors.groupingBy(Client::getCompany)).entrySet().stream()
                .flatMap(group -> group.getValue().stream()
                        .findFirst()
                        .map(Collections::singletonList)
                        .orElse(Collections.emptyList())
                        .stream())
                .sorted(Comparator.comparing(Client::getTotalBalance).reversed().thenComparing(Client::getDecryptCode))
                .collect(Collectors.groupingBy(Client::getMale)).entrySet().stream()
                .flatMap(group -> group.getValue().stream()
                        .limit(4))
                .sorted(Comparator.comparing(Client::getTotalBalance).reversed().thenComparing(Client::getDecryptCode))
                .limit(8)
                .collect(Collectors.toList());

        return filterByGender(filteredClients)
                .sorted(Comparator.comparing(Client::getTotalBalance).reversed().thenComparing(Client::getDecryptCode))
                .collect(Collectors.toList());
    }

    private Stream<Client> filterByGender(List<Client> clients) {
        var males = clients.stream().filter(Client::getMale).collect(Collectors.toList());
        var countMales = males.size();
        var females = clients.stream().filter(client -> !client.getMale()).collect(Collectors.toList());
        var countFemales = females.size();
        if (countMales > countFemales) {
            males.sort(Comparator.comparing(Client::getTotalBalance).thenComparing(Client::getDecryptCode));
            return Stream.concat(males.stream().skip(countMales - countFemales), females.stream());
        } else if (countMales < countFemales) {
            females.sort(Comparator.comparing(Client::getTotalBalance).thenComparing(Client::getDecryptCode));
            return Stream.concat(females.stream().skip(countFemales - countMales), males.stream());
        } else {
            return clients.stream();
        }
    }

    private List<DinerRequest> getOthersRequests(List<DinerRequest> dinerRequests, DinerRequest requestFound) {
        return dinerRequests.stream()
                .filter(dinerRequest -> !dinerRequest.getTableName().equals(requestFound.getTableName()))
                .collect(Collectors.toList());
    }

    private List<Client> applyFilters(Map<DinerRequest.Filter, String> filters, List<Client> clientsToProcess, List<Client> processedClients) {
        Optional<Map.Entry<DinerRequest.Filter, String>> first = filters.entrySet().stream().findFirst();
        if (first.isEmpty()) {
            return clientsToProcess;
        } else {
            Map.Entry<DinerRequest.Filter, String> firstFound = first.get();
            Map<DinerRequest.Filter, String> othersFilters = filters.entrySet().stream()
                    .filter(filter -> !filter.getKey().equals(first.get().getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            List<Client> filtered = clientsToProcess.stream()
                    .filter(client -> DinerRequest.validate(firstFound.getKey(), firstFound.getValue(), client) && !processedClients.contains(client))
                    .collect(Collectors.toList());

            return applyFilters(
                    othersFilters,
                    filtered,
                    processedClients
            );
        }
    }
}
