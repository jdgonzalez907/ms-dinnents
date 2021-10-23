package com.jdgonzalez907.msdinnents.infrastructure.repositories;

import com.jdgonzalez907.msdinnents.infrastructure.entities.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public interface AccountReactiveRepository extends ReactiveCrudRepository<AccountEntity, Integer> {

    Flux<AccountEntity> findByClientId(Integer clientId);

}
