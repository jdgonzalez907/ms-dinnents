package com.jdgonzalez907.msdinnents.infrastructure.clients;

import com.jdgonzalez907.msdinnents.domain.client.Client;
import com.jdgonzalez907.msdinnents.domain.client.ClientCodeDecrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CodeDecryptClient {

    private WebClient webClient;

    public CodeDecryptClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://test.evalartapp.com/extapiquest/code_decrypt").build();
    }

    public Mono<ClientCodeDecrypt> consume(Client client) {
        return this.webClient.get().uri("/{code}", client.getCode())
                .retrieve().bodyToMono(String.class)
                .map(decryptCode -> new ClientCodeDecrypt(decryptCode, client));
    }
}
