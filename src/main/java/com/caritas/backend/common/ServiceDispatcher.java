package com.caritas.backend.common;

import com.caritas.backend.common.utils.UtilsJSON;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ServiceDispatcher {

    @Value("${SELF_API_URL}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private JsonNode callApi(String url, JsonNode body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonNode bodyResponse = response.getBody();
            if (bodyResponse == null) {
                throw new RuntimeException("Could not get the body from the API call");
            }

            return bodyResponse;
        }

        throw new RuntimeException("Failed to call external API");
    }

    public record ServiceReservationCallResponse(String externalReservationId, Integer count) {
    }

    public ServiceReservationCallResponse createServiceReservation(JsonNode request) {
        String serviceName = UtilsJSON.getField("serviceName", request, true).asText();
        ServiceNames.isValidServiceOrThrow(serviceName);

        JsonNode externalReservationResponse = callApi(baseUrl + "/internal/" + serviceName, request);

        String externalReservationId = UtilsJSON.getField("id", externalReservationResponse, true)
                .asText();
        Integer count = UtilsJSON.getField("count", externalReservationResponse, true).asInt();

        return new ServiceReservationCallResponse(externalReservationId, count);
    }
}
