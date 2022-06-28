package backend.drivor.base.service.inf;

import backend.drivor.base.domain.document.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long accountId);

    RefreshToken verifyExpiration(RefreshToken token);
}
