package org.pancakelab.domain;

import org.junit.jupiter.api.Test;
import org.pancakelab.domain.Order;
import org.pancakelab.domain.OrderStatus;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void create_whenIllegalArguments_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Order(-1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Order(1, -1));
    }

    @Test
    void create_whenCorrectArguments_shouldCreate() {
        Order order = new Order(1, 1);

        assertNotNull(order.getId());
        assertEquals(1, order.getBuilding());
        assertEquals(1, order.getRoom());
        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void complete_whenComplete_shouldChangeStatusToCompleted() {
        Order order = new Order(1, 1);

        Order completedOrder = order.complete();

        assertEquals(order.getId(), completedOrder.getId());
        assertEquals(order.getBuilding(), completedOrder.getBuilding());
        assertEquals(order.getRoom(), completedOrder.getRoom());
        assertEquals(OrderStatus.COMPLETED, completedOrder.getStatus());
    }

    @Test
    void prepare_whenPrepare_shouldChangeStatusToPrepared() {
        Order order = new Order(1, 1);

        Order completedOrder = order.prepare();

        assertEquals(order.getId(), completedOrder.getId());
        assertEquals(order.getBuilding(), completedOrder.getBuilding());
        assertEquals(order.getRoom(), completedOrder.getRoom());
        assertEquals(OrderStatus.PREPARED, completedOrder.getStatus());
    }
}