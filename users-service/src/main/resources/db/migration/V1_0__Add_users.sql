CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE TABLE IF NOT EXISTS users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  credit_rating SMALLINT NOT NULL DEFAULT 100,
  CHECK ( length(password) > 7 )
);

CREATE INDEX user_name_idx ON users USING GIN (name gin_trgm_ops);