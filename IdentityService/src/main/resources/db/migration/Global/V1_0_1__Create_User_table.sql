CREATE SCHEMA IF NOT EXISTS global_metadata;

CREATE TABLE global_metadata.global_users (
                                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                              first_name VARCHAR(100) NOT NULL,
                                              last_name VARCHAR(100) NOT NULL,
                                              email VARCHAR(255) NOT NULL UNIQUE,
                                              password_hash VARCHAR(255) NOT NULL,
                                              phone_number VARCHAR(20),
                                              gender VARCHAR(10),
                                              is_super_admin BOOLEAN DEFAULT FALSE,
                                              created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                              updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Index for phone lookups
CREATE INDEX idx_global_users_phone ON global_metadata.global_users (phone_number);

-- Index for name searches
CREATE INDEX idx_global_users_name ON global_metadata.global_users (first_name, last_name);
