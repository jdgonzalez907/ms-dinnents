package com.jdgonzalez907.msdinnents.domain.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientCodeDecrypt {
    private String decryptCode;
    private Client client;
}
