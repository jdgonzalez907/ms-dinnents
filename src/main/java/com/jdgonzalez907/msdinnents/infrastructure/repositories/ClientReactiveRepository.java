package com.jdgonzalez907.msdinnents.infrastructure.repositories;

import com.jdgonzalez907.msdinnents.infrastructure.entities.ClientEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClientReactiveRepository extends ReactiveCrudRepository<ClientEntity, Integer> {
}
