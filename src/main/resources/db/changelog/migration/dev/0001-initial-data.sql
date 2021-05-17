INSERT INTO product_item (id, title)
VALUES (1, 'Book'),
       (2, 'Pencil');

SELECT setval('product_item_id_seq', 2);
