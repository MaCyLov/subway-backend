package com.subway.service;

import com.subway.model.Line;
import com.subway.model.PathResult;
import com.subway.model.Station;

import java.time.LocalDateTime;
import java.util.List;

public interface SubwayService {
    void loadData();
    void addLine(Line line);
    void deleteLine(String lineId);
    PathResult findShortestTimePath(String start, String end, String dayType, LocalDateTime currentTime);
    PathResult findLeastTransferPath(String start, String end, String dayType, LocalDateTime currentTime);
    List<Line> getAllLines();
    List<Station> getAllStations();
}