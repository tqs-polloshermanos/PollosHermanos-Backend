package com.github.tqspolloshermanos.controllers;

import com.github.tqspolloshermanos.dtos.OrderItemDto;
import com.github.tqspolloshermanos.dtos.OrderDto;
import com.github.tqspolloshermanos.dtos.PlaceOrderDto;
import com.github.tqspolloshermanos.dtos.StatusChangeDto;
import com.github.tqspolloshermanos.entities.*;
import com.github.tqspolloshermanos.services.OrderService;
import com.github.tqspolloshermanos.services.ProductService;
import com.github.tqspolloshermanos.services.RestaurantService;
import com.github.tqspolloshermanos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService, RestaurantService restaurantService, UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrderHistory(@AuthenticationPrincipal User user) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        }

        List<OrderDto> orderDtos = new ArrayList<>();
        orderService.getUserOrders(user).forEach((order -> orderDtos.add(new OrderDto(order))));
        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Order order = orderOpt.get();

        // the user should not know about other users' orders
        if (!userService.isUserEmployee(user) && !Objects.equals(order.getUser().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(new OrderDto(order));
    }

    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getOrdersByRestaurantAndStatus(@PathVariable Long restaurantId, @RequestBody List<EOrderStatus> statuses) {
        Optional<Restaurant> restaurantOpt = restaurantService.findRestaurantById(restaurantId);
        if (restaurantOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Set<EOrderStatus> statusSet = new HashSet<>(statuses);

        List<OrderDto> orderDtos = new ArrayList<>();
        orderService.getRestaurantOrders(restaurantId, statusSet).forEach((order -> orderDtos.add(new OrderDto(order))));

        return ResponseEntity.ok(orderDtos);
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
                                               @RequestBody StatusChangeDto body) {
        if (body.getStatus() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status is required");
        }

        EOrderStatus newStatus;
        try {
            newStatus = EOrderStatus.valueOf(body.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status value");
        }

        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        orderService.updateOrderStatus(orderOpt.get(), newStatus);
        return ResponseEntity.ok("Order status updated successfully");
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal User user, @RequestBody PlaceOrderDto placeOrderDto) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        if (orderService.userHasPendingOrders(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User has did not pay for past orders");
        }

        List<OrderItemDto> items = placeOrderDto.getItems();
        Long restaurantId = placeOrderDto.getRestaurantId();

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order has to have at least one item");
        }

        Optional<Restaurant> restaurantOpt = restaurantService.findRestaurantById(restaurantId);
        if (restaurantOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant is not found");
        }

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurantOpt.get());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(EOrderStatus.PENDING);

        for (OrderItemDto dto : items) {

            if (dto.getQuantity() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item quantity cannot be 0");
            }

            Optional<Product> productOpt = productService.findById(dto.getProductId());
            if (productOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            if (!Objects.equals(productOpt.get().getRestaurant().getId(), restaurantId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Products have to be from the same restaurant");
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(productOpt.get());
            item.setQuantity(dto.getQuantity());
            order.getOrderItems().add(item);
        }

        order = orderService.saveOrder(order);
        return ResponseEntity.ok(new OrderDto(order));
    }

    @PostMapping("/payment/{orderId}")
    public ResponseEntity<String> payForOrder(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        if (orderService.isOrderPaidFor(orderOpt.get())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Order has been already paid for");
        }

        if (!userService.isUserEmployee(user) && !orderService.isUserOwner(user, orderOpt.get())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This order does not belong to this user");
        }

        orderService.updateOrderStatus(orderOpt.get(), EOrderStatus.PROCESSING);
        return ResponseEntity.ok("Order paid for successfully");
    }

}
