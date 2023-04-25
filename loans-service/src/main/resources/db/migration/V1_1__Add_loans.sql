CREATE TABLE IF NOT EXISTS loans (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    manager_id UUID NOT NULL,
    user_id UUID NOT NULL,
    rate_id BIGINT REFERENCES rates(id),
    amount NUMERIC(12, 2) NOT NULL,
    amount_remain NUMERIC(12, 2) NOT NULL,
    created DATE NOT NULL DEFAULT CURRENT_DATE,
    last_payment DATE,
    closed BOOL NOT NULL DEFAULT false,
    CHECK ( amount >= 10000 ), -- Loans in RUB only, default min amount is 10k rubles for loan
    CHECK ( user_id != manager_id ) -- Manager can't give loan to himself
);

CREATE INDEX IF NOT EXISTS loans_user_id_idx ON loans(user_id);