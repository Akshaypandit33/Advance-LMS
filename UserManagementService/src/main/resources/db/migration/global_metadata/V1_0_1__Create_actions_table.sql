-- V1_0_1__Create_actions_table.sql
-- Create Actions table (will be created in each tenant schema dynamically)

CREATE TABLE actions (
                         id BIGSERIAL PRIMARY KEY,
                         action VARCHAR(50) NOT NULL UNIQUE,
                         created_at TIMESTAMPTZ ,
                         updated_at TIMESTAMPTZ
);

-- Create index for actions
CREATE INDEX idx_actions_action ON actions(action) WHERE action IS NOT NULL;

