package org.pancakelab.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    @AfterEach
    void tearDown() {
        orders.clear();
    }

    private final List<Order> orders = new ArrayList<>();

    private final OrderRepository orderRepository = new OrderRepository(orders);

    @Test
    void create_whenIllegalArguments_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new OrderRepository(null));
    }

    @Test
    void save_whenDoesNotExist_shouldSave() {
        Order order = new Order(1, 1);

        orderRepository.save(order);

        assertEquals(1, orders.size());
        assertEquals(order, orders.get(0));
    }

    @Test
    void save_whenExists_shouldUpdate() {
        Order order = new Order(1, 1);
        orders.add(order);

        Order completedOrder = order.complete();

        orderRepository.save(completedOrder);

        assertEquals(1, orders.size());
        assertEquals(completedOrder, orders.get(0));
    }

    @Test
    void findById_whenExists_shouldReturnResult() {
        Order order = new Order(1, 1);
        orders.add(order);

        Order result = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals(order, result);
    }

    @Test
    void findById_whenDoesNotExist_shouldReturnEmptyResult() {
        Order order = new Order(1, 1);
        orders.add(order);

        Optional<Order> result = orderRepository.findById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    void findUUIDByStatus_whenExists_shouldReturnResult() {
        Order order = new Order(1, 1);
        orders.add(order);

        Set<UUID> result = orderRepository.findUUIDByStatus(OrderStatus.PENDING);
        assertTrue(result.contains(order.getId()));
    }

    @Test
    void findUUIDByStatus_whenDoesNotExist_shouldReturnEmptyResult() {
        Order order = new Order(1, 1);
        orders.add(order);

        Set<UUID> result = orderRepository.findUUIDByStatus(OrderStatus.COMPLETED);
        assertTrue(result.isEmpty());
    }

    @Test
    void delete_whenExist_shouldRemoveFromCollection() {
        Order order = new Order(1, 1);
        orders.add(order);

        orderRepository.delete(order);

        assertTrue(orders.isEmpty());
    }
}