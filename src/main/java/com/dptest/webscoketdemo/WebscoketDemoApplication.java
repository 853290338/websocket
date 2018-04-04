package com.dptest.webscoketdemo;

import com.dptest.webscoketdemo.common.LoggerQueue;
import com.dptest.webscoketdemo.entity.Message;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebscoketDemoApplication {

  @Autowired
  SimpMessagingTemplate messagingTemplate;
  int info = 1;
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public static void main(String[] args) {
    SpringApplication.run(WebscoketDemoApplication.class, args);
  }

  @Scheduled(fixedRate = 1000L)
  public void outputLogger() {
    logger.info("测试日志输出" + info++);
  }

  /**
   * 推送日志到/topic/pullLogger
   */
  @PostConstruct
  public void pushLogger() {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        while (true) {
          try {
            Message message = LoggerQueue.getInstance().poll();
            if (message != null) {
              if (messagingTemplate != null) {
                messagingTemplate.convertAndSend("/topic/pullLogger", message);
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    };
    executorService.submit(runnable);
  }
}
