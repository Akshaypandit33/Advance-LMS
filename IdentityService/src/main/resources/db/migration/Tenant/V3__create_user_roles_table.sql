-- Create user_roles table for the many-to-many relationship between users and roles
CREATE TABLE user_roles (
                            id UUID PRIMARY KEY ,
                            user_id UUID NOT NULL,
                            role_id UUID NOT NULL,
                            assigned_by UUID ,
                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
                            CONSTRAINT fk_user_roles_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_user_roles_role
                                FOREIGN KEY (role_id)
                                    REFERENCES roles(id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_user_roles_assigned_by
                                FOREIGN KEY (assigned_by)
                                    REFERENCES users(id)
                                    ON DELETE RESTRICT,

    -- Unique constraint to prevent duplicate user-role assignments
                            CONSTRAINT unique_user_role
                                UNIQUE (user_id, role_id)
);

-- Create indexes for better query performance
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);
CREATE INDEX idx_user_roles_assigned_by ON user_roles(assigned_by);
CREATE INDEX idx_user_roles_created_at ON user_roles(created_at);

-- Optional: Add comments for documentation
COMMENT ON TABLE user_roles IS 'Mapping table between users and their assigned roles';
COMMENT ON COLUMN user_roles.assigned_by IS 'User ID of the administrator who assigned this role';