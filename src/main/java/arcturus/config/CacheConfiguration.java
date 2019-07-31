package arcturus.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, arcturus.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, arcturus.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, arcturus.domain.User.class.getName());
            createCache(cm, arcturus.domain.Authority.class.getName());
            createCache(cm, arcturus.domain.User.class.getName() + ".authorities");
            createCache(cm, arcturus.domain.SysModule.class.getName());
            createCache(cm, arcturus.domain.SysAccess.class.getName());
            createCache(cm, arcturus.domain.SysAccess.class.getName() + ".groupIds");
            createCache(cm, arcturus.domain.SysIpGroup.class.getName());
            createCache(cm, arcturus.domain.SysIpGroup.class.getName() + ".groupIds");
            createCache(cm, arcturus.domain.SysIpGroupItem.class.getName());
            createCache(cm, arcturus.domain.SysIpGroupItem.class.getName() + ".sysIpGroups");
            createCache(cm, arcturus.domain.SysGroup.class.getName());
            createCache(cm, arcturus.domain.SysGroup.class.getName() + ".ipGroupIds");
            createCache(cm, arcturus.domain.SysGroup.class.getName() + ".accessIds");
            createCache(cm, arcturus.domain.SysGroup.class.getName() + ".enterpriseIds");
            createCache(cm, arcturus.domain.SysGroup.class.getName() + ".userIds");
            createCache(cm, arcturus.domain.SysLicense.class.getName());
            createCache(cm, arcturus.domain.SysEnterprise.class.getName());
            createCache(cm, arcturus.domain.SysEnterprise.class.getName() + ".groupIds");
            createCache(cm, arcturus.domain.SysEnterprise.class.getName() + ".userIds");
            createCache(cm, arcturus.domain.SysPersonType.class.getName());
            createCache(cm, arcturus.domain.SysPerson.class.getName());
            createCache(cm, arcturus.domain.SysUser.class.getName());
            createCache(cm, arcturus.domain.SysUser.class.getName() + ".groupIds");
            createCache(cm, arcturus.domain.SysUser.class.getName() + ".enterpriseIds");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
