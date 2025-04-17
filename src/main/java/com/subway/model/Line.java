package com.subway.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Line {
    private String lineId;
    private List<Station> stations = new ArrayList<>();
    private List<String> directions = new ArrayList<>();
    private double speed;
    private List<Double> distances = new ArrayList<>(); // 添加距离列表
    private Map<String, Map<String, Map<String, Map<String, String>>>> timetable;
}