-- Create user
CREATE ROLE happy_s WITH LOGIN PASSWORD 'h@pp1_d8';
ALTER ROLE happy_s CREATEDB;

-- Create database
CREATE DATABASE happy_db OWNER happy_s;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE happy_db TO happy_s;

-- Switch to the new database
\c happy_db;

-- Ensure schema exists
CREATE SCHEMA IF NOT EXISTS happy_db AUTHORIZATION happy_s;
GRANT USAGE ON SCHEMA happy_db TO happy_s;
