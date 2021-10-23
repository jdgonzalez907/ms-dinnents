package com.jdgonzalez907.msdinnents.domain;

import com.jdgonzalez907.msdinnents.shared.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Account {
    private int id;
    private BigDecimal balance;
}
