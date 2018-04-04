package com.dptest.webscoketdemo.common;

import com.dptest.webscoketdemo.entity.Message;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 描述:
 *
 * @author dengpeng
 * @create 2018-04-03 14:30
 */
public class LoggerQueue {

  //队列大小
  private static final int QUEUE_MAX_SIZE = 10000;

  private static LoggerQueue alarmMessageQueue = new LoggerQueue();
  //阻塞队列
  private BlockingQueue blockingQueue = new LinkedBlockingQueue(QUEUE_MAX_SIZE);

  private LoggerQueue() {
  }

  public static LoggerQueue getInstance() {

    return alarmMessageQueue;
  }

  /**
   * 消息入队
   */
  @SuppressWarnings({"UnusedReturnValue", "unchecked"})
  public boolean push(Message log) {
    return this.blockingQueue.add(log);//队列满了就抛出异常，不阻塞
  }

  /**
   * 消息出队
   */
  public Message poll() {
    Message result = null;
    try {
      result = (Message) this.blockingQueue.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  }

}
