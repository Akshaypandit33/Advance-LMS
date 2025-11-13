-- Create Role Permissions table (Many-to-Many relationship)

CREATE TABLE role_permissions (
                                  id UUID PRIMARY KEY,
                                  role_id UUID NOT NULL,
                                  permission_id UUID NOT NULL,
                                  created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                  updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                  CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
                                  CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE,
                                  CONSTRAINT uk_role_permissions_role_permission UNIQUE (role_id, permission_id)
);

-- Create indexes for Role Permissions table
CREATE INDEX idx_role_permissions_role_id ON role_permissions(role_id);
CREATE INDEX idx_role_permissions_permission_id ON role_permissions(permission_id);
CREATE INDEX idx_role_permissions_composite ON role_permissions(role_id, permission_id);
