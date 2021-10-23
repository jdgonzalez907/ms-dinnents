package com.jdgonzalez907.msdinnents.application;

import com.jdgonzalez907.msdinnents.domain.client.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class TableRequest {
    public enum Filter {
        TC, UG, RI, RF
    };

    String tableName;
    Map<Filter, String> filters;

    public static Boolean validate(Filter filterName, String filterValue, Client client) {
        switch (filterName) {
            case TC:
                return client.getType() == Integer.valueOf(filterValue);
            case UG:
                return client.getLocation().equals(filterValue);
            case RI:
                return client.getTotalBalance().compareTo(new BigDecimal(filterValue)) > 0;
            case RF:
                return client.getTotalBalance().compareTo(new BigDecimal(filterValue)) < 0;
            default:
                return false;
        }
    }
}
