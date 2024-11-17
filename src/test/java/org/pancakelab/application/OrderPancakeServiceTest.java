package org.pancakelab.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pancakelab.domain.OrderRepository;
import org.pancakelab.domain.Ingredient;
import org.pancakelab.domain.Order;
import org.pancakelab.domain.Pancake;
import org.pancakelab.domain.PancakeRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OrderPancakeServiceTest {

    private Order order = null;

    private final static String DARK_CHOCOLATE_PANCAKE_DESCRIPTION = "Delicious pancake with dark chocolate!";

    private final static String MILK_CHOCOLATE_PANCAKE_DESCRIPTION = "Delicious pancake with milk chocolate!";

    private final static String MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION = "Delicious pancake with milk chocolate, hazelnuts!";

    private OrderPancakeService orderPancakeService;

    @BeforeEach
    public void setUp() {
        orderPancakeService = new OrderPancakeService(new OrderRepository(new CopyOnWriteArrayList<>()), new PancakeRepository(new CopyOnWriteArrayList<>()));
        order = orderPancakeService.createOrder(10, 20);
        addPancakes();
    }

    @Test
    public void GivenOrderDoesNotExist_WhenCreatingOrder_ThenOrderCreatedWithCorrectData_Test() {
        assertEquals(10, order.getBuilding());
        assertEquals(20, order.getRoom());
    }

    @Test
    public void GivenOrderExists_WhenAddingPancakes_ThenCorrectNumberOfPancakesAdded_Test() {
        assertEquals(List.of(DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION), viewPancakesByOrderId(order.getId()));
    }

    @Test
    public void GivenPancakesExists_WhenRemovingPancakes_ThenCorrectNumberOfPancakesRemoved_Test() {
        orderPancakeService.removePancakes(DARK_CHOCOLATE_PANCAKE_DESCRIPTION, order.getId(), 2);
        orderPancakeService.removePancakes(MILK_CHOCOLATE_PANCAKE_DESCRIPTION, order.getId(), 3);
        orderPancakeService.removePancakes(MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION, order.getId(), 1);

        List<String> ordersPancakes = orderPancakeService.findByOrderId(order.getId()).stream().map(Pancake::getDescription).toList();

        assertEquals(List.of(DARK_CHOCOLATE_PANCAKE_DESCRIPTION, MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION, MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION),
                ordersPancakes);
    }

    @Test
    public void GivenOrderExists_WhenCompletingOrder_ThenOrderCompleted_Test() {
        orderPancakeService.completeOrder(order.getId());
        Set<UUID> completedOrdersOrders = orderPancakeService.listCompletedOrders();
        assertTrue(completedOrdersOrders.contains(order.getId()));
    }

    @Test
    public void GivenOrderExists_WhenPreparingOrder_ThenOrderPrepared_Test() {
        orderPancakeService.prepareOrder(order.getId());

        Set<UUID> completedOrders = orderPancakeService.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderPancakeService.listPreparedOrders();
        assertTrue(preparedOrders.contains(order.getId()));
    }

    @Test
    public void GivenOrderExists_WhenDeliveringOrder_ThenCorrectOrderReturnedAndOrderRemovedFromTheDatabase_Test() {
        List<Pancake> pancakes = orderPancakeService.deliverOrder(order.getId());

        Set<UUID> completedOrders = orderPancakeService.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderPancakeService.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.getId()));

        List<String> ordersPancakes = viewPancakesByOrderId(order.getId());

        assertEquals(List.of(), ordersPancakes);
        pancakes.stream().map(Pancake::getOrderId).forEach(orderId -> assertEquals(order.getId(), orderId));
        ;
        assertEquals(viewPancakesByOrderId(order.getId()), orderPancakeService.findByOrderId(order.getId()).stream().map(Pancake::getDescription).toList());
    }

    @Test
    public void GivenOrderExists_WhenCancellingOrder_ThenOrderAndPancakesRemoved_Test() {
        orderPancakeService.cancelOrder(order.getId());

        Set<UUID> completedOrders = orderPancakeService.listCompletedOrders();
        assertFalse(completedOrders.contains(order.getId()));

        Set<UUID> preparedOrders = orderPancakeService.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.getId()));

        List<String> ordersPancakes = viewPancakesByOrderId(order.getId());

        assertEquals(List.of(), ordersPancakes);
    }

    private void addPancakes() {
        orderPancakeService.addPancake(order.getId(), List.of(Ingredient.DARK_CHOCOLATE), 3);
        orderPancakeService.addPancake(order.getId(), List.of(Ingredient.MILK_CHOCOLATE), 3);
        orderPancakeService.addPancake(order.getId(), List.of(Ingredient.MILK_CHOCOLATE, Ingredient.HAZELNUTS), 3);
        orderPancakeService.prepareOrder(order.getId());
    }

    private List<String> viewPancakesByOrderId(UUID orderId) {
        return orderPancakeService.findByOrderId(orderId).stream().map(Pancake::getDescription).toList();
    }
}
