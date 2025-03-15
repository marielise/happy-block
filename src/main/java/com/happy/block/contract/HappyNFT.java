package com.happy.block.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.3.
 */
@SuppressWarnings("rawtypes")
public class HappyNFT extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040518060400160405280600881526020017f48617070794e46540000000000000000000000000000000000000000000000008152506040518060400160405280600481526020017f484e465400000000000000000000000000000000000000000000000000000000815250816000908161008c91906102f4565b50806001908161009c91906102f4565b5050506103c6565b600081519050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061012557607f821691505b602082108103610138576101376100de565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026101a07fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610163565b6101aa8683610163565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b60006101f16101ec6101e7846101c2565b6101cc565b6101c2565b9050919050565b6000819050919050565b61020b836101d6565b61021f610217826101f8565b848454610170565b825550505050565b600090565b610234610227565b61023f818484610202565b505050565b5b818110156102635761025860008261022c565b600181019050610245565b5050565b601f8211156102a8576102798161013e565b61028284610153565b81016020851015610291578190505b6102a561029d85610153565b830182610244565b50505b505050565b600082821c905092915050565b60006102cb600019846008026102ad565b1980831691505092915050565b60006102e483836102ba565b9150826002028217905092915050565b6102fd826100a4565b67ffffffffffffffff811115610316576103156100af565b5b610320825461010d565b61032b828285610267565b600060209050601f83116001811461035e576000841561034c578287015190505b61035685826102d8565b8655506103be565b601f19841661036c8661013e565b60005b828110156103945784890151825560018201915060208501945060208101905061036f565b868310156103b157848901516103ad601f8916826102ba565b8355505b6001600288020188555050505b505050505050565b61221e806103d56000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c806354ba0f2711610097578063a22cb46511610066578063a22cb465146102d1578063b88d4fde146102ed578063c87b56dd14610309578063e985e9c51461033957610100565b806354ba0f27146102235780636352211e1461025357806370a082311461028357806395d89b41146102b357610100565b806323b872dd116100d357806323b872dd1461019f5780632890e0d7146101bb57806340398d67146101d757806342842e0e1461020757610100565b806301ffc9a71461010557806306fdde0314610135578063081812fc14610153578063095ea7b314610183575b600080fd5b61011f600480360381019061011a91906118a5565b610369565b60405161012c91906118ed565b60405180910390f35b61013d61044b565b60405161014a9190611998565b60405180910390f35b61016d600480360381019061016891906119f0565b6104dd565b60405161017a9190611a5e565b60405180910390f35b61019d60048036038101906101989190611aa5565b6104f9565b005b6101b960048036038101906101b49190611ae5565b61050f565b005b6101d560048036038101906101d091906119f0565b610611565b005b6101f160048036038101906101ec9190611b38565b610693565b6040516101fe9190611c23565b60405180910390f35b610221600480360381019061021c9190611ae5565b61072a565b005b61023d60048036038101906102389190611b38565b61074a565b60405161024a9190611c54565b60405180910390f35b61026d600480360381019061026891906119f0565b6107e2565b60405161027a9190611a5e565b60405180910390f35b61029d60048036038101906102989190611b38565b6107f4565b6040516102aa9190611c54565b60405180910390f35b6102bb6108ae565b6040516102c89190611998565b60405180910390f35b6102eb60048036038101906102e69190611c9b565b610940565b005b61030760048036038101906103029190611e10565b610956565b005b610323600480360381019061031e91906119f0565b61097b565b6040516103309190611998565b60405180910390f35b610353600480360381019061034e9190611e93565b6109e4565b60405161036091906118ed565b60405180910390f35b60007f80ac58cd000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916148061043457507f5b5e139f000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916145b80610444575061044382610a78565b5b9050919050565b60606000805461045a90611f02565b80601f016020809104026020016040519081016040528092919081815260200182805461048690611f02565b80156104d35780601f106104a8576101008083540402835291602001916104d3565b820191906000526020600020905b8154815290600101906020018083116104b657829003601f168201915b5050505050905090565b60006104e882610ae2565b506104f282610b6a565b9050919050565b61050b8282610506610ba7565b610baf565b5050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036105815760006040517f64a0ae920000000000000000000000000000000000000000000000000000000081526004016105789190611a5e565b60405180910390fd5b60006105958383610590610ba7565b610bc1565b90508373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161461060b578382826040517f64283d7b00000000000000000000000000000000000000000000000000000000815260040161060293929190611f33565b60405180910390fd5b50505050565b3373ffffffffffffffffffffffffffffffffffffffff16610631826107e2565b73ffffffffffffffffffffffffffffffffffffffff1614610687576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161067e90611fb6565b60405180910390fd5b61069081610ddb565b50565b6060600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002080548060200260200160405190810160405280929190818152602001828054801561071e57602002820191906000526020600020905b81548152602001906001019080831161070a575b50505050509050919050565b61074583838360405180602001604052806000815250610956565b505050565b60006006600081548092919061075f90612005565b919050555061077082600654610e61565b600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060065490806001815401808255809150506001900390600052602060002001600090919091909150556006549050919050565b60006107ed82610ae2565b9050919050565b60008073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036108675760006040517f89c62b6400000000000000000000000000000000000000000000000000000000815260040161085e9190611a5e565b60405180910390fd5b600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b6060600180546108bd90611f02565b80601f01602080910402602001604051908101604052809291908181526020018280546108e990611f02565b80156109365780601f1061090b57610100808354040283529160200191610936565b820191906000526020600020905b81548152906001019060200180831161091957829003601f168201915b5050505050905090565b61095261094b610ba7565b8383610f5a565b5050565b61096184848461050f565b61097561096c610ba7565b858585856110c9565b50505050565b606061098682610ae2565b50600061099161127a565b905060008151116109b157604051806020016040528060008152506109dc565b806109bb84611291565b6040516020016109cc929190612089565b6040516020818303038152906040525b915050919050565b6000600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16905092915050565b60007f01ffc9a7000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916149050919050565b600080610aee8361135f565b9050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610b6157826040517f7e273289000000000000000000000000000000000000000000000000000000008152600401610b589190611c54565b60405180910390fd5b80915050919050565b60006004600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b600033905090565b610bbc838383600161139c565b505050565b600080610bcd8461135f565b9050600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614610c0f57610c0e818486611561565b5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610ca057610c5160008560008061139c565b6001600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055505b600073ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff1614610d23576001600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055505b846002600086815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550838573ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a4809150509392505050565b6000610dea6000836000610bc1565b9050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610e5d57816040517f7e273289000000000000000000000000000000000000000000000000000000008152600401610e549190611c54565b60405180910390fd5b5050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1603610ed35760006040517f64a0ae92000000000000000000000000000000000000000000000000000000008152600401610eca9190611a5e565b60405180910390fd5b6000610ee183836000610bc1565b9050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610f555760006040517f73c6ac6e000000000000000000000000000000000000000000000000000000008152600401610f4c9190611a5e565b60405180910390fd5b505050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1603610fcb57816040517f5b08ba18000000000000000000000000000000000000000000000000000000008152600401610fc29190611a5e565b60405180910390fd5b80600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31836040516110bc91906118ed565b60405180910390a3505050565b60008373ffffffffffffffffffffffffffffffffffffffff163b1115611273578273ffffffffffffffffffffffffffffffffffffffff1663150b7a02868685856040518563ffffffff1660e01b81526004016111289493929190612102565b6020604051808303816000875af192505050801561116457506040513d601f19601f820116820180604052508101906111619190612163565b60015b6111e8573d8060008114611194576040519150601f19603f3d011682016040523d82523d6000602084013e611199565b606091505b5060008151036111e057836040517f64a0ae920000000000000000000000000000000000000000000000000000000081526004016111d79190611a5e565b60405180910390fd5b805181602001fd5b63150b7a0260e01b7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916817bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19161461127157836040517f64a0ae920000000000000000000000000000000000000000000000000000000081526004016112689190611a5e565b60405180910390fd5b505b5050505050565b606060405180602001604052806000815250905090565b6060600060016112a084611625565b01905060008167ffffffffffffffff8111156112bf576112be611ce5565b5b6040519080825280601f01601f1916602001820160405280156112f15781602001600182028036833780820191505090505b509050600082602001820190505b600115611354578080600190039150507f3031323334353637383961626364656600000000000000000000000000000000600a86061a8153600a858161134857611347612190565b5b049450600085036112ff575b819350505050919050565b60006002600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b80806113d55750600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614155b156115095760006113e584610ae2565b9050600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff161415801561145057508273ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614155b8015611463575061146181846109e4565b155b156114a557826040517fa9fbf51f00000000000000000000000000000000000000000000000000000000815260040161149c9190611a5e565b60405180910390fd5b811561150757838573ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45b505b836004600085815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050505050565b61156c838383611778565b61162057600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16036115e157806040517f7e2732890000000000000000000000000000000000000000000000000000000081526004016115d89190611c54565b60405180910390fd5b81816040517f177e802f0000000000000000000000000000000000000000000000000000000081526004016116179291906121bf565b60405180910390fd5b505050565b600080600090507a184f03e93ff9f4daa797ed6e38ed64bf6a1f0100000000000000008310611683577a184f03e93ff9f4daa797ed6e38ed64bf6a1f010000000000000000838161167957611678612190565b5b0492506040810190505b6d04ee2d6d415b85acef810000000083106116c0576d04ee2d6d415b85acef810000000083816116b6576116b5612190565b5b0492506020810190505b662386f26fc1000083106116ef57662386f26fc1000083816116e5576116e4612190565b5b0492506010810190505b6305f5e1008310611718576305f5e100838161170e5761170d612190565b5b0492506008810190505b612710831061173d57612710838161173357611732612190565b5b0492506004810190505b60648310611760576064838161175657611755612190565b5b0492506002810190505b600a831061176f576001810190505b80915050919050565b60008073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff161415801561183057508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff1614806117f157506117f084846109e4565b5b8061182f57508273ffffffffffffffffffffffffffffffffffffffff1661181783610b6a565b73ffffffffffffffffffffffffffffffffffffffff16145b5b90509392505050565b6000604051905090565b600080fd5b600080fd5b60007fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b6118828161184d565b811461188d57600080fd5b50565b60008135905061189f81611879565b92915050565b6000602082840312156118bb576118ba611843565b5b60006118c984828501611890565b91505092915050565b60008115159050919050565b6118e7816118d2565b82525050565b600060208201905061190260008301846118de565b92915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015611942578082015181840152602081019050611927565b60008484015250505050565b6000601f19601f8301169050919050565b600061196a82611908565b6119748185611913565b9350611984818560208601611924565b61198d8161194e565b840191505092915050565b600060208201905081810360008301526119b2818461195f565b905092915050565b6000819050919050565b6119cd816119ba565b81146119d857600080fd5b50565b6000813590506119ea816119c4565b92915050565b600060208284031215611a0657611a05611843565b5b6000611a14848285016119db565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000611a4882611a1d565b9050919050565b611a5881611a3d565b82525050565b6000602082019050611a736000830184611a4f565b92915050565b611a8281611a3d565b8114611a8d57600080fd5b50565b600081359050611a9f81611a79565b92915050565b60008060408385031215611abc57611abb611843565b5b6000611aca85828601611a90565b9250506020611adb858286016119db565b9150509250929050565b600080600060608486031215611afe57611afd611843565b5b6000611b0c86828701611a90565b9350506020611b1d86828701611a90565b9250506040611b2e868287016119db565b9150509250925092565b600060208284031215611b4e57611b4d611843565b5b6000611b5c84828501611a90565b91505092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b611b9a816119ba565b82525050565b6000611bac8383611b91565b60208301905092915050565b6000602082019050919050565b6000611bd082611b65565b611bda8185611b70565b9350611be583611b81565b8060005b83811015611c16578151611bfd8882611ba0565b9750611c0883611bb8565b925050600181019050611be9565b5085935050505092915050565b60006020820190508181036000830152611c3d8184611bc5565b905092915050565b611c4e816119ba565b82525050565b6000602082019050611c696000830184611c45565b92915050565b611c78816118d2565b8114611c8357600080fd5b50565b600081359050611c9581611c6f565b92915050565b60008060408385031215611cb257611cb1611843565b5b6000611cc085828601611a90565b9250506020611cd185828601611c86565b9150509250929050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b611d1d8261194e565b810181811067ffffffffffffffff82111715611d3c57611d3b611ce5565b5b80604052505050565b6000611d4f611839565b9050611d5b8282611d14565b919050565b600067ffffffffffffffff821115611d7b57611d7a611ce5565b5b611d848261194e565b9050602081019050919050565b82818337600083830152505050565b6000611db3611dae84611d60565b611d45565b905082815260208101848484011115611dcf57611dce611ce0565b5b611dda848285611d91565b509392505050565b600082601f830112611df757611df6611cdb565b5b8135611e07848260208601611da0565b91505092915050565b60008060008060808587031215611e2a57611e29611843565b5b6000611e3887828801611a90565b9450506020611e4987828801611a90565b9350506040611e5a878288016119db565b925050606085013567ffffffffffffffff811115611e7b57611e7a611848565b5b611e8787828801611de2565b91505092959194509250565b60008060408385031215611eaa57611ea9611843565b5b6000611eb885828601611a90565b9250506020611ec985828601611a90565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611f1a57607f821691505b602082108103611f2d57611f2c611ed3565b5b50919050565b6000606082019050611f486000830186611a4f565b611f556020830185611c45565b611f626040830184611a4f565b949350505050565b7f4e6f742074686520746f6b656e206f776e657200000000000000000000000000600082015250565b6000611fa0601383611913565b9150611fab82611f6a565b602082019050919050565b60006020820190508181036000830152611fcf81611f93565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000612010826119ba565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361204257612041611fd6565b5b600182019050919050565b600081905092915050565b600061206382611908565b61206d818561204d565b935061207d818560208601611924565b80840191505092915050565b60006120958285612058565b91506120a18284612058565b91508190509392505050565b600081519050919050565b600082825260208201905092915050565b60006120d4826120ad565b6120de81856120b8565b93506120ee818560208601611924565b6120f78161194e565b840191505092915050565b60006080820190506121176000830187611a4f565b6121246020830186611a4f565b6121316040830185611c45565b818103606083015261214381846120c9565b905095945050505050565b60008151905061215d81611879565b92915050565b60006020828403121561217957612178611843565b5b60006121878482850161214e565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b60006040820190506121d46000830185611a4f565b6121e16020830184611c45565b939250505056fea2646970667358221220019846af928411ffdf8385d25f74e6891653d7240ff7da780e6b0e34284738a264736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURNNFT = "burnNFT";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_GETTOKENSBYOWNER = "getTokensByOwner";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_MINTNFT = "mintNFT";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected HappyNFT(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HappyNFT(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HappyNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HappyNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<ApprovalForAllEventResponse> getApprovalForAllEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalForAllEventResponse getApprovalForAllEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log);
        ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalForAllEventFromLog(log));
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burnNFT(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_BURNNFT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getTokensByOwner(String owner) {
        final Function function = new Function(FUNC_GETTOKENSBYOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner), 
                new org.web3j.abi.datatypes.Address(160, operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mintNFT(String to) {
        final Function function = new Function(
                FUNC_MINTNFT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to,
            BigInteger tokenId) {
        final Function function = new Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to,
            BigInteger tokenId, byte[] data) {
        final Function function = new Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator,
            Boolean approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Bool(approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
        final Function function = new Function(FUNC_TOKENURI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to,
            BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static HappyNFT load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new HappyNFT(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HappyNFT load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HappyNFT(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HappyNFT load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new HappyNFT(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HappyNFT load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HappyNFT(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HappyNFT> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HappyNFT.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<HappyNFT> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HappyNFT.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<HappyNFT> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HappyNFT.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<HappyNFT> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HappyNFT.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }
}
