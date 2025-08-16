-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS auth_schema;

-- Create User table if not exists
CREATE TABLE IF NOT EXISTS auth_schema.user (
    uid BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_timestamp TIMESTAMP,
    updated_timestamp TIMESTAMP
);