-- Creating CuisineType table
CREATE TABLE IF NOT EXISTS CuisineType (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(255) NOT NULL UNIQUE
);

-- Creating Status table
CREATE TABLE IF NOT EXISTS Status (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL UNIQUE
);

-- Creating RoleType table
CREATE TABLE IF NOT EXISTS RoleType (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL UNIQUE
);

-- Creating Ingredients table
CREATE TABLE IF NOT EXISTS Ingredients (
                                           ingredient_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           ingredient_name VARCHAR(255) NOT NULL,
                                           description TEXT
);

-- Creating Users table
CREATE TABLE IF NOT EXISTS Users (
                                     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     email VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role VARCHAR(255) NOT NULL
);

-- Creating Restaurants table
CREATE TABLE IF NOT EXISTS Restaurants (
                                           restaurant_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           restaurant_name VARCHAR(255) NOT NULL,
                                           address VARCHAR(255) NOT NULL,
                                           cuisine_type VARCHAR(255) NOT NULL,
                                           description TEXT,
                                           restaurant_image_path VARCHAR(255),
                                           number_of_orders INT
);

-- Creating Products table
CREATE TABLE IF NOT EXISTS Products (
                                        product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        product_name VARCHAR(255) NOT NULL,
                                        cuisine_type VARCHAR(255) NOT NULL,
                                        restaurant_id BIGINT,
                                        description TEXT,
                                        price DECIMAL(10,2) NOT NULL,
                                        product_image_path VARCHAR(255),
                                        FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id)
);

-- Creating Orders table
CREATE TABLE IF NOT EXISTS Orders (
                                      order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      user_id BIGINT,
                                      restaurant_id BIGINT,
                                      order_date DATETIME NOT NULL,
                                      total_amount DECIMAL(10,2) NOT NULL,
                                      status VARCHAR(255) NOT NULL,
                                      FOREIGN KEY (user_id) REFERENCES Users(user_id),
                                      FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id)
);

-- Creating Payments table
CREATE TABLE IF NOT EXISTS Payments (
                                        payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        order_id BIGINT,
                                        payment_date DATETIME NOT NULL,
                                        amount DECIMAL(10,2) NOT NULL,
                                        card_number VARCHAR(255) NOT NULL,
                                        card_holder_name VARCHAR(255) NOT NULL,
                                        card_expiry_date DATE NOT NULL,
                                        card_cvv VARCHAR(255) NOT NULL,
                                        FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

-- Creating OrderDetails table
CREATE TABLE IF NOT EXISTS OrderDetails (
                                            order_detail_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            product_id BIGINT,
                                            order_id BIGINT,
                                            quantity INT NOT NULL,
                                            price DECIMAL(10,2) NOT NULL,
                                            FOREIGN KEY (product_id) REFERENCES Products(product_id),
                                            FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

-- Creating ProductIngredients table
CREATE TABLE IF NOT EXISTS ProductIngredients (
                                                  product_ingredients_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                  product_id BIGINT,
                                                  ingredient_id BIGINT,
                                                  quantity INT NOT NULL,
                                                  FOREIGN KEY (product_id) REFERENCES Products(product_id),
                                                  FOREIGN KEY (ingredient_id) REFERENCES Ingredients(ingredient_id)
);

-- Inserting CuisineType enum values
INSERT INTO CuisineType (name) VALUES
                                   ('MEXICAN'),
                                   ('AMERICAN'),
                                   ('ITALIAN'),
                                   ('CHINESE'),
                                   ('JAPANESE'),
                                   ('INDIAN'),
                                   ('FRENCH'),
                                   ('MEDITERRANEAN'),
                                   ('OTHER');

-- Inserting Status enum values
INSERT INTO Status (name) VALUES
                              ('PENDING'),
                              ('PROCESSING'),
                              ('DONE'),
                              ('DELIVERED'),
                              ('CANCELLED');

-- Inserting RoleType enum values
INSERT INTO RoleType (name) VALUES
                                ('ADMIN'),
                                ('CUSTOMER'),
                                ('EMPLOYEE');

-- Inserting Ingredients
INSERT INTO Ingredients (ingredient_name, description) VALUES
                                                           ('Ingredient1', 'Description1'),
                                                           ('Ingredient2', 'Description2'),
                                                           ('Ingredient3', 'Description3');

-- Inserting Users
INSERT INTO Users (email, password, role) VALUES
                                              ('user1@example.com', 'password1', 'CUSTOMER'),
                                              ('user2@example.com', 'password2', 'CUSTOMER'),
                                              ('admin@example.com', 'adminpassword', 'ADMIN');

-- Inserting Restaurants
INSERT INTO Restaurants (restaurant_name, address, cuisine_type, description, restaurant_image_path, number_of_orders) VALUES
                                                                                                                           ('Restaurant1', 'Address1', 'MEXICAN', 'Description1', 'image1.jpg', 0),
                                                                                                                           ('Restaurant2', 'Address2', 'AMERICAN', 'Description2', 'image2.jpg', 0),
                                                                                                                           ('Restaurant3', 'Address3', 'ITALIAN', 'Description3', 'image3.jpg', 0);

-- Inserting Products
INSERT INTO Products (product_name, cuisine_type, restaurant_id, description, price, product_image_path) VALUES
                                                                                                             ('Product1', 'MEXICAN', 1, 'Description1', 10.99, 'product_image1.jpg'),
                                                                                                             ('Product2', 'AMERICAN', 2, 'Description2', 12.99, 'product_image2.jpg'),
                                                                                                             ('Product3', 'ITALIAN', 3, 'Description3', 15.99, 'product_image3.jpg');

-- Inserting Orders
INSERT INTO Orders (user_id, restaurant_id, order_date, total_amount, status) VALUES
                                                                                  (1, 1, NOW(), 25.99, 'DONE'),
                                                                                  (2, 2, NOW(), 30.99, 'DELIVERED'),
                                                                                  (3, 3, NOW(), 20.99, 'PENDING');

-- Inserting Payments
INSERT INTO Payments (order_id, payment_date, amount, card_number, card_holder_name, card_expiry_date, card_cvv) VALUES
                                                                                                                     (1, NOW(), 25.99, '1234567890123456', 'John Doe', '2024-12-31', '123'),
                                                                                                                     (2, NOW(), 30.99, '1234567890123456', 'Jane Doe', '2025-12-31', '456'),
                                                                                                                     (3, NOW(), 20.99, '1234567890123456', 'Admin', '2026-12-31', '789');

-- Inserting OrderDetails
INSERT INTO OrderDetails (product_id, order_id, quantity, price) VALUES
                                                                     (1, 1, 2, 20.99),
                                                                     (2, 2, 3, 30.99),
                                                                         (3, 3, 1, 15.99);

-- Inserting ProductIngredients
INSERT INTO ProductIngredients (product_id, ingredient_id, quantity) VALUES
                                                                         (1, 1, 2),
                                                                         (2, 2, 3),
                                                                         (3, 3, 1);