package com.jdgonzalez907.msdinnents.domain.diner;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Diner {
    String tableName;
    List<String> clientCodes;

    @Override
    public String toString() {
        return String.format("%s\n%s\n", this.getTableName(), this.joininClienCodes());
    }

    private String joininClienCodes() {
        if (this.clientCodes.size() < 4) {
            return "CANCELADA";
        } else {
            return this.clientCodes.stream().collect(Collectors.joining(","));
        }
    }
}
