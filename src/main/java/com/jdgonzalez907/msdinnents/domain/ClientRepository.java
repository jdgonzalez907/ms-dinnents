package com.jdgonzalez907.msdinnents.domain;

import reactor.core.publisher.Flux;

public interface ClientRepository {
    Flux<Client> findAll();
}
