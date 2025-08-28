package com.ShiXi.user.IdentityAuthentication.common.ScheduledJob;

import com.ShiXi.user.IdentityAuthentication.common.utils.RedisConstants;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
@Slf4j
    public class waitForAuditingListZSetScheduledJob {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 每分钟扫描一次Redis中的adminAuditingBufferPoolZSet
     * 将score小于当前时间戳1分钟前的元素取出并LPUSH到waitForAuditingList
     * 同时删除这些元素对应的key
     */
    @XxlJob("waitForAuditingListZSetScheduledJob")
    public void redisZSetScanJobHandler() throws Exception {
        log.info("开始执行Redis ZSet扫描任务");

        // 获取当前时间戳和1分钟前的时间戳
        long currentTime = System.currentTimeMillis();
        long oneMinuteAgo = currentTime - 60 * 1000; // 1分钟前的时间戳

        // 从Redis中获取adminAuditingBufferPoolZSet中所有score小于1分钟前时间戳的元素
        Set<String> members = stringRedisTemplate.opsForZSet()
                .rangeByScore(RedisConstants.ADMIN_AUDITING_BUFFER_POOL_ZSET, 0, oneMinuteAgo);

        if (members != null && !members.isEmpty()) {
            log.info("找到{}个过期的审核项", members.size());

            // 将这些元素LPUSH到waitForAuditingList
            for (String member : members) {
                stringRedisTemplate.opsForList().rightPush(RedisConstants.WAIT_FOR_AUDITING_LIST, member);
                log.debug("已将元素 {} 推入 {}", member, RedisConstants.WAIT_FOR_AUDITING_LIST);
            }

            // 从ZSet中删除这些元素
            stringRedisTemplate.opsForZSet().remove(RedisConstants.ADMIN_AUDITING_BUFFER_POOL_ZSET,
                    members.toArray());

            // 删除这些元素作为key的对应值
            for (String member : members) {
                stringRedisTemplate.delete(member);
                log.debug("已删除key: {}", member);
            }
        } else {
            log.info("未找到过期的审核项");
        }

        log.info("Redis ZSet扫描任务执行完成");
    }
}
