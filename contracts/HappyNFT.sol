// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "../node_modules/@openzeppelin/contracts/token/ERC721/ERC721.sol";

contract HappyNFT is ERC721 {
    uint256 private _tokenIdCounter;

    constructor() ERC721("HappyNFT", "HNFT") {}

    function mintNFT(address to) public returns (uint256) {
        _tokenIdCounter++;
        _mint(to, _tokenIdCounter);
        return _tokenIdCounter;
    }
}