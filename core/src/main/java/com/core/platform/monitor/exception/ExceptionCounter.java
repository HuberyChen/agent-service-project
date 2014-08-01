package com.core.platform.monitor.exception;

import com.core.utils.DateRangeUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExceptionCounter {
    @XmlElement(name = "exception-class")
    private String exceptionClass;

    @XmlElement(name = "last-date")
    private Date lastDate;

    @XmlElement(name = "count")
    private int count;

    public void increase() {
        count++;
        lastDate = new Date();
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(final Date lastDate) {
        this.lastDate = lastDate;
    }

    @XmlElement(name = "error-occurred-in-last-5-mins")
    public boolean isErrorOccurredInLastFiveMins() {
        return DateRangeUtils.minutesBetween(lastDate, new Date()) > 5 ? false : true;
    }

}
