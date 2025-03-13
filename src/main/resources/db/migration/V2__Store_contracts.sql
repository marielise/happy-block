CREATE TABLE nft_contracts (
    contract_uuid UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    contract_address VARCHAR(42) UNIQUE NOT NULL
);