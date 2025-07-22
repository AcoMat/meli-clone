-- H2 2.3.232;
;
CREATE USER IF NOT EXISTS "SA" SALT '40e25bf312a1a3a8' HASH 'e456f32641a24563175ed1615b8fc54f82cb4b01fde40fe7739a044bc3b9c3ac' ADMIN;
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.APP_USERS;
INSERT INTO "PUBLIC"."APP_USERS" (email, first_name, is_admin, last_name, password) VALUES
    ('admin@email.com', 'Matias', TRUE, 'Acosta', '$2a$10$Ypk8uz0GAfjpV7.cDYjF3u0NtRx5O6W4sydZfwFGHlYVtQuE7ONQa');
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT;
INSERT INTO "PUBLIC"."PRODUCT" (id, description, is_free_shipping, name, price, price_discount_percentage) VALUES
    ('MLA49315128', U&'Homem N\00f3s Masculino  \000aEau De Parfum  \000aNatura  \000aContenido: 100ml  \000a\000aPerfume Natura Homem N\00f3s, una fragancia dise\00f1ada para el hombre moderno que busca expresar su autenticidad y carisma. Este Eau Parfum de la reconocida marca Natura ofrece una experiencia olfativa \00fanica, ideal para quienes valoran la elegancia y la sofisticaci\00f3n en cada detalle de su vida.  \000a\000aLa l\00ednea Homem se caracteriza por su enfoque en la masculinidad contempor\00e1nea, y N\00f3s no es la excepci\00f3n. Con un aroma que evoca la frescura y la fuerza, este perfume se convierte en un aliado perfecto para cualquier ocasi\00f3n, ya sea un d\00eda en la oficina o una salida nocturna.  \000a\000aLa duraci\00f3n de la fragancia asegura que su esencia perdure a lo largo del d\00eda, brindando una sensaci\00f3n de confianza y bienestar. Adem\00e1s, Natura se compromete con la sostenibilidad, ofreciendo un producto libre de crueldad y vegano, lo que lo convierte en una elecci\00f3n consciente para el consumidor actual.  \000a\000aSum\00e9rgete en la experiencia de Natura Homem N\00f3s y permite que su aroma te acompa\00f1e en cada paso, reflejando tu personalidad y estilo de vida. Este perfume es m\00e1s que una fragancia; es una declaraci\00f3n de intenciones para el hombre que sabe lo que quiere.', TRUE, 'Homem Nos Natura 100ml', 873895.07, 13),
    ('MLA22986401', U&'Este producto combina la potencia y la capacidad de una computadora con la versatilidad y facilidad de uso que solo un iPad puede brindar. Realizar varias tareas a la vez, como editar documentos mientras busc\00e1s informaci\00f3n en internet o sacarte una selfie, es sumamente sencillo. Como si esto fuera poco, tambi\00e9n ofrece la posibilidad de descargar desde la App Store cientos de aplicaciones creadas para pintar, dibujar, escuchar m\00fasica y \00a1mucho m\00e1s!', FALSE, 'iPad Apple iPad Pro 2018', 966132.86, 20);
-- 17 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT_ATTRIBUTES;
INSERT INTO "PUBLIC"."PRODUCT_ATTRIBUTES" (product_id, name, attribute_value) VALUES
    ('MLA49315128', 'Marca', 'Natura'),
    ('MLA49315128', U&'L\00ednea', 'Homem'),
    ('MLA49315128', 'Nombre del perfume', U&'N\00f3S'),
    ('MLA49315128', U&'G\00e9nero', 'Hombre'),
    ('MLA49315128', 'Tipo de perfume', 'Eau Parfum'),
    ('MLA49315128', U&'Formato de aplicaci\00f3n', 'Spray'),
    ('MLA49315128', 'Volumen de la unidad', '100 mL'),
    ('MLA49315128', 'Es recargable', 'No'),
    ('MLA49315128', U&'Pa\00eds de origen', 'Brasil'),
    ('MLA49315128', 'Es libre de crueldad', U&'S\00ed'),
    ('MLA49315128', 'Es vegano', U&'S\00ed'),
    ('MLA49315128', 'Es libre de alcohol', U&'S\00ed'),
    ('MLA22986401', 'Marca', 'Apple'),
    ('MLA22986401', U&'L\00ednea', 'iPad'),
    ('MLA22986401', 'Modelo', 'iPad Pro'),
    ('MLA22986401', U&'Versi\00f3n', '2018'),
    ('MLA22986401', U&'Homologaci\00f3n Anatel N\00ba', '63571801993');
-- 6 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT_PICTURES;
INSERT INTO "PUBLIC"."PRODUCT_PICTURES" VALUES
                                            ('MLA49315128', 'https://http2.mlstatic.com/D_NQ_NP_977497-MLA84238785726_052025-F.jpg'),
                                            ('MLA49315128', 'https://http2.mlstatic.com/D_NQ_NP_961163-MLA84535725035_052025-F.jpg'),
                                            ('MLA22986401', 'https://http2.mlstatic.com/D_NQ_NP_784225-MLU77660440505_072024-F.jpg'),
                                            ('MLA22986401', 'https://http2.mlstatic.com/D_NQ_NP_656800-MLU77443484640_072024-F.jpg'),
                                            ('MLA22986401', 'https://http2.mlstatic.com/D_NQ_NP_702770-MLU77443484644_072024-F.jpg'),
                                            ('MLA22986401', 'https://http2.mlstatic.com/D_NQ_NP_924330-MLU75329999576_032024-F.jpg');
-- Se eliminaron los CREATE TABLE y ALTER TABLE para evitar conflictos con Hibernate.
