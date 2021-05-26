package ua.com.alevel.entity;

import ua.com.alevel.annotation.PropertyKey;


public class AppProperties {
    @PropertyKey("application.version")
    public String applicationVersion;

    @PropertyKey("application.name")
    public String applicationName;

    @PropertyKey("connections.limit")
    public int maxConnection;

    @PropertyKey("application.state")
    public State state;

    @PropertyKey("HTTP2Enabled")
    public boolean isHTTP2Enabled;

    @Override
    public String toString() {
        return "AppProperties{" +
                "applicationVersion='" + applicationVersion + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", maxConnection=" + maxConnection +
                ", state=" + state +
                ", isHTTP2Enabled=" + isHTTP2Enabled +
                '}';
    }
}
