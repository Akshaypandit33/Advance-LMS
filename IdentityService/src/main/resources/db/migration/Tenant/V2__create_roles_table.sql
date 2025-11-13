-- Create roles table
CREATE TABLE roles (
                       id UUID PRIMARY KEY ,
                       role_name VARCHAR(100) NOT NULL UNIQUE,
                       role_description TEXT,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_roles_role_name ON roles(role_name);
CREATE INDEX idx_roles_created_at ON roles(created_at);

-- Optional: Add comment for documentation
COMMENT ON TABLE roles IS 'Stores system roles and their descriptions';
COMMENT ON COLUMN roles.role_name IS 'Role name (automatically converted to uppercase)';
