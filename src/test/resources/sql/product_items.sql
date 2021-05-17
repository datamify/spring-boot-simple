INSERT INTO product_item (id, title)
VALUES (1, 'Test Book'),
       (2, 'Test Pencil');

SELECT setval('product_item_id_seq', 2);
