package backend.drivor.base.service.impl;

import backend.drivor.base.domain.document.RefreshToken;
import backend.drivor.base.domain.exception.TokenRefreshException;
import backend.drivor.base.domain.repository.AccountRepository;
import backend.drivor.base.domain.repository.RefreshTokenRepository;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import backend.drivor.base.service.inf.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    public static final long REFRESH_TOKEN_VALIDITY = 5*60*60;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long accountId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setAccount(accountRepository.findById(accountId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw ServiceExceptionUtils.verifiedRefreshTokenFailed(token.getToken());
        }

        return token;
    }
}
