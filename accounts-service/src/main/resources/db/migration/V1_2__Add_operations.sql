-- In task description was specified that add and take operations can be "free
-- This means that we can add money from nowhere and take off to nowhere.
-- In general account_id and destination_account_id can be ATM, or QIWI, or PayPal
-- And because there is no requirements about it
-- I wont add  constraint NOT NULL to account_id and destination_account_id.

CREATE TABLE IF NOT EXISTS operations (
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  account_id BIGINT REFERENCES accounts(id),
  destination_account_id BIGINT REFERENCES accounts(id),
  is_successfully BOOL NOT NULL,
  amount NUMERIC(14, 2) NOT NULL,
  date DATE NOT NULL DEFAULT CURRENT_DATE
);