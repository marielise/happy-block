CREATE TABLE IF NOT EXISTS happy_db.users (
   user_uuid UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
   username VARCHAR(255) NOT NULL UNIQUE,
   eth_address VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS happy_db.happy_wallet (
    wallet_uuid UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_uuid uuid REFERENCES users(user_uuid),
    address VARCHAR(255) NOT NULL,
    private_encrypted_key TEXT NOT NULL,  -- Store encrypted private key
    json_file_path TEXT NOT NULL,
    encrypted_password TEXT NOT NULL,
    created_date TIMESTAMPTZ DEFAULT now()
);
