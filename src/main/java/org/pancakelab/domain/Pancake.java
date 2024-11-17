package org.pancakelab.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Pancake {

    private final UUID orderId;

    private final List<Ingredient> ingredients;

    public Pancake(UUID orderId, List<Ingredient> ingredients) {
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is required");
        }

        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException("ingredients are required");
        }

        List<Ingredient> sortedIngredients = new ArrayList<>(ingredients);
        Collections.sort(sortedIngredients);

        this.orderId = orderId;
        this.ingredients = List.copyOf(sortedIngredients);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getDescription() {
        return "Delicious pancake with %s!".formatted(String.join(", ", ingredients.stream().map(Ingredient::getValue).toArray(String[]::new)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Pancake) obj;
        return Objects.equals(this.orderId, that.orderId) && Objects.equals(this.ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, ingredients);
    }

    @Override
    public String toString() {
        return "Pancake[" + "orderId=" + orderId + ", " + "ingredients=" + ingredients + ']';
    }
}
