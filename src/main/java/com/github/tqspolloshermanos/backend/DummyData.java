package com.github.tqspolloshermanos.backend;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.tqspolloshermanos.backend.Entities.ECuisineType;
import com.github.tqspolloshermanos.backend.Entities.ERole;
import com.github.tqspolloshermanos.backend.Entities.EStatus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DummyData {
    
    private static final String BASE_URL = "http:/localhost:8080/api";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();
    private static final Logger logger = LogManager.getLogger(DummyData.class.getName());

    public static void main(String[] args) {
        try{
            List<Integer> userIds = insertUsers();
            List<Integer> restaurantIds = insertRestaurants();
            List<Integer> ingredientIds = insertIngredients();
            Map<Integer, Map<List<Integer>, List<Double>>> restaurantProductMap = new HashMap<>();
            for (int restaurantId: restaurantIds) {
                Map<List<Integer>, List<Double>> productIdsAndPrices = insertProducts(ingredientIds, restaurantId);
                restaurantProductMap.put(restaurantId, productIdsAndPrices);
            }
            Map<List<Integer>, List<Double>> orderIdsAndAmount = insertOrders(userIds, restaurantProductMap);
            insertPayments(orderIdsAndAmount);
        } catch (Exception e) {
            logger.error("An error ocurred during data insertion: " + e.getMessage(), e);
            System.err.println("An error ocurred during data insertion.");
        }
    }

    // insert dummy data for users
    private static List<Integer> insertUsers() throws Exception {
        List<Integer> userIds = new ArrayList<>();
        String[] customerEmails = {"customer1@example.com", "customer2@example.com", "customer3@example.com", "customer4@example.com"};
        String[] employeeEmails = {"employee1@example.com", "employee2@example.com", "employee3@example.com"};
        String password = "password123";

        // create admin
        JsonObject admin = createUserJsonObject("admin@example.com", password, ERole.ADMIN);
        userIds.add(insertUser(admin));

        // create customers
        for (String email: customerEmails) {
            JsonObject customer = createUserJsonObject(email, password, ERole.CUSTOMER);
            userIds.add(insertUser(customer));
        }

        // create employees
        for (String email: employeeEmails) {
            JsonObject employee = createUserJsonObject(email, password, ERole.EMPLOYEE);
            userIds.add(insertUser(employee));
        }
        
        return userIds;
    }

    private static JsonObject createUserJsonObject(String email, String password, ERole role) {
        JsonObject user = new JsonObject();
        user.addProperty("email", email);
        user.addProperty("password", password);
        // user.addProperty("role", role.toString()); NOTE: I DON'T KNOW IF IT IS SUPPOSED TO ENTER OR NOT
        return user;
    }

    private static int insertUser(JsonObject user) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/users/register"))
            .header("Content-Type", "/application/json")
            .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Inserted User: " + response.body());

        // extract the user ID from the response
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
        return jsonResponse.get("id").getAsInt();
    }

    // insert dummy data for restaurants
    private static List<Integer> insertRestaurants() throws Exception {
        List<Integer> restaurantIds = new ArrayList<>();
        String[] names = {"Mexican Fiesta", "Italian Bistro", "Indian Palace", "Sushi House", "BBQ Grill", "Pizza Paradise", "Gourmet Delights", "American Diner", "Exotic Eats", "Chinese Garden"};
        String[] addresses = {"123 Main St", "456 Oak Ave", "789 Pine Rd", "101 Elm St", "202 Maple Ave", "345 Cedar St", "678 Pinecrest Blvd", "901 Oakwood Ln", "234 Elmwood Dr", "567 Birchwood Rd"};
        ECuisineType[] cuisineTypes = {ECuisineType.MEXICAN, ECuisineType.ITALIAN, ECuisineType.INDIAN, ECuisineType.JAPANESE, ECuisineType.AMERICAN, ECuisineType.ITALIAN, ECuisineType.OTHER, ECuisineType.AMERICAN, ECuisineType.OTHER, ECuisineType.CHINESE};       
        String[] descriptions = {"Authentic Mexican cuisine in a vibrant atmosphere", "Classic Italian dishes with a modern twist", "Traditional Indian flavors cooked to perfection", "Fresh sushi made with the finest ingredients", "Savor the taste of BBQ at its best", "Indulge in the best pizza in town", "Explore gourmet dishes from around the world", "All-American favorites served with a smile", "Discover exotic flavors from distant lands", "Experience the richness of Chinese cuisine"};
       
        for (int i = 0; i < names.length; i++) {
            JsonObject restaurant = new JsonObject();
            restaurant.addProperty("name", names[i]);
            restaurant.addProperty("address", addresses[i]);
            restaurant.addProperty("cuisineType", cuisineTypes[i].toString());
            restaurant.addProperty("description", descriptions[i]);
            restaurant.addProperty("imagePath", "/images/" + names[i].toLowerCase().replace(" ", "_") + ".jpg");
            restaurant.addProperty("numberOfOrders", 0);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/restaurants"))
                .header("Content-Type", "/applications/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(restaurant)))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Inserted Restaurant: " + response.body());

            // extract the restaurant ID from the response
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            restaurantIds.add(jsonResponse.get("id").getAsInt());
        }

        return restaurantIds;
    }

    // insert dummy data for ingredients
    private static List<Integer> insertIngredients() throws Exception {
        List<Integer> ingredientIds = new ArrayList<>();
        String[] names = {"Tomato", "Cheese", "Chicken", "Lettuce", "Onion", "Beef", "Pork", "Fish", "Mushroom", "Potato", "Carrot", "Broccoli", "Garlic", "Oregano", "Chili Pepper"};
        String[] descriptions = {"Fresh Tomato", "Mozzarella Cheese", "Grilled Chicken", "Crisp Lettuce", "Sliced Onion", "Ground Beef", "Pulled Pork", "Fresh Fish Fillet", "Sliced Mushroom", "Russet Potato", "Carrot Sticks", "Broccoli Florets", "Minced Garlic", "Dried Oregano", "Sliced Chili Pepper"};

        for (int i = 0; i < names.length; i++) {
            JsonObject ingredients = new JsonObject();
            ingredients.addProperty("name", names[i]);
            ingredients.addProperty("description", descriptions[i]);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/ingredients"))
                .header("Content-Type", "/applications/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(ingredients)))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Inserted Ingredient: " + response.body());

            // extract the ingredient ID from the response
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            ingredientIds.add(jsonResponse.get("id").getAsInt());
        }

        return ingredientIds;
    }

    // insert dummy data for products
    private static Map<List<Integer>, List<Double>> insertProducts(List<Integer> ingredientIds, int restaurantId) throws Exception {
        List<Integer> productIds = new ArrayList<>();
        List<Double> productPrices = new ArrayList<>();
        Map<List<Integer>, List<Double>> productIdsAndPrices = new HashMap<>();
        String[] names = {"Taco", "Pizza", "Butter Chicken", "Sushi Roll", "BBQ Ribs", "Caprese Salad", "Pad Thai", "Hamburger", "Fish and Chips", "Vegetable Stir Fry"};
        ECuisineType[] cuisineTypes = {ECuisineType.MEXICAN, ECuisineType.ITALIAN, ECuisineType.INDIAN, ECuisineType.JAPANESE, ECuisineType.AMERICAN, ECuisineType.ITALIAN, ECuisineType.OTHER, ECuisineType.AMERICAN, ECuisineType.OTHER, ECuisineType.CHINESE};
        String[] descriptions = {"Delicious Taco with Seasoned Meat and Fresh Salsa", "Classic Pizza with Mozzarella Cheese and Tomato Sauce", "Creamy Butter Chicken with Fragrant Spices", "Sushi Roll with Fresh Fish and Sticky Rice", "Tender BBQ Ribs with Smoky Flavor", "Fresh Caprese Salad with Tomatoes, Mozzarella, and Basil", "Authentic Pad Thai with Tofu, Peanuts, and Tamarind Sauce", "Juicy Hamburger with Lettuce, Tomato, and Pickles", "Crispy Fish and Chips with Tartar Sauce", "Colorful Vegetable Stir Fry with Soy Sauce and Ginger"};
        double[] prices = {5.99, 8.99, 7.99, 9.99, 12.99, 6.99, 10.99, 4.99, 11.99, 8.49};
        Integer[][] productIngredientsIds = {
            {1, 6, 14},   // Taco: Tomato, Beef, Oregano
            {1, 2, 14},   // Pizza: Tomato, Cheese, Oregano
            {1, 3, 13},   // Butter Chicken: Tomato, Chicken, Garlic
            {1, 8, 14},   // Sushi Roll: Tomato, Fish, Oregano
            {1, 6, 14},   // BBQ Ribs: Tomato, Beef, Oregano
            {1, 2, 14},   // Caprese Salad: Tomato, Cheese, Oregano
            {1, 3, 13},   // Pad Thai: Tomato, Chicken, Garlic
            {1, 6, 14},   // Hamburger: Tomato, Beef, Oregano
            {1, 8, 14},   // Fish and Chips: Tomato, Fish, Oregano
            {1, 11, 14}   // Vegetable Stir Fry: Tomato, Potato, Oregano
        };

        // Filter the cuisine type of the restaurant
        ECuisineType thisRestaurantCuisineType = getRestaurantCuisineType(restaurantId);
        List<Integer> filteredProductIndexes = new ArrayList<>();
        for (int i = 0; i < cuisineTypes.length; i++) {
            if (cuisineTypes[i] == thisRestaurantCuisineType) {
                filteredProductIndexes.add(i);
            }
        }

        Random random = new Random();
        for (int i: filteredProductIndexes) {
            JsonObject product = new JsonObject();
            product.addProperty("name", names[i]);
            product.addProperty("cuisineType", cuisineTypes[i].toString());
            product.addProperty("restaurantId", restaurantId);
            product.addProperty("description", descriptions[i]);
            product.addProperty("price", prices[i]);
            product.addProperty("imagePath", "/images/" + names[i].toLowerCase().replace(" ", "_") + ".jpg");

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/products"))
                .header("Content-Type", "/applications/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(product)))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Inserted Product: " + response.body());

            // extract the product ID from the response
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            int productId = jsonResponse.get("id").getAsInt();
            productIds.add(productId);
            productPrices.add(prices[i]);

            // Add ingredients to the product
            for (Integer ingredientId: productIngredientsIds[i]) {
                JsonObject productIngredient = new JsonObject();
                productIngredient.addProperty("productId", productId);
                productIngredient.addProperty("ingredientId", ingredientId);
                productIngredient.addProperty("quantity", 1);

                HttpRequest ingredientRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/products" + productId + "/ingredients"))
                    .header("Content-Type", "/applications/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(productIngredient)))
                    .build();

                HttpResponse<String> ingredientResponse = client.send(ingredientRequest, HttpResponse.BodyHandlers.ofString());
                logger.info("Inserted Product Ingredient: " + ingredientResponse.body());
            }
        }

        productIdsAndPrices.put(productIds, productPrices);

        return productIdsAndPrices;
    }

    private static ECuisineType getRestaurantCuisineType(int restaurantId) {
        if (restaurantId < 0 || restaurantId > 9) {
            return ECuisineType.OTHER;
        }
        ECuisineType[] cuisineTypes = {ECuisineType.MEXICAN, ECuisineType.ITALIAN, ECuisineType.INDIAN, ECuisineType.JAPANESE, ECuisineType.AMERICAN, ECuisineType.ITALIAN, ECuisineType.OTHER, ECuisineType.AMERICAN, ECuisineType.OTHER, ECuisineType.CHINESE}; 
        return cuisineTypes[restaurantId - 1];
    }

    // insert dummy data for orders
    private static Map<List<Integer>, List<Double>> insertOrders(List<Integer> userIds,Map<Integer, Map<List<Integer>, List<Double>>> restaurantProductMap) throws Exception {
        List<Integer> orderIds = new ArrayList<>();
        List<Double> orderAmounts = new ArrayList<>();
        Map<List<Integer>, List<Double>> orderIdsAndAmount = new HashMap<>();
        Random random = new Random();

        // Select only the customers as users (2, 3, 4, and 5)
        List<Integer> selectedUserIds = userIds.stream()
                                                .filter(userId -> userId == 2 || userId == 3 || userId == 4 || userId == 5)
                                                .collect(Collectors.toList());

        // Restaurants
        List<Integer> restaurantsIds = new ArrayList<>(restaurantProductMap.keySet());

        // Generate 5 or less orders for each selected user
        for (Integer userId: selectedUserIds) {
            for (int i = 0; i < 5; i ++) {
                // Restaurant and its products
                int restaurantId = random.nextInt(restaurantsIds.size());
                Map<List<Integer>, List<Double>> productsAndPrices = new HashMap<>(restaurantProductMap.get(restaurantId));
                List<Integer> productIds = new ArrayList<>(productsAndPrices.keySet().iterator().next());
                List<Double> productPrices = new ArrayList<>(productsAndPrices.values().iterator().next());

                // if the restaurant has no products, ignore this loop
                if (productIds.size() == 0) {
                    continue;
                }

                double totalAmount = 0.0;

                // Generate random order date within the last 30 days
                LocalDate currentDate = LocalDate.now();
                LocalDate orderDate = currentDate.minusDays(random.nextInt(30));
                // Get a random status
                EStatus[] statuses = EStatus.values();
                EStatus status = statuses[random.nextInt(statuses.length)];

                // Get from 1 to 4 products from the restaurant
                int numberOfProducts = productIds.size();
                if (numberOfProducts > 4) {
                    numberOfProducts = 4;
                }
                List<Integer> thisProductsIds = new ArrayList<>();
                List<Integer> quantities = new ArrayList<>();
                for (int n = 0; n < numberOfProducts; n++) {
                    int r = random.nextInt(productIds.size());
                    if (!thisProductsIds.contains(r + 1)) {
                        thisProductsIds.add(r + 1);
                        quantities.add(random.nextInt(4));
                        totalAmount += productPrices.get(r + 1);
                    }
                }

                String orderDetail = getOrderDetailToString(thisProductsIds, quantities);

                JsonObject orders = new JsonObject();
                orders.addProperty("userId", userId);
                orders.addProperty("restaurantId", restaurantId);
                orders.addProperty("orderDate", orderDate.toString());
                orders.addProperty("totalAmount", totalAmount);
                orders.addProperty("status", status.toString());
                // orders.addProperty("orderDetail", orderDetail); NOTE: I DON'T KNOW IF IT IS SUPPOSED TO ENTER OR NOT

                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/orders/place"))
                    .header("Content-Type", "/applications/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(orders)))
                    .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                logger.info("Inserted Order: " + response.body());

                // extract the order ID from the response
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                orderIds.add(jsonResponse.get("id").getAsInt());

                orderAmounts.add(totalAmount);
            }
        }

        orderIdsAndAmount.put(orderIds, orderAmounts);
        return orderIdsAndAmount;
    }

    private static String getOrderDetailToString(List<Integer> thisProductsIds, List<Integer> quantities) {
        StringBuilder orderDetails = new StringBuilder();
        for (int i=0; i < thisProductsIds.size(); i++) {
            orderDetails.append("Product ID: ").append(thisProductsIds.get(i)).append("Quantity: ").append(quantities.get(i));
        }
        return orderDetails.toString();
    }

    private static List<Integer> insertPayments(Map<List<Integer>, List<Double>> orderIdsAndAmount) throws Exception {
        List<Integer> paymentIds = new ArrayList<>();
        List<Integer> orderIds = new ArrayList<>(orderIdsAndAmount.keySet().iterator().next());
        List<Double> ordersAmount = new ArrayList<>(orderIdsAndAmount.values().iterator().next());
        Random random = new Random();

        for (int i = 0; i < orderIds.size(); i++) {
            int orderId = orderIds.get(i);
            double amount = ordersAmount.get(i);
            LocalDate paymentDate = LocalDate.now();

            // Generate random card details
            String cardNumber = generateRandomCardNumber();
            String cardHolderName = generateRandomCardHolderName();
            LocalDate cardExpiryDate = generateRandomCardExpiryDate();
            String cardCVV = generateRandomCardCVV();
            
            JsonObject orders = new JsonObject();
            orders.addProperty("orderId", orderId);
            orders.addProperty("amount", amount);
            orders.addProperty("paymentDate", paymentDate.toString());
            orders.addProperty("cardNumber", cardNumber);
            orders.addProperty("cardHolderName", cardHolderName);
            orders.addProperty("cardExpiryDate", cardExpiryDate.toString());
            orders.addProperty("cardCVV", cardCVV);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/payments/process"))
                .header("Content-Type", "/applications/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(orders)))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Inserted Payment: " + response.body());

            // extract the payment ID from the response
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            paymentIds.add(jsonResponse.get("id").getAsInt());
        }

        return paymentIds;
    }

    private static String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
    
    private static String generateRandomCardHolderName() {
        String[] firstNames = {"John", "Emma", "Michael", "Olivia", "William", "Sophia", "James", "Isabella"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};
        Random random = new Random();
        return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }
    
    private static LocalDate generateRandomCardExpiryDate() {
        Random random = new Random();
        int year = LocalDate.now().getYear() + random.nextInt(5);
        int month = random.nextInt(12) + 1;
        return LocalDate.of(year, month, 1);
    }
    
    private static String generateRandomCardCVV() {
        StringBuilder cvv = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            cvv.append(random.nextInt(10));
        }
        return cvv.toString();
    }

}
