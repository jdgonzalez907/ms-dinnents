package com.jdgonzalez907.msdinnents.infrastructure.entities;

import com.jdgonzalez907.msdinnents.shared.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Table(value = "account")
public class AccountEntity implements Entity {
    @Id
    private Integer id;
    @Column(value = "client_id")
    private Integer clientId;
    private BigDecimal balance;
}
