package com.core.platform.monitor;

import com.core.xml.XMLBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Status {

    Map<String, ServiceDetail> serviceDetails = new HashMap<String, ServiceDetail>();

    public void check(List<ServiceMonitor> monitors) {
        for (ServiceMonitor monitor : monitors) {
            check(monitor);
        }
    }

    private void check(ServiceMonitor monitor) {

        ServiceDetail detail = new ServiceDetail();
        try {
            ServiceStatus status = monitor.getServiceStatus();
            detail.setStatus(status);
        } catch (Exception e) {
            detail.setErrorMessage(e.getClass().getName() + " " + e.getMessage());
            detail.setStatus(ServiceStatus.DOWN);
        }

        serviceDetails.put(monitor.getServiceName(), detail);
    }

    public String toXML(ServerStatus serverStatus) {
        XMLBuilder builder = XMLBuilder.indentedXMLBuilder();
        builder.startElement("status");
        builder.textElement("server", serverStatus.status().name());

        builder.startElement("services");
        for (Map.Entry<String, ServiceDetail> entry : serviceDetails.entrySet()) {
            builder.startElement("service");
            builder.attribute("name", entry.getKey());
            ServiceDetail detail = entry.getValue();
            builder.textElement("status", detail.getStatus().name());
            builder.textElement("elapsedTime", String.valueOf(detail.getElapsedTime()));
            builder.textElement("errorMessage", detail.getErrorMessage());
            builder.endElement();
        }
        builder.endElement();   // end of services

        builder.endElement();   // end of status

        return builder.toXML();
    }
}
