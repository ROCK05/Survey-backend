CREATE TABLE IF NOT EXISTS survey (
id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
survey_name VARCHAR(1000),
survey_description VARCHAR(5000),
surveyor_id BIGINT NOT NULL,
is_general BOOLEAN NOT NULL,
start_date DATE,
end_date DATE,
time_limit BIGINT,
visibility BOOLEAN NOT NULL,
total_points INT
);