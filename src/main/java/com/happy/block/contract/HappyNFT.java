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
    public static final String BINARY = "608060405234801561000f575f5ffd5b506040518060400160405280600881526020016712185c1c1e53919560c21b815250604051806040016040528060048152602001631213919560e21b815250815f908161005c9190610109565b5060016100698282610109565b5050506101c3565b634e487b7160e01b5f52604160045260245ffd5b600181811c9082168061009957607f821691505b6020821081036100b757634e487b7160e01b5f52602260045260245ffd5b50919050565b601f82111561010457805f5260205f20601f840160051c810160208510156100e25750805b601f840160051c820191505b81811015610101575f81556001016100ee565b50505b505050565b81516001600160401b0381111561012257610122610071565b610136816101308454610085565b846100bd565b6020601f821160018114610168575f83156101515750848201515b5f19600385901b1c1916600184901b178455610101565b5f84815260208120601f198516915b828110156101975787850151825560209485019460019092019101610177565b50848210156101b457868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b610f52806101d05f395ff3fe608060405234801561000f575f5ffd5b50600436106100e5575f3560e01c80636352211e11610088578063a22cb46511610063578063a22cb465146101db578063b88d4fde146101ee578063c87b56dd14610201578063e985e9c514610214575f5ffd5b80636352211e146101ad57806370a08231146101c057806395d89b41146101d3575f5ffd5b8063095ea7b3116100c3578063095ea7b31461015157806323b872dd1461016657806342842e0e1461017957806354ba0f271461018c575f5ffd5b806301ffc9a7146100e957806306fdde0314610111578063081812fc14610126575b5f5ffd5b6100fc6100f7366004610bdb565b610227565b60405190151581526020015b60405180910390f35b610119610278565b6040516101089190610c24565b610139610134366004610c36565b610307565b6040516001600160a01b039091168152602001610108565b61016461015f366004610c68565b61032e565b005b610164610174366004610c90565b61033d565b610164610187366004610c90565b6103cb565b61019f61019a366004610cca565b6103ea565b604051908152602001610108565b6101396101bb366004610c36565b610413565b61019f6101ce366004610cca565b61041d565b610119610462565b6101646101e9366004610ce3565b610471565b6101646101fc366004610d30565b61047c565b61011961020f366004610c36565b610494565b6100fc610222366004610e0d565b610505565b5f6001600160e01b031982166380ac58cd60e01b148061025757506001600160e01b03198216635b5e139f60e01b145b8061027257506301ffc9a760e01b6001600160e01b03198316145b92915050565b60605f805461028690610e3e565b80601f01602080910402602001604051908101604052809291908181526020018280546102b290610e3e565b80156102fd5780601f106102d4576101008083540402835291602001916102fd565b820191905f5260205f20905b8154815290600101906020018083116102e057829003601f168201915b5050505050905090565b5f61031182610532565b505f828152600460205260409020546001600160a01b0316610272565b61033982823361056a565b5050565b6001600160a01b03821661036b57604051633250574960e11b81525f60048201526024015b60405180910390fd5b5f610377838333610577565b9050836001600160a01b0316816001600160a01b0316146103c5576040516364283d7b60e01b81526001600160a01b0380861660048301526024820184905282166044820152606401610362565b50505050565b6103e583838360405180602001604052805f81525061047c565b505050565b600680545f91826103fa83610e76565b919050555061040b82600654610669565b505060065490565b5f61027282610532565b5f6001600160a01b038216610447576040516322718ad960e21b81525f6004820152602401610362565b506001600160a01b03165f9081526003602052604090205490565b60606001805461028690610e3e565b6103393383836106ca565b61048784848461033d565b6103c53385858585610768565b606061049f82610532565b505f6104b560408051602081019091525f815290565b90505f8151116104d35760405180602001604052805f8152506104fe565b806104dd84610890565b6040516020016104ee929190610eb1565b6040516020818303038152906040525b9392505050565b6001600160a01b039182165f90815260056020908152604080832093909416825291909152205460ff1690565b5f818152600260205260408120546001600160a01b03168061027257604051637e27328960e01b815260048101849052602401610362565b6103e58383836001610920565b5f828152600260205260408120546001600160a01b03908116908316156105a3576105a3818486610a24565b6001600160a01b038116156105dd576105be5f855f5f610920565b6001600160a01b0381165f90815260036020526040902080545f190190555b6001600160a01b0385161561060b576001600160a01b0385165f908152600360205260409020805460010190555b5f8481526002602052604080822080546001600160a01b0319166001600160a01b0389811691821790925591518793918516917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4949350505050565b6001600160a01b03821661069257604051633250574960e11b81525f6004820152602401610362565b5f61069e83835f610577565b90506001600160a01b038116156103e5576040516339e3563760e11b81525f6004820152602401610362565b6001600160a01b0382166106fc57604051630b61174360e31b81526001600160a01b0383166004820152602401610362565b6001600160a01b038381165f81815260056020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b6001600160a01b0383163b1561088957604051630a85bd0160e11b81526001600160a01b0384169063150b7a02906107aa908890889087908790600401610ec5565b6020604051808303815f875af19250505080156107e4575060408051601f3d908101601f191682019092526107e191810190610f01565b60015b61084b573d808015610811576040519150601f19603f3d011682016040523d82523d5f602084013e610816565b606091505b5080515f0361084357604051633250574960e11b81526001600160a01b0385166004820152602401610362565b805181602001fd5b6001600160e01b03198116630a85bd0160e11b1461088757604051633250574960e11b81526001600160a01b0385166004820152602401610362565b505b5050505050565b60605f61089c83610a88565b60010190505f8167ffffffffffffffff8111156108bb576108bb610d1c565b6040519080825280601f01601f1916602001820160405280156108e5576020820181803683370190505b5090508181016020015b5f19016f181899199a1a9b1b9c1cb0b131b232b360811b600a86061a8153600a85049450846108ef57509392505050565b808061093457506001600160a01b03821615155b156109f5575f61094384610532565b90506001600160a01b0383161580159061096f5750826001600160a01b0316816001600160a01b031614155b801561098257506109808184610505565b155b156109ab5760405163a9fbf51f60e01b81526001600160a01b0384166004820152602401610362565b81156109f35783856001600160a01b0316826001600160a01b03167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45b505b50505f90815260046020526040902080546001600160a01b0319166001600160a01b0392909216919091179055565b610a2f838383610b5f565b6103e5576001600160a01b038316610a5d57604051637e27328960e01b815260048101829052602401610362565b60405163177e802f60e01b81526001600160a01b038316600482015260248101829052604401610362565b5f8072184f03e93ff9f4daa797ed6e38ed64bf6a1f0160401b8310610ac65772184f03e93ff9f4daa797ed6e38ed64bf6a1f0160401b830492506040015b6d04ee2d6d415b85acef81000000008310610af2576d04ee2d6d415b85acef8100000000830492506020015b662386f26fc100008310610b1057662386f26fc10000830492506010015b6305f5e1008310610b28576305f5e100830492506008015b6127108310610b3c57612710830492506004015b60648310610b4e576064830492506002015b600a83106102725760010192915050565b5f6001600160a01b03831615801590610bbb5750826001600160a01b0316846001600160a01b03161480610b985750610b988484610505565b80610bbb57505f828152600460205260409020546001600160a01b038481169116145b949350505050565b6001600160e01b031981168114610bd8575f5ffd5b50565b5f60208284031215610beb575f5ffd5b81356104fe81610bc3565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b602081525f6104fe6020830184610bf6565b5f60208284031215610c46575f5ffd5b5035919050565b80356001600160a01b0381168114610c63575f5ffd5b919050565b5f5f60408385031215610c79575f5ffd5b610c8283610c4d565b946020939093013593505050565b5f5f5f60608486031215610ca2575f5ffd5b610cab84610c4d565b9250610cb960208501610c4d565b929592945050506040919091013590565b5f60208284031215610cda575f5ffd5b6104fe82610c4d565b5f5f60408385031215610cf4575f5ffd5b610cfd83610c4d565b915060208301358015158114610d11575f5ffd5b809150509250929050565b634e487b7160e01b5f52604160045260245ffd5b5f5f5f5f60808587031215610d43575f5ffd5b610d4c85610c4d565b9350610d5a60208601610c4d565b925060408501359150606085013567ffffffffffffffff811115610d7c575f5ffd5b8501601f81018713610d8c575f5ffd5b803567ffffffffffffffff811115610da657610da6610d1c565b604051601f8201601f19908116603f0116810167ffffffffffffffff81118282101715610dd557610dd5610d1c565b604052818152828201602001891015610dec575f5ffd5b816020840160208301375f6020838301015280935050505092959194509250565b5f5f60408385031215610e1e575f5ffd5b610e2783610c4d565b9150610e3560208401610c4d565b90509250929050565b600181811c90821680610e5257607f821691505b602082108103610e7057634e487b7160e01b5f52602260045260245ffd5b50919050565b5f60018201610e9357634e487b7160e01b5f52601160045260245ffd5b5060010190565b5f81518060208401855e5f93019283525090919050565b5f610bbb610ebf8386610e9a565b84610e9a565b6001600160a01b03858116825284166020820152604081018390526080606082018190525f90610ef790830184610bf6565b9695505050505050565b5f60208284031215610f11575f5ffd5b81516104fe81610bc356fea26469706673582212205159e15e76f575e938bd09707047361ab54223171d47e305a136b462d96f0b0d64736f6c634300081d0033";

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
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
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
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
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
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
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
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log);
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
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
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
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
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
                Arrays.<Type>asList(new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new Address(160, owner),
                new Address(160, operator)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mintNFT(String to) {
        final Function function = new Function(
                FUNC_MINTNFT, 
                Arrays.<Type>asList(new Address(160, to)),
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
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to,
            BigInteger tokenId) {
        final Function function = new Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to,
            BigInteger tokenId, byte[] data) {
        final Function function = new Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId),
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator,
            Boolean approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new Address(160, operator),
                new Bool(approved)),
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
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to,
            BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
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

    public static void linkLibraries(List<LinkReference> references) {
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
