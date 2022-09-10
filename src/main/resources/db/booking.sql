
CREATE DATABASE booking_db;


-- Table account
CREATE TABLE IF NOT EXISTS account (
id SERIAL,
username VARCHAR(255),
name VARCHAR(255),
password VARCHAR(255),
email VARCHAR(255),
account_status VARCHAR(255),
online_status VARCHAR(255),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id)
);

CREATE TYPE E_ROLES AS ENUM ('USER', 'DRIVER', 'ADMIN', 'MERCHANT');
-- Table roles
CREATE TABLE IF NOT EXISTS roles  (
id SERIAL,
name E_ROLES,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id)
);

INSERT INTO roles (name)
VALUES ('USER');
INSERT INTO roles (name)
VALUES ('DRIVER');
INSERT INTO roles (name)
VALUES ('ADMIN');
INSERT INTO roles (name)
VALUES ('MERCHANT');

-- Table account_roles
CREATE TABLE IF NOT EXISTS account_roles  (
account_id INT,
role_id INT,
CONSTRAINT account_roles_fk1 FOREIGN KEY (account_id) REFERENCES account(id),
CONSTRAINT account_roles_fk2 FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Table account
CREATE TABLE IF NOT EXISTS chat_account (
id SERIAL,
account_id INT,
username VARCHAR(30),
password VARCHAR(30),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id),
CONSTRAINT chat_account_fk1 FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TYPE E_CAR AS ENUM ('BIKE', 'CAR');

-- Table vehicle
CREATE TABLE IF NOT EXISTS vehicle (
id SERIAL,
account_id INT,
make VARCHAR(255),
seats INT,
type E_CAR,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id)
);

INSERT INTO vehicle (account_id, make, seats, type)
VALUES (1, '2020', 4, 'CAR');
INSERT INTO vehicle (account_id, make, type)
VALUES (1, '2021','CAR');
INSERT INTO vehicle (account_id, make, type)
VALUES (1, '2022', 'BIKE');

-- Table billing_config
CREATE TABLE IF NOT EXISTS billing_config (
id SERIAL,
vehicle_type E_CAR,
price_per_km INT,
seats INT,
price_per_m INT,
extend_price INT,
price_per_hour INT,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id)
);

INSERT INTO billing_config (vehicle_type, price_per_km, seats, price_per_m, extend_price, price_per_hour)
VALUES ('CAR', 100, 4, 10, null, 70 );

INSERT INTO billing_config (vehicle_type, price_per_km, price_per_m, extend_price, price_per_hour)
VALUES ('CAR', 80, 8, null, 30 );

INSERT INTO billing_config (vehicle_type, price_per_km, price_per_m, extend_price, price_per_hour)
VALUES ('BIKE', 40, 4, null, 20 );

-- Table account_wallet
CREATE TABLE IF NOT EXISTS account_wallet (
id SERIAL,
account_id INT,
walletType VARCHAR(255),
balance INT,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT account_wallet_fk1 FOREIGN KEY (account_id) REFERENCES account(id),
PRIMARY KEY(id)
);


-- Table booking_history
CREATE TABLE IF NOT EXISTS booking_history (
id SERIAL,
requestId VARCHAR(255),
requester_account_id INT,
requested_at INT,
passengerName VARCHAR(255),
distance decimal,
distance_unit VARCHAR(255),
hours INT,
amount INT,
waiting_fee INT,
pay_type VARCHAR(255),
waiting_pay_type VARCHAR(255),
status VARCHAR(255),
waiting_fee_status VARCHAR(255),
total_amount INT,
driver_amount INT,
driver_account_id INT,
accepted_at INT,
canceled_at INT,
expired_at INT,
arrived_at INT,
started_at INT,
finished_at INT,
canceled_by INT,
canceled_comment VARCHAR(255),
billing_updated BOOLEAN,
billing_status VARCHAR(255),
billing_transaction_id VARCHAR(255),
note VARCHAR(255),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id)
);

-- Table balance_history
CREATE TABLE IF NOT EXISTS balance_history (
id SERIAL,
txref_id VARCHAR(255),
type INT,
amount INT,
currency VARCHAR(255),
wallet_type VARCHAR(255),
wallet_id INT,
operation INT,
note VARCHAR(255),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id)
);
-- Table refresh_token
CREATE TABLE IF NOT EXISTS refresh_token (
id SERIAL,
account_id INT,
token VARCHAR(255),
expiryDate TIMESTAMP,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT refresh_token_fk1 FOREIGN KEY (account_id) REFERENCES account(id),
PRIMARY KEY(id)
);