package control.tower.user.service.core.constants;

public class LogMessages {

    private LogMessages() {
        throw new IllegalStateException("Constants class");
    }

    public static final String FETCHING_FIND_ALL_ADDRESSES_FOR_USER_QUERY = "Fetching findAllAddressesForUserQuery";
    public static final String ISSUING_REMOVE_ADDRESS_COMMAND_FOR_ADDRESS_WITH_ID =
            "Issuing remove address command for address: %s";
    public static final String EXCEPTION_ENCOUNTERED_WHEN_ISSUING_REMOVE_ADDRESS_COMMAND_FOR_ADDRESS_WITH_ID =
            "Exception encountered when issuing remove address command for address id: %s";
    public static final String FETCHING_FIND_ALL_PAYMENT_METHODS_FOR_USER_QUERY = "Fetching findAllPaymentMethodsForUserQuery";
    public static final String ISSUING_REMOVE_PAYMENT_METHOD_COMMAND_FOR_PAYMENT_METHOD_WITH_ID =
            "Issuing remove payment method command for payment method: %s";
    public static final String EXCEPTION_ENCOUNTERED_WHEN_ISSUING_REMOVE_PAYMENT_METHOD_COMMAND_FOR_PAYMENT_METHOD_WITH_ID =
            "Exception encountered when issuing remove payment method command for payment method id: %s";
}