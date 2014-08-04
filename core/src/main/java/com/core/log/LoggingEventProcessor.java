package com.core.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.core.utils.CharacterEncodings;
import com.core.utils.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoggingEventProcessor {
    private static final int MAX_HOLD_SIZE = 5000;
    private final PatternLayout layout;
    private final String logFolder;
    private final Queue<ILoggingEvent> events = new ConcurrentLinkedQueue<>();
    private final AtomicInteger eventSize = new AtomicInteger(0);
    private final Lock lock = new ReentrantLock();
    private volatile boolean hold = true;
    private volatile Writer writer;
    private volatile EmailErrorNotifier emailErrorNotifier;

    public LoggingEventProcessor(PatternLayout layout, String logFolder) {
        this.layout = layout;
        this.logFolder = logFolder;
    }

    public void process(ILoggingEvent event) throws IOException {
        if (hold) {
            addEvent(event);
            if (flushLog(event)) {
                createEmailNotifierIfNeeded(event.getLevel());
                flushTraceLogs();
                hold = false;
            }
        } else {
            write(event);
        }
    }

    private void createEmailNotifierIfNeeded(Level triggerLevel) {
        if (triggerLevel.isGreaterOrEqual(Level.ERROR) && LogSettings.get().getErrorEmailSender() != null) {
            emailErrorNotifier = new EmailErrorNotifier();
        }
    }

    private void addEvent(ILoggingEvent event) {
        event.getThreadName(); // force "logback" to remember current thread
        events.add(event);
        eventSize.getAndIncrement();
    }

    private boolean flushLog(ILoggingEvent event) {
        return event.getLevel().isGreaterOrEqual(Level.WARN) || eventSize.get() > MAX_HOLD_SIZE;
    }

    private void flushTraceLogs() throws IOException {
        try {
            lock.lock(); // lock on flush to make sure multiple threads come in
            // this method to maintain order
            if (writer == null)
                writer = createWriter();

            while (true) {
                ILoggingEvent event = events.poll();
                if (event == null) {
                    if (emailErrorNotifier != null)
                        emailErrorNotifier.send();
                    return;
                }
                write(event);
                if (emailErrorNotifier != null)
                    emailErrorNotifier.addEvent(layout.doLayout(event));
            }
        } finally {
            lock.unlock();
        }
    }

    void write(ILoggingEvent event) throws IOException {
        String log = layout.doLayout(event);
        writer.write(log);
    }

    private Writer createWriter() throws FileNotFoundException {
        if (logFolder == null)
            return new BufferedWriter(new OutputStreamWriter(System.err, CharacterEncodings.CHARSET_UTF_8));
        String logFilePath = generateLogFilePath(getAction(), new Date(), getRequestId());
        ActionLoggerImpl.get().currentActionLog().setTraceLogPath(logFilePath);
        File logFile = new File(logFilePath);
        createParentFolder(logFile);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), CharacterEncodings.CHARSET_UTF_8));
    }

    private void createParentFolder(File logFile) {
        File folder = logFile.getParentFile();
        folder.mkdirs();
    }

    void cleanup(boolean forceFlushTraceLog) throws IOException {
        if (forceFlushTraceLog) {
            flushTraceLogs();
        }
        if (logFolder == null) {
            IOUtils.flush(writer); // do not close System.err (when logFolder is
            // null)
        } else {
            IOUtils.close(writer);
        }
    }

    private String getRequestId() {
        String requestId = MDC.get(LogConstants.MDC_REQUEST_ID);
        if (requestId == null) {
            requestId = "unknown";
        }
        return requestId;
    }

    private String getAction() {
        String action = MDC.get(LogConstants.MDC_ACTION);
        if (action == null) {
            action = "unknown";
        }
        return action;
    }

    String generateLogFilePath(String action, Date targetDate, String requestId) {
        String sequence = RandomStringUtils.randomAlphanumeric(5);

        return String.format("%1$s/%2$tY/%2$tm/%2$td/%3$s/%2$tH%2$tM.%4$s.%5$s.log", logFolder, targetDate, action, requestId, sequence);
    }
}
