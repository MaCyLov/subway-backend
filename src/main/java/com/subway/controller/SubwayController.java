// SubwayController.java
package com.subway.controller;

import com.subway.dto.LineEditDTO;
import com.subway.model.Line;
import com.subway.model.PathResult;
import com.subway.model.Station;
import com.subway.service.SubwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subway")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SubwayController {

    private final SubwayService subwayService;

    @Autowired
    public SubwayController(SubwayService subwayService) {
        this.subwayService = subwayService;
    }

    @GetMapping("/lines")
    public ResponseEntity<List<Line>> getLines() {
        List<Line> lines = subwayService.getAllLines();
        return ResponseEntity.ok(lines);
    }

    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getStations() {
        List<Station> stations = subwayService.getAllStations();
        return ResponseEntity.ok(stations);
    }

    // ---------- 新增线路编辑接口 ----------

    // POST：添加线路
    @PostMapping("/line")
    public ResponseEntity<?> addLine(@RequestBody LineEditDTO lineDto) {
        // 将 DTO 转换为内部模型对象 Line
        try {
            if(lineDto.getLineId() == null || lineDto.getLineId().trim().isEmpty()){
                throw new RuntimeException("线路编号不能为空");
            }
            // 判断站点与站间距离数量
            List<String> stations = lineDto.getStations();
            List<Double> distances = lineDto.getDistances();
            if(stations == null || stations.size() < 2){
                throw new RuntimeException("至少需要两个站点");
            }
            if(distances == null || distances.size() != (stations.size() - 1)){
                throw new RuntimeException("站间距离数量必须为站点数量-1");
            }
            // 构造 Line 对象
            Line line = new Line();
            line.setLineId(lineDto.getLineId());
            line.setSpeed(lineDto.getSpeed()); // 前端可传入也可使用默认
            // 将站点名称转换为 Station 对象（仅保存站点名称，不做太多数据关联，此处仅用于线路编辑和后续计算）
            List<Station> stationList = new java.util.ArrayList<>();
            for (String stationName : stations) {
                Station st = new Station();
                st.setName(stationName);
                stationList.add(st);
            }
            line.setStations(stationList);
            line.setDistances(distances);
            subwayService.addLine(line);
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "线路添加成功");
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResp = new HashMap<>();
            errorResp.put("error", "线路添加失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(errorResp);
        }
    }

    // DELETE：删除指定线路
    @DeleteMapping("/line/{lineId}")
    public ResponseEntity<?> deleteLine(@PathVariable String lineId) {
        try {
            subwayService.deleteLine(lineId);
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "线路删除成功");
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResp = new HashMap<>();
            errorResp.put("error", "线路删除失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(errorResp);
        }
    }

    // ---------- 乘车路径查询接口 ----------

    @GetMapping("/path/shortest")
    public ResponseEntity<?> getShortestPath(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false, defaultValue = "weekday") String dayType,
            @RequestParam(required = false) String queryTime
    ) {
        System.out.println("Shortest Path Request - Start: " + start + ", End: " + end +
                ", DayType: " + dayType + ", QueryTime: " + queryTime);
        if (start.trim().isEmpty() || end.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "起始站和终点站不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        LocalDateTime time;
        try {
            if (queryTime != null && !queryTime.trim().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime lt = LocalTime.parse(queryTime, formatter);
                // 以当前日期为基础
                time = LocalDateTime.now().withHour(lt.getHour()).withMinute(lt.getMinute()).withSecond(0).withNano(0);
            } else {
                time = LocalDateTime.now();
            }
            System.out.println("查询时间转换后：" + time);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "查询时间格式错误，应为 HH:mm");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        PathResult result = subwayService.findShortestTimePath(start, end, dayType, time);
        if (result == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "无法找到路径：站点不存在或无有效路线");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/path/least-transfer")
    public ResponseEntity<?> getLeastTransferPath(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false, defaultValue = "weekday") String dayType,
            @RequestParam(required = false) String queryTime
    ) {
        System.out.println("Least Transfer Path Request - Start: " + start + ", End: " + end + ", DayType: " + dayType + ", QueryTime: " + queryTime);
        if (start.trim().isEmpty() || end.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "起始站和终点站不能为空");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        LocalDateTime time;
        try {
            if (queryTime != null && !queryTime.trim().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime lt = LocalTime.parse(queryTime, formatter);
                time = LocalDateTime.now().withHour(lt.getHour()).withMinute(lt.getMinute()).withSecond(0).withNano(0);
            } else {
                time = LocalDateTime.now();
            }
            System.out.println("查询时间转换后：" + time);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "查询时间格式错误，应为 HH:mm");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        PathResult result = subwayService.findLeastTransferPath(start, end, dayType, time);
        if (result == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "无法找到路径：站点不存在或无有效路线");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return ResponseEntity.ok(result);
    }
}