
CREATE DATABASE IF NOT EXISTS backend-drivor DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

-- Table account
CREATE TABLE IF NOT EXISTS account (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(30),
name VARCHAR(30),
password VARCHAR(30),
email VARCHAR(30),
account_status VARCHAR(30),
online_status VARCHAR(30),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table roles
CREATE TABLE IF NOT EXISTS roles  (
id INT AUTO_INCREMENT PRIMARY KEY,
name ENUM ('USER', 'DRIVER', 'ADMIN', 'MERCHANT'),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
id INT AUTO_INCREMENT PRIMARY KEY,
account_id INT,
username VARCHAR(30),
password VARCHAR(30),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT chat_account_fk1 FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Table vehicle

CREATE TABLE IF NOT EXISTS vehicle (
id INT AUTO_INCREMENT PRIMARY KEY,
account_id INT,
make VARCHAR(30),
seats INT,
type ENUM ('BIKE', 'CAR'),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO IF NOT EXISTS vehicle (account_id, make, seats, type)
VALUES (1, "2020", 4, 'CAR');
INSERT INTO vehicle (account_id, make, type)
VALUES (1, "2021",'CAR');
INSERT INTO vehicle (account_id, make, type)
VALUES (1, "2022", 'BIKE');

-- Table billing_config

CREATE TABLE IF NOT EXISTS billing_config (
id INT AUTO_INCREMENT PRIMARY KEY,
vehicle_type ENUM ('BIKE', 'CAR'),
price_per_km INT,
seats INT,
price_per_m INT,
extend_price INT,
price_per_hour INT,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO billing_config (vehicle_type, price_per_km, seats, price_per_m, extend_price, price_per_hour)
VALUES ('CAR', 100, 4, 10, null, 70 );

INSERT INTO billing_config (vehicle_type, price_per_km, price_per_m, extend_price, price_per_hour)
VALUES ('CAR', 80, 8, null, 30 );

INSERT INTO billing_config (vehicle_type, price_per_km, price_per_m, extend_price, price_per_hour)
VALUES ('BIKE', 40, 4, null, 20 );

-- Table account_wallet
CREATE TABLE IF NOT EXISTS account_wallet (
id INT AUTO_INCREMENT PRIMARY KEY,
account_id INT,
walletType VARCHAR(200),
balance INT,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT account_wallet_fk1 FOREIGN KEY (account_id) REFERENCES account(id)
);


-- Table booking_history
CREATE TABLE IF NOT EXISTS booking_history (
id INT AUTO_INCREMENT PRIMARY KEY,
requestId VARCHAR(30),
requester_account_id INT,
requested_at INT,
passengerName VARCHAR(30),
distance double,
distance_unit VARCHAR(30),
hours INT,
amount INT,
waiting_fee INT,
pay_type VARCHAR(30),
waiting_pay_type VARCHAR(30),
status VARCHAR(30),
waiting_fee_status VARCHAR(30),
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
canceled_comment VARCHAR(30),
billing_updated BOOLEAN,
billing_status VARCHAR(30),
billing_transaction_id VARCHAR(30),
note VARCHAR(30),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table balance_history
CREATE TABLE IF NOT EXISTS balance_history (
id INT AUTO_INCREMENT PRIMARY KEY,
txref_id VARCHAR,
type INT,
amount INT,
currency VARCHAR(30),
wallet_type VARCHAR(30),
wallet_id INT,
operation INT,
note VARCHAR(30),
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Table refresh_token
CREATE TABLE IF NOT EXISTS refresh_token (
id INT(6) AUTO_INCREMENT PRIMARY KEY,
account_id INT,
token VARCHAR(30),
expiryDate TIMESTAMP,
created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT refresh_token_fk1 FOREIGN KEY (account_id) REFERENCES account(id)
);