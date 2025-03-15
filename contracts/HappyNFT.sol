// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "../node_modules/@openzeppelin/contracts/token/ERC721/ERC721.sol";

contract HappyNFT is ERC721 {
    uint256 private _tokenIdCounter;
    mapping(address => uint256[]) private _ownerTokens;

    constructor() ERC721("HappyNFT", "HNFT") {}

    function mintNFT(address to) public returns (uint256) {
        _tokenIdCounter++;
        _mint(to, _tokenIdCounter);
        _ownerTokens[to].push(_tokenIdCounter);
        return _tokenIdCounter;
    }

    function getTokensByOwner(address owner) public view returns (uint256[] memory) {
        return _ownerTokens[owner];
    }

    function burnNFT(uint256 tokenId) public {
        require(ownerOf(tokenId) == msg.sender, "Not the token owner");
        _burn(tokenId);
    }
}