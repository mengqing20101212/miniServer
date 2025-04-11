package ly.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Author: liuYang
 * Date: 2025/4/9
 * File: GameObject
 */
public class GameObject {
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameObject.class);
  static Logger logger = LoggerDef.SystemLogger;

  private final long guid;
  Connector connector;

  /** 上一次收到的包的序列号 */
  int lastReceivedSeq;

  /***
   * 异步 待发送的消息放在这里面，之后 单独线程统一 10ms 定时发送，时间 间隔可以调整
   */
  Queue<AbstractMessagePacket> sendPacketQueue = new ConcurrentLinkedQueue<AbstractMessagePacket>();

  /*** 所有收到的包都放在这个线程安全队列中，由业务层主动获取 */
  BlockingQueue<AbstractMessagePacket> receivePacketQueue = new ArrayBlockingQueue<>(1024);

  public GameObject(long guid) {
    this.guid = guid;
  }

  public long getGuid() {
    return guid;
  }

  public Connector getConnector() {
    return connector;
  }

  public void setConnector(Connector connector) {
    this.connector = connector;
  }

  /***
   * 添加收到的包
   */
  public void addReceivePacket(AbstractMessagePacket packet) {
    if (!canAddReceivePacket(packet)) return;
    if (!receivePacketQueue.offer(packet)) {
      logger.warn("Too many receive packets, dropping packet. size: " + receivePacketQueue.size());
    }
    this.lastReceivedSeq = packet.getSeq();
  }

  public boolean canAddReceivePacket(AbstractMessagePacket packet) {
    if (packet == null) {
      logger.error("Can't add receive packet, packet is null");
      return false;
    }
    if (packet.getSeq() != 0 && lastReceivedSeq != packet.getSeq() - 1) {
      logger.error(
          String.format(
              "sid[%s : %d]  丢包了，上一个包的序列号:%d, 当前包序列号:%d",
              connector.socketChannel.channel().id(),
              connector.sessionId,
              lastReceivedSeq,
              packet.getSeq()));
      return false;
    }
    return checkAddReceivePacket(packet);
  }

  protected boolean checkAddReceivePacket(AbstractMessagePacket packet) {
    return true;
  }

  /***
   *  该消息不会立即发送，会缓存在sendPacketQueue 由另外一个task  定时刷新 发送
   * @param packet 待发送的消息
   * @return 是否添加成功
   */
  public boolean addSendPacket(AbstractMessagePacket packet) {
    return sendPacketQueue.add(packet);
  }

  /**
   * 如果连接可用 ，则立即发送消息
   *
   * @param packet 待发送消息
   * @return true 发送成功， false发送失败
   */
  public boolean sendPacket(AbstractMessagePacket packet) {
    if (connector != null) {
      return connector.write(packet);
    }
    return false;
  }

  public void sendAllPackets() {
    if (connector != null) {
      AbstractMessagePacket sendPacket = sendPacketQueue.poll();
      while ((sendPacket = sendPacketQueue.poll()) != null) {
        if (!sendPacket(sendPacket)) {
          break;
        }
      }
    }
  }

  public void closeChannel() {
    if (connector != null && connector.isConnected()) {
      connector.close();
    }
  }

  /***
   * 批量获取当前所有接收的消息并清空队列
   */
  public List<AbstractMessagePacket> getReceivePacketList() {
    List<AbstractMessagePacket> packets = new ArrayList<>();
    receivePacketQueue.drainTo(packets); // 一次性取出所有内容
    return packets;
  }

  public void tick() {}
}
