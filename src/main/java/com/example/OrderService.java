package com.example;

import java.util.Optional;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public String processOrder(Order order) {
        if (order == null) {
            return "fail";
        }
        try {
            int orderId = orderRepository.saveOrder(order);
            if (orderId > 0) {
                return "success";
            } else {
                return "fail";
            }


        } catch (Exception e) {
            return "fail";
        }

    }

//    public String processOrder(Order order) {
//        if (order == null) {
//            return "fail";
//        }
//        int orderId = orderRepository.saveOrder(order);
//        if (orderId > 0) {
//            return "success";
//        } else {
//            return "fail";
//        }
//
//    }

    public double calculateTotal(int id) {

        Optional<Order> orderOptional = orderRepository.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return order.getTotalPrice();
        } else {
            throw new IllegalArgumentException("Заказ с ID" + id + "Не найден");
        }

    }


}
