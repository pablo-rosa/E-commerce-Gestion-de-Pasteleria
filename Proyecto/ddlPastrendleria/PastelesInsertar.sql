-- Eliminar todos los registros anteriores en la tabla producto
DELETE FROM logica_negocio.producto;

-- Insertar los nuevos registros con nombres de imágenes aleatorios
INSERT INTO logica_negocio.producto (id_producto, nombre_producto, descripcion_producto, precio_producto, stock_producto, categoria_producto, imagen_producto) VALUES
(1, 'Pastel de manzana con canela', 'Un delicioso pastel de manzana con un toque de canela, ideal para cualquier ocasión.', 10.00, 50, 'Pasteles', 'imagen_abc123.png'),
(2, 'Pastel de chocolate clásico', 'Pastel de chocolate rico y esponjoso con un glaseado suave de chocolate.', 12.00, 30, 'Pasteles', 'imagen_xyz456.png'),
(3, 'Pastel de zanahoria', 'Un pastel suave de zanahoria, cubierto con un delicioso glaseado de queso crema.', 8.50, 40, 'Pasteles', 'imagen_def789.png'),
(4, 'Tarta de limón y merengue', 'Un pastel de limón refrescante, con una capa suave de merengue por encima.', 9.00, 25, 'Pasteles', 'imagen_ghi234.png'),
(5, 'Pastel de fresa con crema batida', 'Un pastel ligero de fresa, cubierto con una suave capa de crema batida.', 11.50, 60, 'Pasteles', 'imagen_jkl567.png'),
(6, 'Pastel de chocolate blanco y frambuesas', 'Pastel cremoso de chocolate blanco con un toque de frambuesas frescas.', 16.00, 40, 'Chocolates', 'imagen_mno890.png'),
(7, 'Pastel de chocolate con menta', 'Pastel de chocolate suave con un toque fresco de menta, una combinación irresistible.', 14.50, 55, 'Chocolates', 'imagen_pqr123.png'),
(8, 'Bombones de chocolate negro', 'Deliciosos bombones de chocolate negro, ideales para los amantes del chocolate puro.', 8.00, 70, 'Chocolates', 'imagen_stu456.png'),
(9, 'Chocolate caliente con malvaviscos', 'Deliciosa bebida de chocolate caliente, acompañada de malvaviscos para un toque dulce.', 5.00, 100, 'Chocolates', 'imagen_vwx789.png'),
(10, 'Trufas de chocolate blanco y almendras', 'Suaves trufas de chocolate blanco con un toque de almendras trituradas.', 12.00, 40, 'Chocolates', 'imagen_yza234.png'),
(11, 'Galletas de avena y pasas', 'Galletas suaves de avena con pasas, perfectas para una merienda saludable.', 4.00, 80, 'Galletas', 'imagen_bcd567.png'),
(12, 'Galletas de chocolate chip', 'Galletas crujientes de chocolate con trozos grandes de chocolate chip.', 5.00, 100, 'Galletas', 'imagen_efg890.png'),
(13, 'Galletas de jengibre', 'Deliciosas galletas de jengibre con un toque de canela, perfectas para las fiestas.', 6.00, 60, 'Galletas', 'magen_hij123.png'),
(14, 'Galletas de limón y semillas de amapola', 'Suaves galletas de limón con semillas de amapola, una combinación fresca y sabrosa.', 5.50, 75, 'Galletas', 'imagen_klm456.png'),
(15, 'Galletas de avena y chocolate', 'Galletas de avena combinadas con trozos de chocolate, una mezcla perfecta de sabores.', 6.00, 90, 'Galletas', 'imagen_nop789.png'),
(16, 'Caramelos de frutas', 'Caramelos suaves con sabor a frutas, ideales para los más pequeños.', 2.00, 200, 'Dulces', 'imagen_qrs234.png'),
(17, 'Lollipops de fresa', 'Chupetines de fresa con un delicioso sabor dulce y refrescante.', 1.50, 150, 'Dulces', 'imagen_tuv567.png'),
(18, 'Gelatina de mango', 'Gelatina de mango suave y refrescante, perfecta para un postre ligero.', 3.50, 80, 'Dulces', 'imagen_wxy890.png'),
(19, 'Turrón de almendras', 'Delicioso turrón de almendras, tradicional en muchas festividades.', 7.00, 60, 'Dulces', 'imagen_zab123.png'),
(20, 'Chicles de menta', 'Chicles de menta refrescantes, ideales para disfrutar en cualquier momento del día.', 1.00, 500, 'Dulces', 'imagen_cde456.png');
