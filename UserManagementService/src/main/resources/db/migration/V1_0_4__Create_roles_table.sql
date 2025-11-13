CREATE TABLE roles (
                       id UUID PRIMARY KEY ,
                       role_name VARCHAR(100) NOT NULL UNIQUE ,
                       role_description VARCHAR(500),
                       is_system_defined BOOLEAN DEFAULT FALSE NOT NULL,
                       created_at TIMESTAMPTZ ,
                       updated_at TIMESTAMPTZ
);

-- Create indexes for Roles table
CREATE INDEX idx_roles_name ON roles(role_name);
CREATE INDEX idx_roles_system_defined ON roles(is_system_defined);
CREATE INDEX idx_roles_name_system ON roles(role_name, is_system_defined);

