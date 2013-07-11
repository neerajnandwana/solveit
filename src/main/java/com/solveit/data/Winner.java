package com.solveit.data;


import com.google.common.base.Splitter;

import java.util.Iterator;

public class Winner {
    private final String ip;
    private final String name;
    private final String timeStamp;

    private Winner(String timeStamp, String ip, String name) {
        this.timeStamp = timeStamp;
        this.ip = ip;
        this.name = name;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Winner winner = (Winner) o;

        if (!name.equals(winner.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Winner{" +
                "ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }

    public String toCsv() {
        return String.format("%s,%s,%s\n", timeStamp, ip, name);
    }

    public static class Builder{
        public static Winner csvBuild(String csValue){
            Iterator<String> iterator = Splitter.on(',').omitEmptyStrings().trimResults().split(csValue).iterator();
            String timeStamp = iterator.next();
            String ip = iterator.next();
            String name = iterator.next();
            return build(timeStamp, ip, name);
        }

        public static Winner build(String timeStamp, String ip, String name){
            return new Winner(timeStamp, ip, name);
        }
    }
}
