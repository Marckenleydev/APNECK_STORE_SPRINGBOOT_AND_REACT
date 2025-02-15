package marc.dev.ecommerce.spring.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import marc.dev.ecommerce.spring.domain.Token;
import marc.dev.ecommerce.spring.domain.TokenData;
import marc.dev.ecommerce.spring.dto.User;
import marc.dev.ecommerce.spring.enumeration.TokenType;

import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    String createToken(User user, Function<Token, String> tokenFunction);
    Optional<String> extractToken(HttpServletRequest request, String tokenType);
    void addCookie(HttpServletResponse response, User user, TokenType type);
    <T> T getTokenData(String token, Function<TokenData, T> tokenFunction);
    void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName);
}
