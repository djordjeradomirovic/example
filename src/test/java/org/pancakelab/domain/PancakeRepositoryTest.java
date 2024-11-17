package org.pancakelab.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.pancakelab.domain.Ingredient;
import org.pancakelab.domain.Pancake;
import org.pancakelab.domain.PancakeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PancakeRepositoryTest {

    private final List<Pancake> pancakes = new ArrayList<>();

    private final PancakeRepository pancakeRepository = new PancakeRepository(pancakes);

    @AfterEach
    void tearDown() {
        pancakes.clear();
    }

    @Test
    void create_whenIllegalArguments_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PancakeRepository(null));
    }

    @Test
    void save_whenDoesNotExist_shouldSave() {
        Pancake pancake = new Pancake(UUID.randomUUID(), List.of(Ingredient.DARK_CHOCOLATE));
        pancakeRepository.save(pancake);

        assertEquals(1, pancakes.size());
        assertEquals(pancake, pancakes.get(0));
    }

    @Test
    void delete_whenExists_shouldRemove() {
        Pancake pancake = new Pancake(UUID.randomUUID(), List.of(Ingredient.DARK_CHOCOLATE));
        pancakes.add(pancake);

        pancakeRepository.delete(pancake);

        assertTrue(pancakes.isEmpty());
    }

    @Test
    void findByOrderId_whenExists_shouldReturnResults() {
        UUID orderId = UUID.randomUUID();
        Pancake pancake = new Pancake(orderId, List.of(Ingredient.DARK_CHOCOLATE));
        pancakes.add(pancake);

        List<Pancake> result = pancakeRepository.findByOrderId(orderId);

        assertEquals(1, result.size());
        assertEquals(pancake, result.get(0));
    }

    @Test
    void findByOrderId_whenDoesNotExist_shouldReturnEmptyResults() {
        UUID orderId = UUID.randomUUID();
        Pancake pancake = new Pancake(orderId, List.of(Ingredient.DARK_CHOCOLATE));
        pancakes.add(pancake);

        List<Pancake> result = pancakeRepository.findByOrderId(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void findByOrderIdAndDescription_whenExists_shouldReturnResults() {
        UUID orderId = UUID.randomUUID();
        Pancake pancake = new Pancake(orderId, List.of(Ingredient.DARK_CHOCOLATE));
        pancakes.add(pancake);

        List<Pancake> result = pancakeRepository.findByOrderIdAndDescription(orderId, pancake.getDescription());

        assertEquals(1, result.size());
        assertEquals(pancake, result.get(0));
    }

    @Test
    void findByOrderIdAndDescription_whenDoesNotExist_shouldReturnEmptyResults() {
        UUID orderId = UUID.randomUUID();
        Pancake pancake = new Pancake(orderId, List.of(Ingredient.DARK_CHOCOLATE));
        pancakes.add(pancake);

        List<Pancake> result = pancakeRepository.findByOrderIdAndDescription(UUID.randomUUID(), pancake.getDescription());
        assertTrue(result.isEmpty());

        result = pancakeRepository.findByOrderIdAndDescription(orderId, "");
        assertTrue(result.isEmpty());
    }
}