CREATE TABLE IF NOT EXISTS happy_db.users (
   user_uuid UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
   username VARCHAR(255) NOT NULL UNIQUE,
   eth_address VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS happy_db.private_keys (
      key_uuid UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
      user_uuid uuid REFERENCES users(user_uuid),
      encrypted_key TEXT NOT NULL,  -- Store encrypted private key
      created_date TIMESTAMPTZ DEFAULT now()
);
