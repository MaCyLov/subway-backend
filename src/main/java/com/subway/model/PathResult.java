package com.subway.model;

import lombok.Data;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PathResult {
    private List<PathSegment> segments = new ArrayList<>();
    private double totalDistance;
    private int totalTime; // 单位：分钟
    private int fare;      // 票价（元）
    private int transferCount;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
}