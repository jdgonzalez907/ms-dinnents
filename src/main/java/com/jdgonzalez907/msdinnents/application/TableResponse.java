package com.jdgonzalez907.msdinnents.application;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TableResponse {
    String tableName;
    List<String> clientCodes;
}
