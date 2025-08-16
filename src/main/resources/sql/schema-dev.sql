-- Create custom schema (if not exists)
CREATE SCHEMA IF NOT EXISTS patient_schema;

-- Create sequence (safe for Spring Boot)
CREATE SEQUENCE IF NOT EXISTS PATIENT_SEQUENCE
    START WITH 568120
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 50;

-- Create patient table if not exists
CREATE TABLE IF NOT EXISTS patient_schema.patient (
    uid BIGINT PRIMARY KEY DEFAULT nextval('PATIENT_SEQUENCE'),
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    address VARCHAR(255),
    date_of_birth DATE,
    registered_date TIMESTAMP,
    updated_timestamp TIMESTAMP
);