package de.uniba.soa.beveragestore.models.errors;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum ErrorType {
    INVALID_PARAMETER
}
