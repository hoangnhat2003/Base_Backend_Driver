package backend.drivor.base.domain.components;

import backend.drivor.base.domain.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {

    private static final String TAG = RedisCache.class.getName();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        try {
            ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-set: " + e.getMessage());
        }
    }

    public void setWithExpire(String key, Object value, int expire) {
        try {
            ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            this.expire(key, expire);
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
        }
    }

    public void delete(String key) {
        try {
            ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
            operations.getOperations().delete(key);
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-delete: " + e.getMessage());
        }
    }

    public Object get(String key) {
        try {
            ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
            return operations.get(key);
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-getByKey: " + e.getMessage());
            LoggerUtil.e(TAG, "Set key into Redis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-getByKey: " + e.getMessage());
        }

        return null;
    }

    public void expire(String key, int expire) {
        try {
            this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-expire: " + e.getMessage());
        }
    }

    public boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-checkExistKey: " + e.getMessage());
            LoggerUtil.e(TAG, "Set key into Redis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-checkExistKey: " + e.getMessage());
        }

        return false;
    }

    public Long increase(String key) {
        try {
            ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
            return operations.increment(key);
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-increase: " + e.getMessage());
        }

        return null;
    }
}
