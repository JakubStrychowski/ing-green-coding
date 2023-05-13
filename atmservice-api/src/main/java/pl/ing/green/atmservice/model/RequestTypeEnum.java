/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pl.ing.green.atmservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Type of request
 */
public enum RequestTypeEnum {
    STANDARD("STANDARD"), PRIORITY("PRIORITY"), SIGNAL_LOW("SIGNAL_LOW"), FAILURE_RESTART("FAILURE_RESTART");
    private String value;

    RequestTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static RequestTypeEnum fromValue(String value) {
        for (RequestTypeEnum b : RequestTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
    
}
