package backend.drivor.base.config.redis;

import lombok.Data;
import lombok.Value;

@Data
public class RedisServerConfig {

    @org.springframework.beans.factory.annotation.Value("${redis.host}")
    private String host;

    @org.springframework.beans.factory.annotation.Value("${redis.port}")
    private int port;

    @org.springframework.beans.factory.annotation.Value("${redis.max_pool}")
    private int max_pool;


}
