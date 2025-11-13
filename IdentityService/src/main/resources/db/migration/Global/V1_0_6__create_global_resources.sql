CREATE TABLE global_metadata.global_resources (
                                                  id UUID PRIMARY KEY,
                                                  name VARCHAR(255) UNIQUE NOT NULL,
                                                  description TEXT,
                                                  resource_type VARCHAR(255),
                                                  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                                  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index for filtering by resource_type
CREATE INDEX idx_global_resources_resource_type
    ON global_metadata.global_resources (resource_type);
