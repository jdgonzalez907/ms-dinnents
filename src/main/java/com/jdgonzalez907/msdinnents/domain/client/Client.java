package com.jdgonzalez907.msdinnents.domain.client;

import com.jdgonzalez907.msdinnents.domain.account.Account;
import com.jdgonzalez907.msdinnents.shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Client implements Aggregate {
    private Integer id;
    private String code;
    private String decryptCode;
    private Boolean male;
    private Integer type;
    private String location;
    private String company;
    private Boolean encrypt;
    private List<Account> accounts;

    public BigDecimal getTotalBalance() {
        return this.accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
