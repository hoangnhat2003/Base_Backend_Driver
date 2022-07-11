package backend.drivor.base.domain.utils;

import backend.drivor.base.domain.exception.ServiceException;
import backend.drivor.base.domain.exception.TokenRefreshException;
import backend.drivor.base.domain.exception.XMPPGenericException;
import org.springframework.http.HttpStatus;

public class ServiceExceptionUtils {

    private static final String ERROR_VERIFIED_REFRESH_TOKEN = "Refresh token was expired. Please make a new signin request";

    private static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found in database";

    private static final String ACCOUNT_NOT_FOUND = "Account not found in database";

    private static final String PERMISSION_CHANGE_PASSWORD_DENIED = "Permission denied because you have been type wrong password greater than 5 times in hour";

    private static final String WRONG_PASSWORD = "You have been type wrong password";

    private static final String INTERNAL_SERVER_ERROR = "Internal Server";

    private static final String ERROR_ACCOUNT_OWING = "This account is owing";

    private static final String ERROR_VALUE_EXISTS = "Value exist: ";

    private static final String ERROR_MISSING_PARAM = "Missing param: ";

    private static final String ERROR_INVALID_PARAM = "Invalid param: ";

    private static final String ERROR_VEHICLE_NOT_FOUND = "Vehicle not found in database";

    private static final String ERROR_WALLET_NOT_FOUND = "Wallet not found";

    private static final String ERROR_NOT_ENOUGH_BALANCE = "Not enough balance";

    private static final String CONNECTION_FAILED = "Failed to connect to XMPP server";

    private static final String ERROR_UNAUTHORIZE = "Unauthorize";



    public static TokenRefreshException verifiedRefreshTokenFailed(String token) {
        return new TokenRefreshException(token, ERROR_VERIFIED_REFRESH_TOKEN);
    }

    public static TokenRefreshException refreshTokenNotFound(String token) {
        return new TokenRefreshException(token, REFRESH_TOKEN_NOT_FOUND);
    }

    public static ServiceException accountNotFound() {
        return new ServiceException(ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static ServiceException permissionChangePasswordDenied() {
        return new ServiceException(PERMISSION_CHANGE_PASSWORD_DENIED, HttpStatus.BAD_REQUEST);
    }

    public static ServiceException wrongPassword() {
        return new ServiceException(WRONG_PASSWORD, HttpStatus.BAD_REQUEST);
    }

    public static ServiceException internalServerError() {
        return new ServiceException(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ServiceException accountOwing() {
        return new ServiceException(ERROR_ACCOUNT_OWING, HttpStatus.NOT_ACCEPTABLE);
    }

    public static ServiceException valueExists(String value) {
        return new ServiceException(String.format(ERROR_VALUE_EXISTS, value), HttpStatus.BAD_REQUEST);
    }

    public static ServiceException missingParam(String param) {
        return new ServiceException(String.format(ERROR_MISSING_PARAM, param), HttpStatus.BAD_REQUEST);
    }

    public static ServiceException invalidParam(String param) {
        return new ServiceException(String.format(ERROR_INVALID_PARAM, param), HttpStatus.BAD_REQUEST);
    }

    public static ServiceException vehicleNotFound() {
        return new ServiceException(ERROR_VEHICLE_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }

    public static ServiceException walletNotFound() {
        return new ServiceException(ERROR_WALLET_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static ServiceException notEnoughBalance() {
        return new ServiceException(ERROR_NOT_ENOUGH_BALANCE, HttpStatus.NOT_ACCEPTABLE);
    }

    public static XMPPGenericException connectionError(String username) {
        return new XMPPGenericException(username);
    }

    public static ServiceException connectionFailed() {
        return new ServiceException(CONNECTION_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ServiceException unAuthorize() {
        return new ServiceException(ERROR_UNAUTHORIZE, HttpStatus.UNAUTHORIZED);
    }

    public static ServiceException handleApplicationException(String message) {
        return new ServiceException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
