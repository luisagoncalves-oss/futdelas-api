CREATE TABLE favorite_teams (
    favorite_team_id BIGSERIAL PRIMARY KEY,
    anonymous_user_id VARCHAR(255) NOT NULL UNIQUE,
    team_id UUID NOT NULL,
    is_favorite BOOLEAN DEFAULT true,
    created_at TIMESTAMP
);