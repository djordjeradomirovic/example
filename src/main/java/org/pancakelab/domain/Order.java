package org.pancakelab.domain;

import java.util.Objects;
import java.util.UUID;

public class Order {

    private final UUID id;

    private final int building;

    private final int room;

    private final OrderStatus status;

    public Order(int building, int room) {
        if (building < 0 || room < 0) {
            throw new IllegalArgumentException("Building and room must be positive integers");
        }
        this.id = UUID.randomUUID();
        this.building = building;
        this.room = room;
        this.status = OrderStatus.PENDING;
    }

    private Order(UUID id, int building, int room, OrderStatus status) {
        this.id = id;
        this.building = building;
        this.room = room;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public int getBuilding() {
        return building;
    }

    public int getRoom() {
        return room;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order complete() {
        return new Order(this.id, this.building, this.room, OrderStatus.COMPLETED);
    }

    public Order prepare() {
        return new Order(this.id, this.building, this.room, OrderStatus.PREPARED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
