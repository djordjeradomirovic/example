package org.pancakelab.domain;

public enum Ingredient {
    MILK_CHOCOLATE("milk chocolate"),
    DARK_CHOCOLATE("dark chocolate"),
    HAZELNUTS("hazelnuts"),
    WHIPPED_CREAM("whipped cream");

    Ingredient(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
