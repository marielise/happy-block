// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "../node_modules/@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "../node_modules/@openzeppelin/contracts/access/Ownable.sol";

contract HappyRaffleNFT is ERC721, Ownable {
    uint256 private _tokenIdCounter;
    address[] private _participants;
    mapping(uint256 => address) private _ticketOwners;
    string public raffleName;

    constructor(string memory _raffleName) ERC721("RaffleNFT", "RAFFLE") Ownable(msg.sender) {
        raffleName = _raffleName;
    }

    function mintRaffleTicket() public {
        _tokenIdCounter++;
        _mint(msg.sender, _tokenIdCounter);

        _participants.push(msg.sender);
        _ticketOwners[_tokenIdCounter] = msg.sender;
    }

    function getParticipants() public view returns (address[] memory) {
        return _participants;
    }

    function getTicketOwner(uint256 tokenId) public view returns (address) {
        return _ticketOwners[tokenId];
    }

    function getRaffleName() public view returns (string memory) {
        return raffleName; // ✅ Return the raffle name
    }

    function pickWinner() public onlyOwner returns (address) {
        require(_participants.length > 0, "No participants in the raffle");

        uint256 winnerIndex = uint256(blockhash(block.number - 1)) % _participants.length;
        address winner = _participants[winnerIndex];

        delete _participants;
        return winner;
    }

    function burnTicket(uint256 tokenId) public {
        require(ownerOf(tokenId) == msg.sender, "Not the ticket owner");
        _burn(tokenId);
    }
}