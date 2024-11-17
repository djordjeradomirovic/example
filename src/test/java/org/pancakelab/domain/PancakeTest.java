package org.pancakelab.domain;

import org.junit.jupiter.api.Test;
import org.pancakelab.domain.Ingredient;
import org.pancakelab.domain.Pancake;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PancakeTest {

    @Test
    void create_whenIllegalArguments_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Pancake(null, List.of(Ingredient.DARK_CHOCOLATE)));
        assertThrows(IllegalArgumentException.class, () -> new Pancake(UUID.randomUUID(), null));
        assertThrows(IllegalArgumentException.class, () -> new Pancake(UUID.randomUUID(), Collections.emptyList()));
    }

    @Test
    void create_whenCorrectArguments_shouldCreate() {
        UUID orderId = UUID.randomUUID();
        Pancake pancake = new Pancake(orderId, List.of(Ingredient.DARK_CHOCOLATE));

        assertEquals(orderId, pancake.getOrderId());
        assertEquals("Delicious pancake with dark chocolate!", pancake.getDescription());
    }
}