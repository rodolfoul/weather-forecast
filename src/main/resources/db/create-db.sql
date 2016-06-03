CREATE TABLE IF NOT EXISTS city_reference
(
  api_id INT PRIMARY KEY NOT NULL,
  name VARCHAR(100) NOT NULL,
  country_code VARCHAR(2) NOT NULL,
  UNIQUE (name, country_code)
);

CREATE TABLE IF NOT EXISTS city
(
  name VARCHAR(100) PRIMARY KEY REFERENCES city_reference(name)
);

