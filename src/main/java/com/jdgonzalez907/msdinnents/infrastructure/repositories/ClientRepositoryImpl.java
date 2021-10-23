package com.jdgonzalez907.msdinnents.infrastructure.repositories;

import com.jdgonzalez907.msdinnents.domain.Client;
import com.jdgonzalez907.msdinnents.domain.ClientRepository;
import com.jdgonzalez907.msdinnents.infrastructure.mappers.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    @Autowired
    private ClientReactiveRepository clientReactiveRepository;
    @Autowired
    private ClientMapper clientMapper;

    @Override

    public Flux<Client> findAll() {
        return clientReactiveRepository.findAll()
                .map(clientEntity -> clientMapper.toDomain(clientEntity));
    }
}
