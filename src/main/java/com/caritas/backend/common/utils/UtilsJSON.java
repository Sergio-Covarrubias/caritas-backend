package com.caritas.backend.common.utils;

import com.caritas.backend.common.errors.BadRequestException;
import com.fasterxml.jackson.databind.JsonNode;

public class UtilsJSON {
    public static JsonNode getField(String fieldName, JsonNode request, boolean required) {
        if (!request.has(fieldName)) {
            if (!required)
                return null;

            throw new BadRequestException("Field '" + fieldName + "' must not be null");
        }

        return request.get(fieldName);
    }
}
