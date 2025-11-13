CREATE TABLE roles (
                       id UUID PRIMARY KEY,
                       role_name VARCHAR(100) NOT NULL UNIQUE,
                       role_description VARCHAR(500),
                       created_at TIMESTAMPTZ,
                       updated_at TIMESTAMPTZ
);

-- Create indexes for Roles table
CREATE INDEX idx_roles_name ON roles(role_name);


