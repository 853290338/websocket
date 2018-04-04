package com.dptest.webscoketdemo.config;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.dptest.webscoketdemo.common.LoggerQueue;
import com.dptest.webscoketdemo.entity.Message;
import java.text.DateFormat;
import java.util.Date;

/**
 * 描述:拦截日志推送到前台
 *
 * @author dengpeng
 * @create 2018-04-03 14:36
 */
public class LogFilter extends Filter {

  @Override
  public FilterReply decide(Object o) {
    ILoggingEvent event = (ILoggingEvent) o;
    Message message = new Message(event.getMessage(),
        DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
        event.getThreadName(), event.getLoggerName(), event.getLevel().levelStr);
    LoggerQueue.getInstance().push(message);
    return FilterReply.ACCEPT;
  }
}
