CREATE TABLE IF NOT EXISTS cars
(
    id        BIGSERIAL PRIMARY KEY,
    brand     VARCHAR(16)                  NOT NULL,
    model     VARCHAR(32)                  NOT NULL,
    price     INTEGER CHECK (cars.price>0) NOT NULL
);

CREATE TABLE IF NOT EXISTS owners
(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(16)                    NOT NULL,
    age                 INTEGER CHECK (owners.price>0) NOT NULL,
    has_driving_licence BOOLEAN DEFAULT FALSE,
    car_id              BIGSERIAL REFERENCES cars(id)
);