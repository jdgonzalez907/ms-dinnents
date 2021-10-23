package com.jdgonzalez907.msdinnents.infrastructure.mappers;

import com.jdgonzalez907.msdinnents.domain.Client;
import com.jdgonzalez907.msdinnents.infrastructure.entities.ClientEntity;
import com.jdgonzalez907.msdinnents.shared.Mapper;
import com.jdgonzalez907.msdinnents.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClientMapper implements Mapper<ClientEntity, Client> {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ClientEntity toEntity(Client domain) {
        return new ClientEntity(domain.getId(), domain.getCode(), Utils.booleanToInteger(domain.getMale()),
                domain.getType(), domain.getLocation(), domain.getCompany(), Utils.booleanToInteger(domain.getEncrypt()),
                domain.getAccounts().stream()
                        .map(account -> accountMapper.toEntity(account))
                        .collect(Collectors.toList()));
    }

    @Override
    public Client toDomain(ClientEntity entity) {
        return new Client(entity.getId(), entity.getCode(), Utils.integerToBoolean(entity.getMale()), entity.getType(),
                entity.getLocation(), entity.getCompany(), Utils.integerToBoolean(entity.getEncrypt()),
                entity.getAccountEntities().stream()
                        .map(accountEntity -> accountMapper.toDomain(accountEntity))
                        .collect(Collectors.toList()));
    }
}
