package com.caritas.backend.common;

import com.fasterxml.jackson.databind.JsonNode;

public class UtilsJSON {
    public static JsonNode getField(String fieldName, JsonNode request, boolean required) {
        if (!request.has(fieldName)) {
            if (!required)
                return null;

            throw new RuntimeException("The field '" + fieldName + "' is mandatory");
        }

        return request.get(fieldName);
    }
}
