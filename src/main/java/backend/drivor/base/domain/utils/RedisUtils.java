package backend.drivor.base.domain.utils;

import backend.drivor.base.domain.components.RedisClient;
import redis.clients.jedis.Jedis;

public class RedisUtils {

    private static final String TAG = RedisUtils.class.getName();

    public static boolean setWithExpire(String key, String value, int seconds) {
        Jedis jedis = RedisClient.getClient();
        try {
            jedis.setex(key, seconds, value);
            return true;
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
            LoggerUtil.e(TAG, "Set jedis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
        } finally {
            if (jedis != null && jedis.isConnected())
                jedis.close();
        }
        return false;
    }

    public static boolean set(String key, String value) {
        Jedis jedis = RedisClient.getClient();
        try {
            jedis.set(key, value);
            return true;
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
            LoggerUtil.e(TAG, "Set jedis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-set: " + e.getMessage());
        } finally {
            if (jedis != null && jedis.isConnected())
                jedis.close();
        }
        return false;
    }

    public static String get(String key) {
        Jedis jedis = RedisClient.getClient();
        try {
            return jedis.get(key);
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
            LoggerUtil.e(TAG, "Set jedis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-get: " + e.getMessage());

        } finally {
            if (jedis != null && jedis.isConnected())
                jedis.close();
        }
        return null;
    }

    public static void delete(String key) {
        Jedis jedis = RedisClient.getClient();
        try {
            jedis.del(key);
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
            LoggerUtil.e(TAG, "Set jedis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-delete: " + e.getMessage());
        } finally {
            if (jedis != null && jedis.isConnected())
                jedis.close();
        }
    }

    public static long increase(String key) {
        Jedis jedis = RedisClient.getClient();
        try {
            return jedis.incr(key);
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
            LoggerUtil.e(TAG, "Set jedis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-increase: " + e.getMessage());
        } finally {
            if (jedis != null && jedis.isConnected())
                jedis.close();
        }
        return -1;
    }

    public static long expire(String key, int seconds) {
        Jedis jedis = RedisClient.getClient();
        try {
            return jedis.expire(key, seconds);
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
            LoggerUtil.e(TAG, "Set jedis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-expire: " + e.getMessage());
        } finally {
            if (jedis != null && jedis.isConnected())
                jedis.close();
        }
        return -1;
    }

    public static boolean exists(String key) {
        Jedis jedis = RedisClient.getClient();
        try {
            return jedis.exists(key);
        } catch (NullPointerException e) {
            LoggerUtil.e(TAG, "Redis error-setWithExpire: " + e.getMessage());
            LoggerUtil.e(TAG, "Set jedis before using");
        } catch (Exception e) {
            LoggerUtil.e(TAG, "Redis error-exists: " + e.getMessage());
        } finally {
            if (jedis != null && jedis.isConnected())
                jedis.close();
        }
        return false;
    }

}
