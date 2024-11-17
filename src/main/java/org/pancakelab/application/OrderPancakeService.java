package org.pancakelab.application;

import org.pancakelab.domain.OrderRepository;
import org.pancakelab.domain.OrderStatus;
import org.pancakelab.domain.Ingredient;
import org.pancakelab.domain.Order;
import org.pancakelab.domain.Pancake;
import org.pancakelab.domain.PancakeRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

public class OrderPancakeService {

    private final OrderRepository orderRepository;

    private final PancakeRepository pancakeRepository;

    public OrderPancakeService(OrderRepository orderRepository, PancakeRepository pancakeRepository) {
        this.orderRepository = orderRepository;
        this.pancakeRepository = pancakeRepository;
    }

    public void addPancake(UUID orderId, List<Ingredient> ingredients, int count) {
        Order order = findOrderById(orderId);
        Pancake pancake = new Pancake(orderId, ingredients);
        IntStream.range(0, count).forEach(i -> pancakeRepository.save(pancake));
        OrderLog.logAddPancake(order, pancake.getDescription(), count);
    }

    public void removePancakes(String description, UUID orderId, int count) {
        List<Pancake> pancakes = pancakeRepository.findByOrderIdAndDescription(orderId, description).stream().limit(count).toList();
        pancakes.forEach(pancakeRepository::delete);
        Order order = findOrderById(orderId);
        OrderLog.logRemovePancakes(order, description, count, pancakes.size());
    }

    public List<Pancake> findByOrderId(UUID orderId) {
        return pancakeRepository.findByOrderId(orderId);
    }

    public Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found."));
    }

    public Order createOrder(int building, int room) {
        Order order = new Order(building, room);
        orderRepository.save(order);
        return order;
    }

    public void cancelOrder(UUID orderId) {
        List<Pancake> pancakes = pancakeRepository.findByOrderId(orderId);
        Order order = findOrderById(orderId);
        pancakes.forEach(pancakeRepository::delete);
        orderRepository.findById(orderId).ifPresent(orderRepository::delete);
        OrderLog.logCancelOrder(order, pancakes.size());
    }

    public void completeOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        Order completedOrder = order.complete();
        orderRepository.save(completedOrder);
    }

    public Set<UUID> listCompletedOrders() {
        return orderRepository.findUUIDByStatus(OrderStatus.COMPLETED);
    }

    public void prepareOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        Order preparedOrder = order.prepare();
        orderRepository.save(preparedOrder);
    }

    public Set<UUID> listPreparedOrders() {
        return orderRepository.findUUIDByStatus(OrderStatus.PREPARED);
    }

    public List<Pancake> deliverOrder(UUID orderId) {
        Order order = findOrderById(orderId);

        if (order.getStatus() != OrderStatus.PREPARED) {
            return null;
        }

        List<Pancake> pancakes = pancakeRepository.findByOrderId(orderId).stream().toList();

        OrderLog.logDeliverOrder(order, pancakes.size());

        pancakes.forEach(pancakeRepository::delete);
        orderRepository.delete(order);

        return pancakes;
    }
}
