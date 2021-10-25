package com.jdgonzalez907.msdinnents.domain.diner;

import com.jdgonzalez907.msdinnents.domain.client.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
public class DinerRequest {
    public enum Filter {
        TC, UG, RI, RF
    }

    String tableName;
    Map<Filter, String> filters;

    public static Boolean validate(Filter filterName, String filterValue, Client client) {
        Boolean isValid;
        switch (filterName) {
            case TC:
                isValid = Objects.equals(client.getType(), Integer.valueOf(filterValue));
                break;
            case UG:
                isValid = client.getLocation().equals(filterValue);
                break;
            case RI:
                isValid = client.getTotalBalance().compareTo(new BigDecimal(filterValue)) > 0;
                break;
            case RF:
                isValid = client.getTotalBalance().compareTo(new BigDecimal(filterValue)) < 0;
                break;
            default:
                isValid = false;
                break;
        }
        return isValid;
    }
}
