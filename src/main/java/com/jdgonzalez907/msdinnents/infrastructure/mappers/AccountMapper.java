package com.jdgonzalez907.msdinnents.infrastructure.mappers;

import com.jdgonzalez907.msdinnents.domain.account.Account;
import com.jdgonzalez907.msdinnents.domain.client.Client;
import com.jdgonzalez907.msdinnents.infrastructure.entities.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountEntity toEntity(Account domain, Client client) {
        return new AccountEntity(domain.getId(), client.getId(), domain.getBalance());
    }

    public Account toDomain(AccountEntity entity) {
        return new Account(entity.getId(), entity.getClientId(), entity.getBalance());
    }
}
