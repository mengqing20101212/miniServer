package ly.bot.stats;

import ly.LoggerDef;
import org.slf4j.Logger;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 机器人统计组件，用于记录协议延迟、响应分布和压测指标。
 */
public class PacketLatencyStats {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    // 存储请求包的发送时间戳，键为请求ID
    private final Map<String, Long> requestTimestamps = new ConcurrentHashMap<>();
    
    // 统计信息
    private final AtomicLong totalRequests = new AtomicLong(0);
    private final AtomicLong slowRequests = new AtomicLong(0); // 超过200ms的请求
    private final AtomicLong totalLatency = new AtomicLong(0);
    private volatile long avgLatency = 0;
    
    // 慢请求记录（超过200ms）
    private final Map<Integer, Long> slowRequestCounts = new ConcurrentHashMap<>();
    
    /**
     * 记录请求包发送时间
     * @param requestId 请求唯一标识
     * @param cmd 命令类型
     */
    public void recordRequestSent(String requestId, int cmd) {
        requestTimestamps.put(requestId, System.currentTimeMillis());
        totalRequests.incrementAndGet();
    }
    
    /**
     * 记录响应包接收时间并计算延迟
     * @param requestId 请求唯一标识
     * @param responseCmd 响应命令类型
     */
    public void recordResponseReceived(String requestId, int responseCmd) {
        Long sentTime = requestTimestamps.remove(requestId);
        if (sentTime != null) {
            long latency = System.currentTimeMillis() - sentTime;
            updateStats(latency, responseCmd);
        }
    }
    
    /**
     * 更新统计信息
     */
    private void updateStats(long latency, int cmd) {
        totalLatency.addAndGet(latency);
        long currentTotal = totalRequests.get();
        if (currentTotal > 0) {
            avgLatency = totalLatency.get() / currentTotal;
        }
        
        if (latency > 200) {
            // 记录慢请求
            slowRequestCounts.merge(cmd, 1L, Long::sum);
            slowRequests.incrementAndGet();
            
            logger.warn("慢请求警告: cmd={}, latency={}ms", cmd, latency);
        }
    }
    
    /**
     * 获取平均延迟
     */
    public long getAverageLatency() {
        return avgLatency;
    }
    
    /**
     * 获取总请求数
     */
    public long getTotalRequests() {
        return totalRequests.get();
    }
    
    /**
     * 获取慢请求数（>200ms）
     */
    public long getSlowRequests() {
        return slowRequests.get();
    }
    
    /**
     * 获取慢请求比例
     */
    public double getSlowRequestRatio() {
        long total = getTotalRequests();
        return total > 0 ? (double) getSlowRequests() / total : 0.0;
    }
    
    /**
     * 输出统计报告
     */
    public void printStats() {
        logger.info("=== 网络延迟统计报告 ===");
        logger.info("总请求数: {}", getTotalRequests());
        logger.info("平均延迟: {}ms", getAverageLatency());
        logger.info("慢请求数 (>200ms): {}", getSlowRequests());
        logger.info("慢请求比例: {:.2%}", getSlowRequestRatio());
        
        if (!slowRequestCounts.isEmpty()) {
            logger.info("--- 慢请求分布 ---");
            slowRequestCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .forEach(entry -> 
                    logger.info("Cmd {}: {} 次慢请求", entry.getKey(), entry.getValue())
                );
        }
        logger.info("===================");
    }
    
    /**
     * 重置统计信息
     */
    public void resetStats() {
        requestTimestamps.clear();
        totalRequests.set(0);
        slowRequests.set(0);
        totalLatency.set(0);
        avgLatency = 0;
        slowRequestCounts.clear();
    }
}
