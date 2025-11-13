CREATE TABLE global_metadata.global_permissions (
                                                    id UUID PRIMARY KEY,
                                                    action_id UUID NOT NULL,
                                                    resource_id UUID NOT NULL,
                                                    name VARCHAR(255) UNIQUE NOT NULL,
                                                    description TEXT,
                                                    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                                    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                                    CONSTRAINT fk_global_permissions_action FOREIGN KEY (action_id)
                                                        REFERENCES global_metadata.global_actions (id)
                                                        ON DELETE CASCADE,
                                                    CONSTRAINT fk_global_permissions_resource FOREIGN KEY (resource_id)
                                                        REFERENCES global_metadata.global_resources (id)
                                                        ON DELETE CASCADE
);

-- Index to speed up queries by action_id
CREATE INDEX idx_global_permissions_action_id
    ON global_metadata.global_permissions (action_id);

-- Index to speed up queries by resource_id
CREATE INDEX idx_global_permissions_resource_id
    ON global_metadata.global_permissions (resource_id);
