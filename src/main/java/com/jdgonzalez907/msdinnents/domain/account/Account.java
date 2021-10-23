package com.jdgonzalez907.msdinnents.domain.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Account {
    private Integer id;
    private Integer clientId;
    private BigDecimal balance;
}
