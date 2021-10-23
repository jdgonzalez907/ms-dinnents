package com.jdgonzalez907.msdinnents.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Table(value = "account")
public class AccountEntity {
    @Id
    private int id;
    @Column(value = "client_id")
    private int clientId;
    private BigDecimal balance;
}