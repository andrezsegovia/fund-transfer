CREATE TABLE account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account VARCHAR(11) NOT NULL UNIQUE,
    account_balance FLOAT NOT NULL,
    create_date TIMESTAMP,
    update_date TIMESTAMP);
