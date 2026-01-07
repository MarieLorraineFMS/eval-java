-- schema.sql
CREATE DATABASE IF NOT EXISTS platform DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

USE platform;

-- /////////////////////////////////
-- TRAINING
-- /////////////////////////////////
CREATE TABLE IF NOT EXISTS training (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    onsite BOOLEAN NOT NULL,
    duration_days INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE INDEX idx_training_onsite ON training (onsite);

CREATE INDEX idx_training_name ON training (name);

-- /////////////////////////////////
-- USER ACCOUNT
-- /////////////////////////////////
CREATE TABLE IF NOT EXISTS user_account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- /////////////////////////////////
-- CLIENT
-- /////////////////////////////////
CREATE TABLE IF NOT EXISTS client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(30) NOT NULL
);

-- /////////////////////////////////
-- CART
-- /////////////////////////////////
CREATE TABLE IF NOT EXISTS cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES user_account (id) ON DELETE CASCADE
);

-- /////////////////////////////////
-- CART ITEM
-- /////////////////////////////////
CREATE TABLE IF NOT EXISTS cart_item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    training_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_training FOREIGN KEY (training_id) REFERENCES training (id),
    UNIQUE KEY uq_cart_training (cart_id, training_id)
);

CREATE INDEX idx_cart_item_cart ON cart_item (cart_id);

-- /////////////////////////////////
-- ORDER
-- /////////////////////////////////
CREATE TABLE IF NOT EXISTS order (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    client_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM(
        'DRAFT',
        'CONFIRMED',
        'CANCELLED'
    ) NOT NULL DEFAULT 'CONFIRMED',
    total DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES user_account (id),
    CONSTRAINT fk_order_client FOREIGN KEY (client_id) REFERENCES client (id)
);

CREATE INDEX idx_order_user ON order (user_id);

CREATE INDEX idx_order_client ON order (client_id);

-- /////////////////////////////////
-- ORDER LINE
-- /////////////////////////////////
CREATE TABLE IF NOT EXISTS order_line (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    training_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order_line_order FOREIGN KEY (order_id) REFERENCES order (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_line_training FOREIGN KEY (training_id) REFERENCES training (id)
);

CREATE INDEX idx_order_line_order ON order_line (order_id);