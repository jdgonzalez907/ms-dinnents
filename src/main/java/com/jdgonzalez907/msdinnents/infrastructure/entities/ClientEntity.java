package com.jdgonzalez907.msdinnents.infrastructure.entities;

import com.jdgonzalez907.msdinnents.shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@AllArgsConstructor
@Table(value = "client")
public class ClientEntity implements Aggregate {
    @Id
    private Integer id;
    private String code;
    private Integer male;
    private Integer type;
    private String location;
    private String company;
    private Integer encrypt;
    @Transient
    private List<AccountEntity> accountEntities;

    @PersistenceConstructor
    public ClientEntity(Integer id, String code, Integer male, Integer type, String location, String company, Integer encrypt) {
        this.id = id;
        this.code = code;
        this.male = male;
        this.type = type;
        this.location = location;
        this.company = company;
        this.encrypt = encrypt;
    }
}
