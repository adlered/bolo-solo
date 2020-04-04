package org.b3log.solo.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.b3log.solo.bolo.tool.FixSizeLinkedList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * <h3>bolo-solo</h3>
 * <p>追加到内存的 Appender，用于在线调试后台日志</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-04-04
 **/
public class RamAppender extends AppenderSkeleton {
    // 定长列表
    public static FixSizeLinkedList<Map<String,Object>> list = new FixSizeLinkedList<>(16);

    public static FixSizeLinkedList<Map<String, Object>> getList() {
        return list;
    }

    public static long id = 0;

    @Override
    protected void append(LoggingEvent loggingEvent) {
        final Map<String,Object> map = new HashMap<>();
        map.put("name", loggingEvent.getLoggerName());
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:ss:SS").format(new Date(loggingEvent.getTimeStamp())));
        map.put("level", loggingEvent.getLevel().toString());
        map.put("message", "" + loggingEvent.getMessage());
        map.put("methodName", loggingEvent.getLocationInformation().getMethodName());
        map.put("lineNumber", loggingEvent.getLocationInformation().getLineNumber());
        map.put("freeMemory", Runtime.getRuntime().freeMemory());

        map.put("id", id);
        if (id == Long.MAX_VALUE) {
            id = 0;
        }
        ++id;

        map.put("throwable", null);
        if(loggingEvent.getThrowableInformation()!=null && loggingEvent.getThrowableInformation().getThrowable()!=null){
            Throwable t = loggingEvent.getThrowableInformation().getThrowable();
            Map<String, Object> throwableMap = new HashMap<>();
            throwableMap.put("message", t.getMessage());
            throwableMap.put("class", t.getClass().getName());
            throwableMap.put("stackTrace", t.getStackTrace());
            map.put("throwable", throwableMap);
        }

        list.add(map);
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
