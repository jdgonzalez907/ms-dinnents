package com.jdgonzalez907.msdinnents.domain;

import com.jdgonzalez907.msdinnents.shared.Repository;
import reactor.core.publisher.Flux;

public interface ClientRepository extends Repository<Client> {
    Flux<Client> findAll();
}
