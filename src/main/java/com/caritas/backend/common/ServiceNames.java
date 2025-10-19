package com.caritas.backend.common;

import java.util.Set;

import com.caritas.backend.common.errors.BadRequestException;

public class ServiceNames {
    public static final String TRANSPORTATION = "transportations";
    public static final String BREAKFAST = "breakfasts";
    public static final String MEAL = "meals";
    public static final String DINNER = "dinners";
    public static final String LAUNDRY = "laundries";
    public static final String BATH = "baths";
    public static final String DENTAL = "dentals";
    public static final String MENTAL = "mentals";
    public static final String DOCUMENT = "documents";

    public static final Set<String> SERVICES = Set.of(TRANSPORTATION, BREAKFAST, MEAL, DINNER, LAUNDRY, BATH, DENTAL, MENTAL, DOCUMENT);

    public static void isValidServiceOrThrow(String serviceName) {
        if (!SERVICES.contains(serviceName)) {
            throw new BadRequestException(serviceName + " is not a valid service name");
        }
    }
}
