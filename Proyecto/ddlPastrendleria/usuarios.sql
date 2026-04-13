-- Insertar el usuario administrador
INSERT INTO gestion_usuario.usuario (nombre_usuario, email_usuario, passwd_usuario, rol_usuario)
VALUES
    ('administrador', 'administrador@gmail.com', '$2a$10$0B2kWpTy6zh7gNrL.YRjaORq7/kZCyMeoWrKyOW8/u/HYlF0NsUEC', 'admin');

-- Insertar el usuario usuario
INSERT INTO gestion_usuario.usuario (nombre_usuario, email_usuario, passwd_usuario, rol_usuario)
VALUES
    ('usuario', 'usuario@gmail.com', '$2a$10$0B2kWpTy6zh7gNrL.YRjaORq7/kZCyMeoWrKyOW8/u/HYlF0NsUEC', 'usuario');