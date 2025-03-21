// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "../node_modules/@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "../node_modules/@openzeppelin/contracts/access/Ownable.sol";

contract HappyRaffleNFT is ERC721, Ownable {
    uint256 private _tokenIdCounter;
    address[] private _participants;
    mapping(uint256 => address) private _ticketOwners;
    string public raffleName;
    address public winner; //don't have time to use events to check winner

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
        return raffleName; // Return the raffle name
    }

    function pickWinner() public onlyOwner returns (address) {
        require(_participants.length > 0, "No participants in the raffle");

        uint256 randomSeed = uint256(keccak256(abi.encodePacked(block.timestamp, block.prevrandao, msg.sender))); //used chatGPT
        uint256 winnerIndex = randomSeed % _participants.length;

        winner = _participants[winnerIndex]; // Store winner in state variable

        delete _participants;
        return winner;
    }

    function getWinner() public view returns (address) {
        require(winner != address(0), "No winner has been picked yet");
        return winner;
    }

    function burnTicket(uint256 tokenId) public {
        require(ownerOf(tokenId) == msg.sender, "Not the ticket owner");

        // Find the participant and remove them from the list
        for (uint256 i = 0; i < _participants.length; i++) {
            if (_participants[i] == msg.sender) {
                _participants[i] = _participants[_participants.length - 1]; //get last
                _participants.pop(); //remove last
                break;
            }
        }
        _burn(tokenId);
    }
}