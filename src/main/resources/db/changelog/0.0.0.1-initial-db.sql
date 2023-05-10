CREATE TABLE country
(
    country_code TEXT PRIMARY KEY,
    name         TEXT
);

CREATE TABLE statistic
(
    id           BIGSERIAL,
    country_code TEXT,
    cases        BIGINT,
    status       TEXT,
    date         DATE
);

CREATE TABLE statistic_update
(
    country_code       TEXT PRIMARY KEY,
    last_update        DATE,
    last_date          DATE,
    previous_max_cases BIGINT
)