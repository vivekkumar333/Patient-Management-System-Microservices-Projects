-- Create custom schema (if not exists)
CREATE SCHEMA IF NOT EXISTS billing_schema;

-- Create sequence (safe for Spring Boot)
CREATE SEQUENCE IF NOT EXISTS BILLING_SEQUENCE
    START WITH 599999
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 10;

CREATE SEQUENCE IF NOT EXISTS TRANSACTION_SEQUENCE
    START WITH 599999999
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 10;

-- Create patient table if not exists
CREATE TABLE IF NOT EXISTS billing_schema.billing_account (
    uid BIGINT PRIMARY KEY DEFAULT nextval('BILLING_SEQUENCE'),
    patient_uid BIGINT UNIQUE NOT NULL,
    account_balance DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_timestamp TIMESTAMP NOT NULL,
    updated_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS billing_schema.billing_transaction (
    uid BIGINT PRIMARY KEY DEFAULT nextval('TRANSACTION_SEQUENCE'),
    billing_id BIGINT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    remarks VARCHAR(255),
    created_timestamp TIMESTAMP NOT NULL,
    CONSTRAINT fk_BILLING_ID FOREIGN KEY(billing_id) REFERENCES billing_schema.billing_account(uid)
);