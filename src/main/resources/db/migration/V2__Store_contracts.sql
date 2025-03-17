CREATE TABLE nft_contracts (
    contract_uuid UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    contract_type VARCHAR(255) NOT NULL,
    contract_address VARCHAR(255) NOT NULL,
    contract_name VARCHAR(255) NOT NULL,
    UNIQUE (contract_type, contract_name)
);