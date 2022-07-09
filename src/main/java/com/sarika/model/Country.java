package com.sarika.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
    INDIA("+91"),
    USA("+1");

    private String countryCode;
}
