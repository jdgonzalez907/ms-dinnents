package com.jdgonzalez907.msdinnents.infrastructure.repositories;

import com.jdgonzalez907.msdinnents.domain.client.Client;
import com.jdgonzalez907.msdinnents.domain.client.ClientRepository;
import com.jdgonzalez907.msdinnents.infrastructure.mappers.AccountMapper;
import com.jdgonzalez907.msdinnents.infrastructure.mappers.ClientMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private ClientReactiveRepository clientReactiveRepository;
    private AccountReactiveRepository accountReactiveRepository;
    private ClientMapper clientMapper;
    private AccountMapper accountMapper;

    public ClientRepositoryImpl(ClientReactiveRepository clientReactiveRepository, AccountReactiveRepository accountReactiveRepository, ClientMapper clientMapper, AccountMapper accountMapper) {
        this.clientReactiveRepository = clientReactiveRepository;
        this.accountReactiveRepository = accountReactiveRepository;
        this.clientMapper = clientMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public Flux<Client> findByEncrypt(Boolean encrypt) {
        return Flux.zip(this.accountReactiveRepository.findAll().collectList(), this.clientReactiveRepository.findByEncrypt(encrypt ? 1 : 0).collectList())
                .flatMap(tuple -> Flux.fromStream(
                        tuple.getT2().stream().map(clientEntity -> this.clientMapper
                                .toDomain(
                                        clientEntity,
                                        tuple.getT1().stream()
                                                .filter(accountEntity -> accountEntity.getClientId().equals(clientEntity.getId())).collect(Collectors.toList())
                                )
                        )
                ));
    }
}
