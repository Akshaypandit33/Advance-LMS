-- Create Resource table

CREATE TABLE resource (
                          id BIGSERIAL PRIMARY KEY,
                          resource_name VARCHAR(50) NOT NULL UNIQUE,
                          created_date TIMESTAMPTZ ,
                          last_modified_date TIMESTAMPTZ
);

-- Create index for resource
CREATE INDEX idx_resource_name ON resource(resource_name) WHERE resource_name IS NOT NULL;
-- Create Resource table

