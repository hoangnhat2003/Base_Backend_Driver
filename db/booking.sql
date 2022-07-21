-- Table roles
CREATE TABLE roles  (
id INT(6) AUTO_INCREMENT PRIMARY KEY,
name ENUM ('USER', 'DRIVER', 'ADMIN', 'MERCHANT')
);

INSERT INTO roles (name)
VALUES ('USER');
INSERT INTO roles (name)
VALUES ('DRIVER');
INSERT INTO roles (name)
VALUES ('ADMIN');
INSERT INTO roles (name)
VALUES ('MERCHANT');

-- Table vehicle

CREATE TABLE vehicle (
id INT(6) AUTO_INCREMENT PRIMARY KEY,
account_id INT NOT NULL,
make VARCHAR(30) NOT NULL,
seats INT,
type ENUM ('BIKE', 'CAR')
);

INSERT INTO vehicle (account_id, make, seats, type)
VALUES (1, "2020", 4, 'CAR');
INSERT INTO vehicle (account_id, make, type)
VALUES (1, "2021",'CAR');
INSERT INTO vehicle (account_id, make, type)
VALUES (1, "2022", 'BIKE');

-- Table billing_config

CREATE TABLE billing_config (
id INT(6) AUTO_INCREMENT PRIMARY KEY,
vehicle_type ENUM ('BIKE', 'CAR') NOT NULL,
price_per_km INT NOT NULL,
seats INT,
price_per_m INT NOT NULL,
extend_price INT,
price_per_hour INT NOT NULL
);

INSERT INTO billing_config (vehicle_type, price_per_km, seats, price_per_m, extend_price, price_per_hour)
VALUES ('CAR', 100, 4, 10, null, 70 );

INSERT INTO billing_config (vehicle_type, price_per_km, price_per_m, extend_price, price_per_hour)
VALUES ('CAR', 80, 8, null, 30 );

INSERT INTO billing_config (vehicle_type, price_per_km, price_per_m, extend_price, price_per_hour)
VALUES ('BIKE', 40, 4, null, 20 );
