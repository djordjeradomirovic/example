package org.pancakelab.domain;

import java.util.List;
import java.util.UUID;

public class PancakeRepository {

    private final List<Pancake> pancakes;

    public PancakeRepository(List<Pancake> pancakes) {
        if (pancakes == null) {
            throw new IllegalArgumentException("Pancakes are required");
        }

        this.pancakes = pancakes;
    }

    public void save(Pancake pancake) {
        pancakes.add(pancake);
    }

    public void delete(Pancake pancake) {
        pancakes.remove(pancake);
    }

    public List<Pancake> findByOrderId(UUID orderId) {
        return pancakes.stream().filter(pancake -> pancake.getOrderId().equals(orderId)).toList();
    }

    public List<Pancake> findByOrderIdAndDescription(UUID orderId, String description) {
        return pancakes.stream()
                .filter(pancake -> pancake.getOrderId().equals(orderId))
                .filter(pancake -> pancake.getDescription().equals(description))
                .toList();
    }
}
