DROP DATABASE IF exists silmara;
CREATE DATABASE silmara;
USE silmara;
SELECT DATABASE();

CREATE TABLE customer(
	customer_id INT NOT NULL AUTO_INCREMENT, 
    first_name VARCHAR(45) NOT NULL, 
    last_name VARCHAR(45) NOT NULL, 
    email VARCHAR(45) NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT '1',
    create_date datetime NOT NULL,
	PRIMARY KEY (customer_id)
);

CREATE TABLE product_categories(
	category_id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(45) NOT NULL,
    PRIMARY KEY (category_id)
);

CREATE TABLE products(
	product_id INT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(45) NOT NULL,
    product_description VARCHAR(100) NOT NULL,
    product_price FLOAT NOT NULL, 
    product_stock INT NOT NULL DEFAULT 0, 
    product_category_id INT NOT NULL,
    PRIMARY KEY (product_id),
    FOREIGN KEY (product_category_id) REFERENCES product_categories(category_id)
);

INSERT INTO product_categories(category_name) VALUES("cadenas lentes"),( "collares"),( "aretes"),( "anillos"),( "pulseras");

INSERT INTO products(product_name,product_description,product_price, product_stock, product_category_id) VALUES
	("Ojo Turco Blanco","Chapa de oro de 18k y 3 micras de recubrimiento.",149.99,20, 1 ),("Ojo Turco Negro","Chapa de oro de 18k y 3 micras de recubrimiento.",199.99,25, 1),
    ("Abulón","Chapa de oro de 18k y 3 micras de recubrimiento.",249.99,20, 1),("Flecha","Chapa de oro de 18k y 3 micras de recubrimiento.",149.99,20, 1),
    ("Akamba","Chapa de oro de 18k y 3 micras de recubrimiento.",449.99,27, 1),("Cartier","Chapa de oro de 18k y 3 micras de recubrimiento.",349.99,30, 1),
    ("Anillo 1","Anillo de Oro",399.99,15, 4), ("Anillo 2","Anillo de Plata",799.99,15, 4), ("Aretes 1","Aretes fantasía",60.99,15, 3), ("Aretes 2","Aretes Oro",999.99,15, 3);
    
/*UPDATE products SET product_name = ?, product_description = ?, product_price = ?, product_stock = ?, product_category_id = ? WHERE product_id = ?;*/    
    
TRUNCATE TABLE product_categories;
TRUNCATE TABLE products;

SELECT * FROM product_categories;

SELECT * FROM products;

-- SELECT to get product details and category
SELECT 
	product_id AS id, 
    product_name AS name, 
    product_description AS description, 
    product_price AS price, 
    product_stock AS stock, 
    category_name AS category
FROM products p
JOIN product_categories pc
	ON p.product_category_id =pc.category_id
ORDER BY p.product_id ASC;

-- SELECT to get Category id and name.
SELECT category_id AS id, category_name AS name FROM product_categories;

-- SELECT to get product by Category.
SELECT 
	product_id AS id, 
    product_name AS name, 
    product_description AS description, 
    product_price AS price, 
    product_stock AS stock, 
    category_name AS category
FROM products p
JOIN product_categories pc
	ON p.product_category_id =pc.category_id
WHERE category_name IN ("aretes","", "", "", "")
ORDER BY p.product_id ASC;

SELECT 
	product_id AS id, 
    product_name AS name, 
    product_description AS description, 
    product_price AS price, 
    product_stock AS stock, 
    category_name AS category
FROM products p
JOIN product_categories pc
	ON p.product_category_id =pc.category_id
WHERE category_name LIKE "aretes"
ORDER BY p.product_id ASC;

