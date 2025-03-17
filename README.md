# happy-block

This is a java - spring boot blockchain project on Ethereum using web3j library.
The main API are on the principle of Raffle tickets using ERC-721 contract and token principle. The contract is called HappyRaffleNFT.

An Admin can create a raffle and pick a winner.
A participant can get a ticket (mint token), transfer the ticket or destroy it

There is a possibility to add new users to the system, and creating their wallet or by registering existing users.

## db installation: 

* Install postgresql
Cf: brew for Mac people (Sorry did not had the time to do a proper installation guide for other OS)

### Initialize the db:
```
create database happy_db;
create user happy_s with password 'h@pp1_d8_or_get_your_own_password';
grant all PRIVILEGES ON DATABASE happy_db to happy_s;
create user happy_w with password 'h@pp1_d8_w_or_get_your_own_password';
```

After starting the application, because it's configured with the besu node and the miner:
```
INSERT INTO happy_db.users (user_uuid, username, eth_address) VALUES ('997f9f7b-91eb-450b-b336-37a9351c0c3e', 'myCorp', '0x2fb9177ed1d952b3cf2c6821f5db3f5cf9ddfdf9');
INSERT INTO happy_db.happy_wallet (wallet_uuid, user_uuid, address, private_encrypted_key, json_file_path, encrypted_password, created_date) VALUES ('603f7f72-fbf7-415c-9dea-580f58355839', '997f9f7b-91eb-450b-b336-37a9351c0c3e', '0xdff18769b14973556fdb7e6f31c9d9e9f7aa8a1e', 'MdmK/IdgCiL54dKAoQc4wF6HfYxyBk9+1ZvHLTEr9MB55j01HW8AyTvop44jCTSc7qlrCkoTYqGHG/KjNL5zS2MeLVXM02RR104QQglHxtc=', './wallet/UTC--2025-03-14T04-16-04.243301000Z--dff18769b14973556fdb7e6f31c9d9e9f7aa8a1e.json', 'S9lu/iyDdSZfLKhiBs3mUWDKAxh3dTPisM2mrBtpWQw=', '2025-03-14 04:16:56.560259 +00:00');

```

## Software and packages installation

### Node.js
Through Brew
```
brew install node
```

### npm
```
brew install npm
```

#### Solidity => for smart contract languages and libraries

```
brew install solidity   #version 0.8.29
mkdir contracts
cd contracts
npm init -y
npm install @openzeppelin/contracts
```

Create the contract

then compile and generate binaries:
```
cd contracts
solc --bin --abi --evm-version paris --overwrite -o build/ HappyNFT.sol       
```

Generate java code:
```
web3j generate solidity -b build/HappyNFT.bin -a build/HappyNFT.abi -o ../src/main/java -p com.happy.block.contract
``` 


## Start Besu with config

Check genesis.json file in besu-config folder

```
besu --data-path=data/dev \
     --genesis-file=genesis.json \
     --rpc-http-enabled \
     --rpc-http-api=ETH,NET,WEB3 \
     --rpc-http-cors-origins="*" \
     --rpc-http-host=0.0.0.0 \
     --rpc-http-port=8545 \
     --min-gas-price=0 \
     --miner-enabled \
     --miner-coinbase=0xdff18769b14973556fdb7e6f31c9d9e9f7aa8a1e

```

# Project evolution

Correct the docker compose and interaction with besu nodes (ran out of time). 

## 1 - Add OAuth or SAML ... OpenId (with google , it's free)
Going in https://console.cloud.google.com/welcome and configuring a new client to have a key and a client-secret

## 2 - Using a vault to store the keys including the encryption key
It can be loaded at runtime through env variables 
HAPPY_ENCRYPT_ENCRYPTIONKEY 

## 3 - Error and validation management
Add proper exceptions and API validation
Check on unique for db insertion. 

## 4 - Add a prize with the raffle

## 5 - Clean some of the POC first tries

## 6 - Clean some of the contract to make it more efficient and less expensive
Removing the _winner variables and using events to get the winner.

## 7 - Using proper async management through the API






