-- Insert product if not exists
INSERT INTO product (id, name, price, description, price_discount_percentage, is_free_shipping)
SELECT 'MLA18955734', 'Eau de Perfum Natura Homem Potence 100 mL', 70170.0,
'Información importante\nProducto vegano\nIntensidad alta\nOcasiones especiales\nAmaderado intenso\n\nCelebra todas las maneras de ser hombre Una moderna combinación de notas de cumarú, ingrediente natural de la biodiversidad brasileña, y maderas calientes con un toque apimentado.\nEl resultado es una fragancia osada para el hombre que sabe lo que quiere.\nTodos tenemos una manera especial de perfumarnos. Pero para aprovechar todo el potencial de la fragancia, nuestra recomendación es aplicarla en áreas como puños, cuello y detrás de las orejas.',
29, true
WHERE NOT EXISTS (SELECT 1 FROM product WHERE id = 'MLA18955734');

-- Insert example user with id = 1 if not exists
INSERT INTO user (id, first_name, last_name, email, password)
SELECT 1, 'John', 'Doe', 'john.doe@example.com', 'password123'
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id = 1);

-- Insert product attributes if not exists
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'APPLICATION_FORMAT', 'Formato de aplicación', 'Spray', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'APPLICATION_FORMAT' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'IS_ALCOHOL_FREE', 'Es libre de alcohol', 'No', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'IS_ALCOHOL_FREE' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'IS_CRUELTY_FREE', 'Es libre de crueldad', 'Sí', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'IS_CRUELTY_FREE' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'IS_REFILLABLE', 'Es recargable', 'No', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'IS_REFILLABLE' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'IS_VEGAN', 'Es vegano', 'Sí', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'IS_VEGAN' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'LINE', 'Línea', 'Homem', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'LINE' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'OLFACTORY_FAMILY', 'Familia olfativa', 'Amaderado intenso', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'OLFACTORY_FAMILY' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'OLFACTORY_NOTES', 'Notas olfativas', 'Haba tonka, Madera caliente, Pimienta', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'OLFACTORY_NOTES' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'ORIGIN_COUNTRY', 'País de origen', 'Brasil', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'ORIGIN_COUNTRY' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'PERFUME_NAME', 'Nombre del perfume', 'Homem', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'PERFUME_NAME' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'PERFUME_TYPE', 'Tipo de perfume', 'Eau de parfum', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'PERFUME_TYPE' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'RELEASE_YEAR', 'Año de lanzamiento', '2018', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'RELEASE_YEAR' AND product_id = 'MLA18955734');
INSERT INTO product_attribute (id, name, value, product_id)
SELECT 'UNIT_VOLUME', 'Volumen de la unidad', '100 mL', 'MLA18955734'
WHERE NOT EXISTS (SELECT 1 FROM product_attribute WHERE id = 'UNIT_VOLUME' AND product_id = 'MLA18955734');

-- Insert product pictures if not exists
INSERT INTO product_pictures (product_id, pictures)
SELECT 'MLA18955734', 'https://http2.mlstatic.com/D_NQ_NP_636735-MLU72564880794_112023-F.jpg'
WHERE NOT EXISTS (SELECT 1 FROM product_pictures WHERE product_id = 'MLA18955734' AND pictures = 'https://http2.mlstatic.com/D_NQ_NP_636735-MLU72564880794_112023-F.jpg');
INSERT INTO product_pictures (product_id, pictures)
SELECT 'MLA18955734', 'https://http2.mlstatic.com/D_NQ_NP_607890-MLU69972226079_062023-F.jpg'
WHERE NOT EXISTS (SELECT 1 FROM product_pictures WHERE product_id = 'MLA18955734' AND pictures = 'https://http2.mlstatic.com/D_NQ_NP_607890-MLU69972226079_062023-F.jpg');
INSERT INTO product_pictures (product_id, pictures)
SELECT 'MLA18955734', 'https://http2.mlstatic.com/D_NQ_NP_994630-MLU69953357248_062023-F.jpg'
WHERE NOT EXISTS (SELECT 1 FROM product_pictures WHERE product_id = 'MLA18955734' AND pictures = 'https://http2.mlstatic.com/D_NQ_NP_994630-MLU69953357248_062023-F.jpg');

-- Insert commentary if not exists
INSERT INTO commentary (product_id, user_id, comment, created_at)
SELECT 'MLA18955734', 1, 'tiene pinta', '2025-05-08T15:45:44.147445'
WHERE NOT EXISTS (SELECT 1 FROM commentary WHERE product_id = 'MLA18955734' AND user_id = 1 AND created_at = '2025-05-08T15:45:44.147445');

-- Insert reviews if not exists
INSERT INTO review (product_id, user_id, rating, comment)
SELECT 'MLA18955734', 1, 5, 'buenisimo'
WHERE NOT EXISTS (SELECT 1 FROM review WHERE product_id = 'MLA18955734' AND user_id = 1 AND rating = 5 AND comment = 'buenisimo');
INSERT INTO review (product_id, user_id, rating, comment)
SELECT 'MLA18955734', 1, 4, 'esta es una review generica'
WHERE NOT EXISTS (SELECT 1 FROM review WHERE product_id = 'MLA18955734' AND user_id = 1 AND rating = 4 AND comment = 'esta es una review generica');
INSERT INTO review (product_id, user_id, rating, comment)
SELECT 'MLA18955734', 1, 3, 'masomenos'
WHERE NOT EXISTS (SELECT 1 FROM review WHERE product_id = 'MLA18955734' AND user_id = 1 AND rating = 3 AND comment = 'masomenos');
INSERT INTO review (product_id, user_id, rating, comment)
SELECT 'MLA18955734', 1, 1, 'es una porqueria'
WHERE NOT EXISTS (SELECT 1 FROM review WHERE product_id = 'MLA18955734' AND user_id = 1 AND rating = 1 AND comment = 'es una porqueria');
