package com.jdgonzalez907.msdinnents.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Client {
    private int id;
    private String code;
    private Boolean gender;
    private int type;
    private String location;
    private String company;
    private Boolean encrypt;
    private List<Account> accounts;
}
