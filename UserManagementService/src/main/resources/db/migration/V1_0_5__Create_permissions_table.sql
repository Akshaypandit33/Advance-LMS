-- Create Permissions table

CREATE TABLE permissions (
                             id UUID PRIMARY KEY ,
                             action_id BIGINT NOT NULL,
                             resource_id BIGINT NOT NULL,
                             created_at TIMESTAMPTZ ,
                             updated_at TIMESTAMPTZ ,
                             CONSTRAINT fk_permissions_action FOREIGN KEY (action_id) REFERENCES actions(id) ON DELETE RESTRICT,
                             CONSTRAINT fk_permissions_resource FOREIGN KEY (resource_id) REFERENCES resource(id) ON DELETE RESTRICT,
                             CONSTRAINT uk_permissions_action_resource UNIQUE (action_id, resource_id)
);

-- Create indexes for Permissions table
CREATE INDEX idx_permissions_action_id ON permissions(action_id);
CREATE INDEX idx_permissions_resource_id ON permissions(resource_id);
CREATE INDEX idx_permissions_composite ON permissions(resource_id, action_id);
