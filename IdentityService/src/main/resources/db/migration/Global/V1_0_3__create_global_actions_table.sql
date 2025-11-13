CREATE TABLE global_metadata.global_actions (
                                                id UUID PRIMARY KEY,
                                                name VARCHAR(255) UNIQUE NOT NULL,
                                                description TEXT,
                                                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index on name (already unique, but we can keep for quick lookups)
CREATE INDEX idx_global_actions_name
    ON global_metadata.global_actions (name);

-- Index on created_at for sorting/filtering by creation time
CREATE INDEX idx_global_actions_created_at
    ON global_metadata.global_actions (created_at);
