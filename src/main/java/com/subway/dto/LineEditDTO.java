// LineEditDTO.java
package com.subway.dto;

import java.util.List;

public class LineEditDTO {
    private String lineId;
    private List<String> stations;
    private List<Double> distances;
    private double speed; // 可选，如果前端不传，则后端可用默认值

    public String getLineId() {
        return lineId;
    }
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
    public List<String> getStations() {
        return stations;
    }
    public void setStations(List<String> stations) {
        this.stations = stations;
    }
    public List<Double> getDistances() {
        return distances;
    }
    public void setDistances(List<Double> distances) {
        this.distances = distances;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}