CREATE TABLE global_metadata.global_role_permissions (
                                                         id UUID PRIMARY KEY,
                                                         role_id UUID NOT NULL,
                                                         permission_id UUID NOT NULL,
                                                         created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                                         updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                                                         CONSTRAINT uk_role_permission UNIQUE (role_id, permission_id),
                                                         CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id)
                                                             REFERENCES global_metadata.global_roles (id)
                                                             ON DELETE CASCADE,
                                                         CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id)
                                                             REFERENCES global_metadata.global_permissions (id)
                                                             ON DELETE CASCADE
);

-- Index to speed up lookups by role_id
CREATE INDEX idx_global_roles_id
    ON global_metadata.global_role_permissions (role_id);

-- Index to speed up lookups by permission_id
CREATE INDEX idx_global_permissions_id
    ON global_metadata.global_role_permissions (permission_id);
