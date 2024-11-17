package org.pancakelab.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderRepository {

    private final List<Order> orders;

    public OrderRepository(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException("Orders are required");
        }

        this.orders = orders;
    }

    public void save(Order order) {
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
    }

    public Optional<Order> findById(UUID orderId) {
        return orders.stream().filter(order -> order.getId().equals(orderId)).findFirst();
    }

    public Set<UUID> findUUIDByStatus(OrderStatus status) {
        return orders.stream().filter(order -> order.getStatus().equals(status)).map(Order::getId).collect(Collectors.toSet());
    }

    public void delete(Order order) {
        orders.remove(order);
    }
}
