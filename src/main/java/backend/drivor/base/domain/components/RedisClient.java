package backend.drivor.base.domain.components;

import backend.drivor.base.config.redis.RedisServerConfig;
import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class RedisClient {

    private static final String TAG = RedisClient.class.getName();


    private RedisServerConfig config;
    private JedisPoolConfig poolConfig;
    private JedisPool jedisPool;

    private static RedisClient instance;

    private JedisPoolConfig buildConfig() {
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(this.config.getMax_pool());
        config.setMaxIdle(10);
        config.setMinIdle(1);
        return config;
    }

    @PostConstruct
    public void init() {
        try {
            poolConfig = buildConfig();
            jedisPool = new JedisPool(poolConfig, config.getHost(), config.getPort(), 10000);
            LoggerUtil.i(TAG, "Init RedisClient successfully with host=" + config.getHost() + " and port=" + config.getPort());

        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
        }
    }

    public static Jedis getClient() {
        try {
            return instance.jedisPool.getResource();
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Could not get resource from the pool - " + e.getMessage() + "\n");
        }
        return null;
    }


}
