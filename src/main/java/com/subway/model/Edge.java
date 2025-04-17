package com.subway.model;

import lombok.Data;

@Data
public class Edge {
    private Station from;           // 起始站点
    private Station to;             // 目的站点
    private String lineId;          // 所属线路ID
    private double distance;        // 站点间距离（公里）
    private double travelTime;      // 行驶时间（分钟）
    private int transferTime;       // 换乘时间（分钟）
    private boolean isTransfer;     // 是否为换乘边

    // 确保 getter 和 setter 正确（@Data 通常会自动生成）
    public double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }

    public int getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(int transferTime) {
        this.transferTime = transferTime;
    }

    public boolean isTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(boolean isTransfer) {
        this.isTransfer = isTransfer;
    }
}