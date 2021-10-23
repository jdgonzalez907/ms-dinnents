package com.jdgonzalez907.msdinnents.infrastructure.mappers;

import com.jdgonzalez907.msdinnents.domain.Account;
import com.jdgonzalez907.msdinnents.infrastructure.entities.AccountEntity;
import com.jdgonzalez907.msdinnents.shared.Mapper;

public class AccountMapper implements Mapper<AccountEntity, Account> {
    @Override
    public AccountEntity toEntity(Account domain) {
        return new AccountEntity(domain.getId(), 0, domain.getBalance());
    }

    @Override
    public Account toDomain(AccountEntity entity) {
        return new Account(entity.getId(), entity.getBalance());
    }
}
