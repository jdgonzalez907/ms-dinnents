package com.jdgonzalez907.msdinnents.domain.client;

import com.jdgonzalez907.msdinnents.infrastructure.entities.ClientEntity;
import com.jdgonzalez907.msdinnents.shared.domain.Aggregate;
import com.jdgonzalez907.msdinnents.shared.infrastructure.Repository;
import reactor.core.publisher.Flux;

public interface ClientRepository extends Repository<ClientEntity> {
    Flux<Client> findAll();
}
