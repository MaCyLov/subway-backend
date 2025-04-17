package com.subway.model;

import lombok.Data;

@Data
public class PathSegment {
    private String lineId;
    private String direction;
    private String startStation;
    private String endStation;
    private int time; // 该段耗时
}