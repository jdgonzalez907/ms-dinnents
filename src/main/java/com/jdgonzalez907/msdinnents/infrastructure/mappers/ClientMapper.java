package com.jdgonzalez907.msdinnents.infrastructure.mappers;

import com.jdgonzalez907.msdinnents.domain.client.Client;
import com.jdgonzalez907.msdinnents.infrastructure.entities.AccountEntity;
import com.jdgonzalez907.msdinnents.infrastructure.entities.ClientEntity;
import com.jdgonzalez907.msdinnents.shared.Utils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    private AccountMapper accountMapper;

    public ClientMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public ClientEntity toEntity(Client domain) {
        return new ClientEntity(domain.getId(), domain.getCode(), Utils.booleanToInteger(domain.getMale()),
                domain.getType(), domain.getLocation(), domain.getCompany(), Utils.booleanToInteger(domain.getEncrypt()),
                domain.getAccounts().stream()
                        .map(account -> accountMapper.toEntity(account, domain))
                        .collect(Collectors.toList()));
    }

    public Client toDomain(ClientEntity entity, List<AccountEntity> accountEntities) {
        return new Client(entity.getId(), entity.getCode(), Utils.integerToBoolean(entity.getMale()), entity.getType(),
                entity.getLocation(), entity.getCompany(), Utils.integerToBoolean(entity.getEncrypt()),
                accountEntities.stream()
                        .map(accountEntity -> accountMapper.toDomain(accountEntity))
                        .collect(Collectors.toList()));
    }
}
