CREATE TABLE IF NOT EXISTS customer (
    customer_id INTEGER AUTO_INCREMENT (1000),
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    surname VARCHAR(30) NOT NULL,
    email_address VARCHAR(50) NOT NULL,
    shipping_address VARCHAR(70) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
    product_id VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(100),
    category VARCHAR(30) NOT NULL,
    list_price NUMERIC(8, 2) NOT NULL,
    quantity_in_stock INTEGER NOT NULL,
    
    CONSTRAINT check_list_price_positive CHECK list_price > 0,
    CONSTRAINT check_quantity_in_stock_positive CHECK quantity_in_stock > 0
);

