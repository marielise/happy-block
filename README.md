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
```angular2html
brew install node
```

### npm
```angular2html
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
solc --bin --abi --optimize --overwrite -o build/ HappyNFT.sol
```

Generate java code:
```
web3j generate solidity -b build/HappyNFT.bin -a build/HappyNFT.abi -o src/main/java -p com.happy.block
``` 
