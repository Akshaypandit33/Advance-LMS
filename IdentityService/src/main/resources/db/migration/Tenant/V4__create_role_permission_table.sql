-- Create role_permission table for mapping roles to global permissions
CREATE TABLE role_permission (
                                 id UUID PRIMARY KEY ,
                                 role_id UUID NOT NULL,
                                 permission_id UUID NOT NULL,
                                 created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
                                 CONSTRAINT fk_role_permission_role
                                     FOREIGN KEY (role_id)
                                         REFERENCES roles(id)
                                         ON DELETE CASCADE,

                                 CONSTRAINT fk_role_permission_permission
                                     FOREIGN KEY (permission_id)
                                         REFERENCES global_metadata.global_permissions(id)
                                         ON DELETE CASCADE,

    -- Unique constraint to prevent duplicate role-permission assignments
                                 CONSTRAINT unique_role_permission
                                     UNIQUE (role_id, permission_id)
);

-- Create indexes for better query performance
CREATE INDEX idx_role_permission_role_id ON role_permission(role_id);
CREATE INDEX idx_role_permission_permission_id ON role_permission(permission_id);
CREATE INDEX idx_role_permission_created_at ON role_permission(created_at);

-- Optional: Add comments for documentation
COMMENT ON TABLE role_permission IS 'Mapping table between roles and global permissions';
COMMENT ON COLUMN role_permission.permission_id IS 'Reference to permission in global_metadata schema';