package com.happy.block.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
    public static final String BINARY = "608060405234801561001057600080fd5b506040518060400160405280600881526020017f48617070794e46540000000000000000000000000000000000000000000000008152506040518060400160405280600481526020017f484e465400000000000000000000000000000000000000000000000000000000815250816000908161008c91906102f4565b50806001908161009c91906102f4565b5050506103c6565b600081519050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061012557607f821691505b602082108103610138576101376100de565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026101a07fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610163565b6101aa8683610163565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b60006101f16101ec6101e7846101c2565b6101cc565b6101c2565b9050919050565b6000819050919050565b61020b836101d6565b61021f610217826101f8565b848454610170565b825550505050565b600090565b610234610227565b61023f818484610202565b505050565b5b818110156102635761025860008261022c565b600181019050610245565b5050565b601f8211156102a8576102798161013e565b61028284610153565b81016020851015610291578190505b6102a561029d85610153565b830182610244565b50505b505050565b600082821c905092915050565b60006102cb600019846008026102ad565b1980831691505092915050565b60006102e483836102ba565b9150826002028217905092915050565b6102fd826100a4565b67ffffffffffffffff811115610316576103156100af565b5b610320825461010d565b61032b828285610267565b600060209050601f83116001811461035e576000841561034c578287015190505b61035685826102d8565b8655506103be565b601f19841661036c8661013e565b60005b828110156103945784890151825560018201915060208501945060208101905061036f565b868310156103b157848901516103ad601f8916826102ba565b8355505b6001600288020188555050505b505050505050565b611e69806103d56000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c80636352211e1161008c578063a22cb46511610066578063a22cb4651461026f578063b88d4fde1461028b578063c87b56dd146102a7578063e985e9c5146102d7576100ea565b80636352211e146101f157806370a082311461022157806395d89b4114610251576100ea565b8063095ea7b3116100c8578063095ea7b31461016d57806323b872dd1461018957806342842e0e146101a557806354ba0f27146101c1576100ea565b806301ffc9a7146100ef57806306fdde031461011f578063081812fc1461013d575b600080fd5b6101096004803603810190610104919061163c565b610307565b6040516101169190611684565b60405180910390f35b6101276103e9565b604051610134919061172f565b60405180910390f35b61015760048036038101906101529190611787565b61047b565b60405161016491906117f5565b60405180910390f35b6101876004803603810190610182919061183c565b610497565b005b6101a3600480360381019061019e919061187c565b6104ad565b005b6101bf60048036038101906101ba919061187c565b6105af565b005b6101db60048036038101906101d691906118cf565b6105cf565b6040516101e8919061190b565b60405180910390f35b61020b60048036038101906102069190611787565b6105ff565b60405161021891906117f5565b60405180910390f35b61023b600480360381019061023691906118cf565b610611565b604051610248919061190b565b60405180910390f35b6102596106cb565b604051610266919061172f565b60405180910390f35b61028960048036038101906102849190611952565b61075d565b005b6102a560048036038101906102a09190611ac7565b610773565b005b6102c160048036038101906102bc9190611787565b610798565b6040516102ce919061172f565b60405180910390f35b6102f160048036038101906102ec9190611b4a565b610801565b6040516102fe9190611684565b60405180910390f35b60007f80ac58cd000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614806103d257507f5b5e139f000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916145b806103e257506103e182610895565b5b9050919050565b6060600080546103f890611bb9565b80601f016020809104026020016040519081016040528092919081815260200182805461042490611bb9565b80156104715780601f1061044657610100808354040283529160200191610471565b820191906000526020600020905b81548152906001019060200180831161045457829003601f168201915b5050505050905090565b6000610486826108ff565b5061049082610987565b9050919050565b6104a982826104a46109c4565b6109cc565b5050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff160361051f5760006040517f64a0ae9200000000000000000000000000000000000000000000000000000000815260040161051691906117f5565b60405180910390fd5b6000610533838361052e6109c4565b6109de565b90508373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16146105a9578382826040517f64283d7b0000000000000000000000000000000000000000000000000000000081526004016105a093929190611bea565b60405180910390fd5b50505050565b6105ca83838360405180602001604052806000815250610773565b505050565b6000600660008154809291906105e490611c50565b91905055506105f582600654610bf8565b6006549050919050565b600061060a826108ff565b9050919050565b60008073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036106845760006040517f89c62b6400000000000000000000000000000000000000000000000000000000815260040161067b91906117f5565b60405180910390fd5b600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b6060600180546106da90611bb9565b80601f016020809104026020016040519081016040528092919081815260200182805461070690611bb9565b80156107535780601f1061072857610100808354040283529160200191610753565b820191906000526020600020905b81548152906001019060200180831161073657829003601f168201915b5050505050905090565b61076f6107686109c4565b8383610cf1565b5050565b61077e8484846104ad565b6107926107896109c4565b85858585610e60565b50505050565b60606107a3826108ff565b5060006107ae611011565b905060008151116107ce57604051806020016040528060008152506107f9565b806107d884611028565b6040516020016107e9929190611cd4565b6040516020818303038152906040525b915050919050565b6000600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16905092915050565b60007f01ffc9a7000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916149050919050565b60008061090b836110f6565b9050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361097e57826040517f7e273289000000000000000000000000000000000000000000000000000000008152600401610975919061190b565b60405180910390fd5b80915050919050565b60006004600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b600033905090565b6109d98383836001611133565b505050565b6000806109ea846110f6565b9050600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614610a2c57610a2b8184866112f8565b5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610abd57610a6e600085600080611133565b6001600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055505b600073ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff1614610b40576001600360008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055505b846002600086815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550838573ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a4809150509392505050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1603610c6a5760006040517f64a0ae92000000000000000000000000000000000000000000000000000000008152600401610c6191906117f5565b60405180910390fd5b6000610c78838360006109de565b9050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610cec5760006040517f73c6ac6e000000000000000000000000000000000000000000000000000000008152600401610ce391906117f5565b60405180910390fd5b505050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1603610d6257816040517f5b08ba18000000000000000000000000000000000000000000000000000000008152600401610d5991906117f5565b60405180910390fd5b80600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c3183604051610e539190611684565b60405180910390a3505050565b60008373ffffffffffffffffffffffffffffffffffffffff163b111561100a578273ffffffffffffffffffffffffffffffffffffffff1663150b7a02868685856040518563ffffffff1660e01b8152600401610ebf9493929190611d4d565b6020604051808303816000875af1925050508015610efb57506040513d601f19601f82011682018060405250810190610ef89190611dae565b60015b610f7f573d8060008114610f2b576040519150601f19603f3d011682016040523d82523d6000602084013e610f30565b606091505b506000815103610f7757836040517f64a0ae92000000000000000000000000000000000000000000000000000000008152600401610f6e91906117f5565b60405180910390fd5b805181602001fd5b63150b7a0260e01b7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916817bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19161461100857836040517f64a0ae92000000000000000000000000000000000000000000000000000000008152600401610fff91906117f5565b60405180910390fd5b505b5050505050565b606060405180602001604052806000815250905090565b606060006001611037846113bc565b01905060008167ffffffffffffffff8111156110565761105561199c565b5b6040519080825280601f01601f1916602001820160405280156110885781602001600182028036833780820191505090505b509050600082602001820190505b6001156110eb578080600190039150507f3031323334353637383961626364656600000000000000000000000000000000600a86061a8153600a85816110df576110de611ddb565b5b04945060008503611096575b819350505050919050565b60006002600083815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b808061116c5750600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614155b156112a057600061117c846108ff565b9050600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16141580156111e757508273ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614155b80156111fa57506111f88184610801565b155b1561123c57826040517fa9fbf51f00000000000000000000000000000000000000000000000000000000815260040161123391906117f5565b60405180910390fd5b811561129e57838573ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45b505b836004600085815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050505050565b61130383838361150f565b6113b757600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff160361137857806040517f7e27328900000000000000000000000000000000000000000000000000000000815260040161136f919061190b565b60405180910390fd5b81816040517f177e802f0000000000000000000000000000000000000000000000000000000081526004016113ae929190611e0a565b60405180910390fd5b505050565b600080600090507a184f03e93ff9f4daa797ed6e38ed64bf6a1f010000000000000000831061141a577a184f03e93ff9f4daa797ed6e38ed64bf6a1f01000000000000000083816114105761140f611ddb565b5b0492506040810190505b6d04ee2d6d415b85acef81000000008310611457576d04ee2d6d415b85acef8100000000838161144d5761144c611ddb565b5b0492506020810190505b662386f26fc10000831061148657662386f26fc10000838161147c5761147b611ddb565b5b0492506010810190505b6305f5e10083106114af576305f5e10083816114a5576114a4611ddb565b5b0492506008810190505b61271083106114d45761271083816114ca576114c9611ddb565b5b0492506004810190505b606483106114f757606483816114ed576114ec611ddb565b5b0492506002810190505b600a8310611506576001810190505b80915050919050565b60008073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16141580156115c757508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff16148061158857506115878484610801565b5b806115c657508273ffffffffffffffffffffffffffffffffffffffff166115ae83610987565b73ffffffffffffffffffffffffffffffffffffffff16145b5b90509392505050565b6000604051905090565b600080fd5b600080fd5b60007fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b611619816115e4565b811461162457600080fd5b50565b60008135905061163681611610565b92915050565b600060208284031215611652576116516115da565b5b600061166084828501611627565b91505092915050565b60008115159050919050565b61167e81611669565b82525050565b60006020820190506116996000830184611675565b92915050565b600081519050919050565b600082825260208201905092915050565b60005b838110156116d95780820151818401526020810190506116be565b60008484015250505050565b6000601f19601f8301169050919050565b60006117018261169f565b61170b81856116aa565b935061171b8185602086016116bb565b611724816116e5565b840191505092915050565b6000602082019050818103600083015261174981846116f6565b905092915050565b6000819050919050565b61176481611751565b811461176f57600080fd5b50565b6000813590506117818161175b565b92915050565b60006020828403121561179d5761179c6115da565b5b60006117ab84828501611772565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006117df826117b4565b9050919050565b6117ef816117d4565b82525050565b600060208201905061180a60008301846117e6565b92915050565b611819816117d4565b811461182457600080fd5b50565b60008135905061183681611810565b92915050565b60008060408385031215611853576118526115da565b5b600061186185828601611827565b925050602061187285828601611772565b9150509250929050565b600080600060608486031215611895576118946115da565b5b60006118a386828701611827565b93505060206118b486828701611827565b92505060406118c586828701611772565b9150509250925092565b6000602082840312156118e5576118e46115da565b5b60006118f384828501611827565b91505092915050565b61190581611751565b82525050565b600060208201905061192060008301846118fc565b92915050565b61192f81611669565b811461193a57600080fd5b50565b60008135905061194c81611926565b92915050565b60008060408385031215611969576119686115da565b5b600061197785828601611827565b92505060206119888582860161193d565b9150509250929050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6119d4826116e5565b810181811067ffffffffffffffff821117156119f3576119f261199c565b5b80604052505050565b6000611a066115d0565b9050611a1282826119cb565b919050565b600067ffffffffffffffff821115611a3257611a3161199c565b5b611a3b826116e5565b9050602081019050919050565b82818337600083830152505050565b6000611a6a611a6584611a17565b6119fc565b905082815260208101848484011115611a8657611a85611997565b5b611a91848285611a48565b509392505050565b600082601f830112611aae57611aad611992565b5b8135611abe848260208601611a57565b91505092915050565b60008060008060808587031215611ae157611ae06115da565b5b6000611aef87828801611827565b9450506020611b0087828801611827565b9350506040611b1187828801611772565b925050606085013567ffffffffffffffff811115611b3257611b316115df565b5b611b3e87828801611a99565b91505092959194509250565b60008060408385031215611b6157611b606115da565b5b6000611b6f85828601611827565b9250506020611b8085828601611827565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611bd157607f821691505b602082108103611be457611be3611b8a565b5b50919050565b6000606082019050611bff60008301866117e6565b611c0c60208301856118fc565b611c1960408301846117e6565b949350505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611c5b82611751565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611c8d57611c8c611c21565b5b600182019050919050565b600081905092915050565b6000611cae8261169f565b611cb88185611c98565b9350611cc88185602086016116bb565b80840191505092915050565b6000611ce08285611ca3565b9150611cec8284611ca3565b91508190509392505050565b600081519050919050565b600082825260208201905092915050565b6000611d1f82611cf8565b611d298185611d03565b9350611d398185602086016116bb565b611d42816116e5565b840191505092915050565b6000608082019050611d6260008301876117e6565b611d6f60208301866117e6565b611d7c60408301856118fc565b8181036060830152611d8e8184611d14565b905095945050505050565b600081519050611da881611610565b92915050565b600060208284031215611dc457611dc36115da565b5b6000611dd284828501611d99565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b6000604082019050611e1f60008301856117e6565b611e2c60208301846118fc565b939250505056fea2646970667358221220cfd1d850395ae3cd79e66e7f42601da2b31a2a0219f47b33f53fa1cb99b23af464736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_GETAPPROVED = "getApproved";

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

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
