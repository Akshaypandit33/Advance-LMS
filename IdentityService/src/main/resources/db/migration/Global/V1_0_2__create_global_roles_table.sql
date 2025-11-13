CREATE TABLE IF NOT EXISTS global_metadata.global_roles (
                                                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                                            name VARCHAR(255) NOT NULL UNIQUE,
                                                            description VARCHAR(500),
                                                            is_template BOOLEAN DEFAULT FALSE,
                                                            created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                                            updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Unique index is already created by the UNIQUE constraint on name
-- Index for quick lookups of template roles
CREATE INDEX idx_global_roles_template
    ON global_metadata.global_roles (is_template);
