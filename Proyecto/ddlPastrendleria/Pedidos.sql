-- ===========================================
-- INSERTAR PEDIDOS Y DETALLES
-- ===========================================

-- Pedido 1
WITH pedido1 AS (
    INSERT INTO logica_negocio.pedido (id_usuario, fecha_pedido, direccion_pedido, estado_pedido, total_pedido)
    VALUES (1, '2025-11-26 10:00:00+00', 'Calle A 12', 'Pendiente', 36.00)
    RETURNING id_pedido
)
INSERT INTO logica_negocio.detalle_pedido (id_pedido, id_producto, nombre_producto, cantidad_producto, precio_unitario, subtotal)
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 2, prod.precio_producto, 2 * prod.precio_producto
FROM pedido1 p
JOIN logica_negocio.producto prod ON prod.id_producto = 1
UNION ALL
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 1, prod.precio_producto, 1 * prod.precio_producto
FROM pedido1 p
JOIN logica_negocio.producto prod ON prod.id_producto = 2;

-- Pedido 2
WITH pedido2 AS (
    INSERT INTO logica_negocio.pedido (id_usuario, fecha_pedido, direccion_pedido, estado_pedido, total_pedido)
    VALUES (2, '2025-11-26 11:00:00+00', 'Avenida B 34', 'Enviado', 55.50)
    RETURNING id_pedido
)
INSERT INTO logica_negocio.detalle_pedido (id_pedido, id_producto, nombre_producto, cantidad_producto, precio_unitario, subtotal)
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 3, prod.precio_producto, 3 * prod.precio_producto
FROM pedido2 p
JOIN logica_negocio.producto prod ON prod.id_producto = 3
UNION ALL
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 1, prod.precio_producto, 1 * prod.precio_producto
FROM pedido2 p
JOIN logica_negocio.producto prod ON prod.id_producto = 4;

-- Pedido 3
WITH pedido3 AS (
    INSERT INTO logica_negocio.pedido (id_usuario, fecha_pedido, direccion_pedido, estado_pedido, total_pedido)
    VALUES (1, '2025-11-26 12:00:00+00', 'Boulevard C 7', 'Pendiente', 42.00)
    RETURNING id_pedido
)
INSERT INTO logica_negocio.detalle_pedido (id_pedido, id_producto, nombre_producto, cantidad_producto, precio_unitario, subtotal)
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 2, prod.precio_producto, 2 * prod.precio_producto
FROM pedido3 p
JOIN logica_negocio.producto prod ON prod.id_producto = 5
UNION ALL
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 1, prod.precio_producto, 1 * prod.precio_producto
FROM pedido3 p
JOIN logica_negocio.producto prod ON prod.id_producto = 2;

-- Pedido 4
WITH pedido4 AS (
    INSERT INTO logica_negocio.pedido (id_usuario, fecha_pedido, direccion_pedido, estado_pedido, total_pedido)
    VALUES (2, '2025-11-26 13:00:00+00', 'Calle D 89', 'En preparación', 75.00)
    RETURNING id_pedido
)
INSERT INTO logica_negocio.detalle_pedido (id_pedido, id_producto, nombre_producto, cantidad_producto, precio_unitario, subtotal)
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 3, prod.precio_producto, 3 * prod.precio_producto
FROM pedido4 p
JOIN logica_negocio.producto prod ON prod.id_producto = 1
UNION ALL
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 2, prod.precio_producto, 2 * prod.precio_producto
FROM pedido4 p
JOIN logica_negocio.producto prod ON prod.id_producto = 3;

-- Pedido 5
WITH pedido5 AS (
    INSERT INTO logica_negocio.pedido (id_usuario, fecha_pedido, direccion_pedido, estado_pedido, total_pedido)
    VALUES (2, '2025-11-26 14:00:00+00', 'Paseo E 12', 'Pendiente', 90.00)
    RETURNING id_pedido
)
INSERT INTO logica_negocio.detalle_pedido (id_pedido, id_producto, nombre_producto, cantidad_producto, precio_unitario, subtotal)
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 2, prod.precio_producto, 2 * prod.precio_producto
FROM pedido5 p
JOIN logica_negocio.producto prod ON prod.id_producto = 5
UNION ALL
SELECT p.id_pedido, prod.id_producto, prod.nombre_producto, 2, prod.precio_producto, 2 * prod.precio_producto
FROM pedido5 p
JOIN logica_negocio.producto prod ON prod.id_producto = 2;
