# happy-block

## db installation: 

* Install postgresql
Cf: brew

### Initialize the db:
```
create database happy_db;
create user happy_s with password 'h@pp1_d8';
grant all PRIVILEGES ON DATABASE happy_db to happy_s;
create user happy_w with password 'h@pp1_d8_w';
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