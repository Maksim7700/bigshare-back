package com.crowdmarketing.service.auth;

import com.crowdmarketing.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    private final Cache blacklist;

    public TokenService(CacheManager cacheManager) {
        this.blacklist = cacheManager.getCache(SecurityUtils.TOKEN_BLACKLIST);
    }

    public void addToBlacklist(String tokenId) {
        if (tokenId != null) {
            blacklist.put(tokenId, StringUtils.EMPTY);
        }
    }

    public boolean isOnBlacklist(String tokenId) {
        return tokenId != null && Optional.ofNullable(blacklist.get(tokenId))
                .filter(v -> v.get() != null)
                .map(v -> v.get() != null)
                .orElse(false);
    }
}