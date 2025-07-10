INSERT INTO app_users (id, email, first_name, is_admin, last_name, password) VALUES (1, 'admin@email.com', 'Matias', TRUE, 'Acosta', '$2a$10$lxNZaZ0mHO3aD/6k3y8Ge.giLaIdgdLFoFt1N.ElEEtrc6SIbMJOW');
INSERT INTO app_users (id, email, first_name, is_admin, last_name, password) VALUES (2, 'juliantrejo@email.com', 'Julian', FALSE, 'Trejo', '$2a$10$R.65lbFnZvMrGFVixopmEO6BXq/s3BE1XiPECVzYRk24NKZpM0TQm');

-- Reset the auto-increment sequence to start from 3
ALTER TABLE app_users ALTER COLUMN id RESTART WITH 3;

INSERT INTO product
(id, description, is_free_shipping, name, price, price_discount_percentage)
VALUES
    ('MLA49315128',
     'Homem Nós Masculino \nEau De Parfum \nNatura \nContenido: 100ml \n\nPerfume Natura Homem Nós, una fragancia diseñada para el hombre moderno que busca expresar su autenticidad y carisma. Este Eau Parfum de la reconocida marca Natura ofrece una experiencia olfativa única, ideal para quienes valoran la elegancia y la sofisticación en cada detalle de su vida. \n\nLa línea Homem se caracteriza por su enfoque en la masculinidad contemporánea, y Nós no es la excepción. Con un aroma que evoca la frescura y la fuerza, este perfume se convierte en un aliado perfecto para cualquier ocasión, ya sea un día en la oficina o una salida nocturna. \n\nLa duración de la fragancia asegura que su esencia perdure a lo largo del día, brindando una sensación de confianza y bienestar. Además, Natura se compromete con la sostenibilidad, ofreciendo un producto libre de crueldad y vegano, lo que lo convierte en una elección consciente para el consumidor actual. \n\nSumérgete en la experiencia de Natura Homem Nós y permite que su aroma te acompañe en cada paso, reflejando tu personalidad y estilo de vida. Este perfume es más que una fragancia; es una declaración de intenciones para el hombre que sabe lo que quiere.',
     TRUE,
     'Homem Nos Natura 100ml - Masculino - Edp 30% Off - Prt Biene',
     NULL,
     NULL);

INSERT INTO product_attribute
(id, name, attribute_value, product_id)
VALUES
    ('BRAND', 'Marca', 'Natura', 'MLA49315128'),
    ('LINE', 'Línea', 'Homem', 'MLA49315128'),
    ('PERFUME_NAME', 'Nombre del perfume', 'NóS', 'MLA49315128'),
    ('GENDER', 'Género', 'Hombre', 'MLA49315128');

INSERT INTO product_pictures
(product_id, pictures)
VALUES
    ('MLA49315128',
     '["https://http2.mlstatic.com/D_NQ_NP_977497-MLA84238785726_052025-F.jpg","https://http2.mlstatic.com/D_NQ_NP_961163-MLA84535725035_052025-F.jpg"]');

INSERT INTO product
(id, description, is_free_shipping, name, price, price_discount_percentage)
VALUES
    ('MLA22986401',
     'Este producto combina la potencia y la capacidad de una computadora con la versatilidad y facilidad de uso que solo un iPad puede brindar. Realizar varias tareas a la vez, como editar documentos mientras buscás información en internet o sacarte una selfie, es sumamente sencillo. Como si esto fuera poco, también ofrece la posibilidad de descargar desde la App Store cientos de aplicaciones creadas para pintar, dibujar, escuchar música y ¡mucho más!',
     FALSE,
     'iPad Apple iPad Pro 2018',
     NULL,
     NULL);

INSERT INTO product_attribute
(id, name, attribute_value, product_id)
VALUES
    ('MODEL', 'Modelo', 'iPad Pro', 'MLA22986401'),
    ('VERSION', 'Versión', '2018', 'MLA22986401'),
    ('ANATEL_HOMOLOGATION_NUMBER', 'Homologación Anatel Nº', '63571801993', 'MLA22986401');

ALTER TABLE product_pictures ALTER COLUMN pictures VARCHAR(1000);
INSERT INTO product_pictures
(product_id, pictures)
VALUES
    ('MLA22986401',
     '["https://http2.mlstatic.com/D_NQ_NP_784225-MLU77660440505_072024-F.jpg","https://http2.mlstatic.com/D_NQ_NP_656800-MLU77443484640_072024-F.jpg","https://http2.mlstatic.com/D_NQ_NP_702770-MLU77443484644_072024-F.jpg","https://http2.mlstatic.com/D_NQ_NP_924330-MLU75329999576_032024-F.jpg"]');