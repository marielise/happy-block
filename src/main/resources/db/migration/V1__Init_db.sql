CREATE TABLE IF NOT EXISTS happy_db.users (
   id uuid PRIMARY KEY,
   username VARCHAR(255) NOT NULL UNIQUE,
   eth_address VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS happy_db.private_keys (
      id uuid PRIMARY KEY,
      user_id uuid REFERENCES users(id),
      encrypted_key TEXT NOT NULL,  -- Store encrypted private key
      created_date TIMESTAMPTZ DEFAULT now()
);
