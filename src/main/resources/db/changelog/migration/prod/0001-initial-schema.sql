DROP TABLE IF EXISTS product_item;

CREATE TABLE product_item
(
    id    SERIAL,
    title VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
