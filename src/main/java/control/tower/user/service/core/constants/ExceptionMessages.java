package control.tower.user.service.core.constants;

import static control.tower.core.constants.ExceptionMessages.*;
import static control.tower.user.service.core.constants.DomainConstants.USER;

public class ExceptionMessages {

    private ExceptionMessages() {
        throw new IllegalStateException("Constants class");
    }

    public static final String USER_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "userId");
    public static final String FIRST_NAME_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "firstName");
    public static final String LAST_NAME_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "lastName");
    public static final String EMAIL_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "email");
    public static final String PHONE_NUMBER_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "phoneNumber");
    public static final String USER_ROLE_CANNOT_BE_NULL = String.format(PARAMETER_CANNOT_BE_NULL, "userRole");

    public static final String USER_WITH_ID_EMAIL_OR_PHONE_NUMBER_ALREADY_EXISTS =
            String.format(ENTITY_WITH_ID_ALREADY_EXISTS, USER, "%s, email %s, or phone number %s,");
    public static final String USER_WITH_ID_DOES_NOT_EXIST = String.format(ENTITY_WITH_ID_DOES_NOT_EXIST, USER, "%s");
    public static final String FAILED_TO_REMOVE_ADDRESS_WITH_ID_CANCELLING_REMOVE_USER_COMMAND = "Failed to remove address %s, cancelling remove user command";
    public static final String FAILED_TO_REMOVE_PAYMENT_METHOD_WITH_ID_CANCELLING_REMOVE_USER_COMMAND = "Failed to remove payment method %s, cancelling remove user command";
}
