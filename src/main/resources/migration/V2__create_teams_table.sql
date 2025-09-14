CREATE TABLE teams (
    team_id UUID PRIMARY KEY,
    team_id_api VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_code VARCHAR(10),
    primary_color VARCHAR(20),
    secondary_color VARCHAR(20),
    text_color VARCHAR(20),
    manager_id UUID,
    manager_name VARCHAR(100)
);