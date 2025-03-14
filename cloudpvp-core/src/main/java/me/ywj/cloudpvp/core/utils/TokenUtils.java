package me.ywj.cloudpvp.core.utils;

import lombok.Setter;
import me.ywj.cloudpvp.core.constant.header.Attributes;

import java.util.Map;

/**
 * TokenUtils
 * Token工具类
 */
@Setter
public class TokenUtils {
    private JWTUtils jwtUtils;

    public TokenUtils() {
    }

    public String generateToken(Long steamId64) {
        return jwtUtils.generateToken(Map.of(Attributes.ID, steamId64));
    }

    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    public Long getIDFromToken(String token) {
        return jwtUtils.getClaim(token, Attributes.ID);
    }
}
