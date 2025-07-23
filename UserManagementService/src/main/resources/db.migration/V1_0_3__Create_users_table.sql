-- Create Users table

CREATE TABLE users (
                       id UUID PRIMARY KEY ,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email VARCHAR(255) UNIQUE NOT NULL, -- RFC 5321 max email length
                       password VARCHAR(255),
                       phone_number VARCHAR(20),
                       gender VARCHAR(20),
                       account_status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL,
                       created_at TIMESTAMPTZ ,
                       updated_at TIMESTAMPTZ
);

-- Create indexes for Users table
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_account_status ON users(account_status);
CREATE INDEX idx_users_created_at ON users(created_at);

