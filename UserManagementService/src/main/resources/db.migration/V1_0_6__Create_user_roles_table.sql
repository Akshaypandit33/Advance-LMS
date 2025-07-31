-- Create User Roles table (Many-to-Many relationship)

CREATE TABLE user_roles (
                            id UUID PRIMARY KEY ,
                            user_id UUID NOT NULL,
                            role_id UUID NOT NULL,
                            assigned_by UUID, -- Optional: track who assigned the role
                            assigned_at TIMESTAMPTZ ,
                            created_at TIMESTAMPTZ ,
                            updated_at TIMESTAMPTZ ,
                            CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
                            CONSTRAINT fk_user_roles_assigned_by FOREIGN KEY (assigned_by) REFERENCES users(id) ON DELETE SET NULL,
                            CONSTRAINT uk_user_roles_user_role UNIQUE (user_id, role_id)
);

-- Create indexes for User Roles table
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);
-- CREATE INDEX idx_user_roles_assigned_by ON user_roles(assigned_by) WHERE assigned_by IS NOT NULL;


