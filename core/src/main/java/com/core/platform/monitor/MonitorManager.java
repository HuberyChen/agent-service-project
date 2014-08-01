package com.core.platform.monitor;

import com.core.platform.ClassUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorManager implements ApplicationContextAware {
    private final Map<String, Object> monitorBeans = new HashMap<>();

    public Object monitorBean(String name) {
        return monitorBeans.get(name);
    }

    public List<String> monitorBeanNames() {
        return new ArrayList<>(monitorBeans.keySet());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        List<Object> monitorBeans = findMonitorBeans(applicationContext);

        for (Object monitorBean : monitorBeans) {
            Monitor monitor = ClassUtils.getOriginalClass(monitorBean.getClass()).getAnnotation(Monitor.class);

            if (monitor != null) {
                this.monitorBeans.put(monitor.value(), monitorBean);
            }
        }
    }

    List<Object> findMonitorBeans(ApplicationContext applicationContext) {
        return new ArrayList<>(applicationContext.getBeansWithAnnotation(Monitor.class).values());
    }
}
