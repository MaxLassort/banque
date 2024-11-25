-- Create schema
CREATE SCHEMA IF NOT EXISTS bank;

-- Create the bank_account table
CREATE TABLE IF NOT EXISTS bank.bank_account (
    id SERIAL PRIMARY KEY,
    id_user INT UNIQUE,
    balance DOUBLE PRECISION
);

-- Create the transaction table
CREATE TABLE IF NOT EXISTS bank.transaction (
    id  SERIAL PRIMARY KEY,
    details TEXT,
    type TEXT,
    id_bank_account INT REFERENCES bank.bank_account(id)
);

-- Insert initial data into the bank_account table
INSERT INTO bank.bank_account (id_user, balance) VALUES (1, 100.0);