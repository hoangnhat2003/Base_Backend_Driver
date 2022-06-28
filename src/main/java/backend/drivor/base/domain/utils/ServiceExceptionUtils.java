package backend.drivor.base.domain.utils;

import backend.drivor.base.domain.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;

public class ServiceExceptionUtils {

    private static final String ERROR_VERIFIED_REFRESH_TOKEN = "Refresh token was expired. Please make a new signin request";

    private static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found in database";


    public static TokenRefreshException verifiedRefreshTokenFailed(String token) {
        return new TokenRefreshException(token, ERROR_VERIFIED_REFRESH_TOKEN);
    }

    public static TokenRefreshException refreshTokenNotFound(String token) {
        return new TokenRefreshException(token, REFRESH_TOKEN_NOT_FOUND);
    }




}
