package com.jdgonzalez907.msdinnents.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@AllArgsConstructor
@Table(value = "client")
public class ClientEntity {
    @Id
    private Integer id;
    private String code;
    private Integer male;
    private Integer type;
    private String location;
    private String company;
    private Integer encrypt;
    private List<AccountEntity> accountEntities;
}
