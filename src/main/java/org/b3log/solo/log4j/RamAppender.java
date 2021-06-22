/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.solo.log4j;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.b3log.solo.bolo.tool.FixSizeLinkedList;
import org.b3log.solo.improve.LogHelperExecutor;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <h3>bolo-solo</h3>
 * <p>追加到内存的 Appender，用于在线调试后台日志</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-04-04
 **/
public class RamAppender extends AppenderSkeleton {
    // 定长列表
    public static FixSizeLinkedList<Map<String, Object>> list = new FixSizeLinkedList<>(100);
    public static long id = 0;

    public static FixSizeLinkedList<Map<String, Object>> getList() {
        return list;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        final Map<String, Object> map = new HashMap<>();
        map.put("name", loggingEvent.getLoggerName());
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(new Date(loggingEvent.getTimeStamp())));
        map.put("level", loggingEvent.getLevel().toString());
        map.put("message", StringEscapeUtils.escapeHtml(loggingEvent.getMessage().toString()));
        map.put("methodName", loggingEvent.getLocationInformation().getMethodName());
        map.put("lineNumber", loggingEvent.getLocationInformation().getLineNumber());

        map.put("id", id);
        if (id == Long.MAX_VALUE) {
            id = 0;
        }
        ++id;

        map.put("throwable", null);
        if (loggingEvent.getThrowableInformation() != null && loggingEvent.getThrowableInformation().getThrowable() != null) {
            Throwable t = loggingEvent.getThrowableInformation().getThrowable();
            Map<String, Object> throwableMap = new HashMap<>();
            throwableMap.put("message", t.getMessage());
            throwableMap.put("class", t.getClass().getName());
            throwableMap.put("stackTrace", t.getStackTrace());
            map.put("throwable", throwableMap);
        }

        list.add(map);

        // 收集最近5条错误报告
        if (loggingEvent.getLevel().toString().equals("ERROR") || loggingEvent.getLevel().toString().equals("WARN")) {
            List<Map<String, Object>> logs = new ArrayList<>();
            int start = list.size() - 1;
            int stop = start - 4;
            if (stop < 0) {
                stop = 0;
            }
            for (int i = start; i >= stop; i--) {
                try {
                    logs.add(list.get(i));
                } catch (Exception ignored) {
                }
            }
            LogHelperExecutor.submit(logs);
        }
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
