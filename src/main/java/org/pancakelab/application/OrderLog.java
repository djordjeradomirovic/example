package org.pancakelab.application;

import org.pancakelab.domain.Order;

public class OrderLog {

    private OrderLog() {
    }

    private static final StringBuilder log = new StringBuilder();

    public static void logAddPancake(Order order, String description, int pancakeSize) {
        log.append("Added pancake with description '%s' ".formatted(description))
                .append("to order %s containing %d pancakes, ".formatted(order.getId(), pancakeSize))
                .append("for building %d, room %d.".formatted(order.getBuilding(), order.getRoom()));
    }

    public static void logRemovePancakes(Order order, String description, int count, int pancakeSize) {
        log.append("Removed %d pancake(s) with description '%s' ".formatted(count, description))
                .append("from order %s now containing %d pancakes, ".formatted(order.getId(), pancakeSize))
                .append("for building %d, room %d.".formatted(order.getBuilding(), order.getRoom()));
    }

    public static void logCancelOrder(Order order, int pancakeSize) {
        log.append("Cancelled order %s with %d pancakes ".formatted(order.getId(), pancakeSize))
                .append("for building %d, room %d.".formatted(order.getBuilding(), order.getRoom()));
    }

    public static void logDeliverOrder(Order order, int pancakeSize) {
        log.append("Order %s with %d pancakes ".formatted(order.getId(), pancakeSize))
                .append("for building %d, room %d out for delivery.".formatted(order.getBuilding(), order.getRoom()));
    }
}
