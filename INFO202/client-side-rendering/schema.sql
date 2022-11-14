CREATE TABLE IF NOT EXISTS customer
(
    customer_id      INTEGER AUTO_INCREMENT (1000),
    username         VARCHAR(30) NOT NULL UNIQUE,
    password         VARCHAR(30) NOT NULL,
    first_name       VARCHAR(20) NOT NULL,
    surname          VARCHAR(30) NOT NULL,
    email_address    VARCHAR(50) NOT NULL,
    shipping_address VARCHAR(70) NOT NULL,
    PRIMARY KEY (customer_id)
);
CREATE TABLE IF NOT EXISTS product
(
    product_id        VARCHAR(20)   NOT NULL,
    name              VARCHAR(40)   NOT NULL,
    description       VARCHAR(100),
    category          VARCHAR(30)   NOT NULL,
    list_price        NUMERIC(8, 2) NOT NULL,
    quantity_in_stock INTEGER       NOT NULL,

    CONSTRAINT check_list_price_positive CHECK list_price > 0,
    CONSTRAINT check_quantity_in_stock_positive CHECK quantity_in_stock >= 0,
    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS sale
(
    sale_id     INTEGER AUTO_INCREMENT (1000),
    date        DATETIME DEFAULT CURRENT_TIMESTAMP(),
    customer_id INTEGER NOT NULL,
    status      VARCHAR(20) NOT NULL,

    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    PRIMARY KEY (sale_id)


);

CREATE TABLE IF NOT EXISTS sale_item
(
    quantity_purchased INTEGER       NOT NULL,
    sale_price          NUMERIC(8, 2) NOT NULL,
    product_id         INTEGER       NOT NULL,
    sale_id INTEGER NOT NULL ,

    FOREIGN KEY (product_id) REFERENCES product (product_id),
    FOREIGN KEY (sale_id) REFERENCES sale(sale_id)
);


