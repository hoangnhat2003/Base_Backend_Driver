package backend.drivor.base.domain.utils;

import backend.drivor.base.domain.exception.ServiceException;
import backend.drivor.base.domain.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;

public class ServiceExceptionUtils {

    private static final String ERROR_VERIFIED_REFRESH_TOKEN = "Refresh token was expired. Please make a new signin request";

    private static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found in database";

    private static final String ACCOUNT_NOT_FOUND = "Account not found in database";

    private static final String PERMISSION_CHANGE_PASSWORD_DENIED = "Permission denied because you have been type wrong password greater than 5 times in hour";

    private static final String WRONG_PASSWORD = "You have been type wrong password";


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

}
