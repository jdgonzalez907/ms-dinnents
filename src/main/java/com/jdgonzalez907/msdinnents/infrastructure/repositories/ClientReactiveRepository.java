package com.jdgonzalez907.msdinnents.infrastructure.repositories;

import com.jdgonzalez907.msdinnents.infrastructure.entities.ClientEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public interface ClientReactiveRepository extends ReactiveCrudRepository<ClientEntity, Integer> {
    Flux<ClientEntity> findByEncrypt(Integer encrypt);
}
