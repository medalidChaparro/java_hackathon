-- Script: crear base de datos, tabla y 5 registros
-- Crear la nueva base de datos
CREATE DATABASE IF NOT EXISTS inventario_computadoras_db;
USE inventario_computadoras_db;

-- Crear la tabla de computadoras
CREATE TABLE computadoras (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              tipo_equipo VARCHAR(50) NOT NULL,
                              marca VARCHAR(50) NOT NULL,
                              modelo VARCHAR(100) NOT NULL,
                              sistema_operativo VARCHAR(50) NOT NULL,
                              ram INT NOT NULL,                         -- En GB
                              almacenamiento INT NOT NULL,              -- En GB
                              fecha_mantenimiento DATE NOT NULL,
                              fecha_registro DATE NOT NULL,
                              estado ENUM('activo', 'inactivo') NOT NULL
);

INSERT INTO computadoras
(tipo_equipo, marca, modelo, sistema_operativo, ram, almacenamiento, fecha_mantenimiento, fecha_registro, estado)
VALUES
    ('Laptop', 'HP', 'Pavilion 15', 'Windows 11', 8, 512, '2025-01-10', '2025-01-15', 'activo'),
    ('Desktop', 'Dell', 'OptiPlex 7080', 'Windows 10', 16, 1024, '2025-02-05', '2025-02-08', 'activo'),
    ('Laptop', 'Lenovo', 'ThinkPad T14', 'Ubuntu 22.04', 16, 512, '2025-01-25', '2025-01-26', 'activo'),
    ('All-in-One', 'ASUS', 'Vivo AIO V241', 'Windows 11', 8, 256, '2025-01-18', '2025-01-20', 'inactivo');

SELECT * FROM computadoras;                                                                              ('PC002','Laptop','Lenovo','ThinkPad','Windows 11','16GB','512GB','2024-02-10',1,1),
                                                                                                                                ('PC003','Laptop','Dell','Latitude','Ubuntu','8GB','256GB','2024-02-15',0,1),
                                                                                                                                ('PC004','All in One','AOC','AIO24','Windows 10','4GB','1TB','2024-03-10',0,1),
                                                                                                                                ('PC005','Escritorio','Acer','Veriton','Windows 11','16GB','1TB','2024-04-03',1,1);
