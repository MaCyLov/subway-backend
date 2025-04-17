package com.subway.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Station {
    private String name;                // 站点名称
    private List<String> lineIds;       // 所属线路ID列表
    private boolean isTransfer;         // 是否为换乘站

    public Station() {
        this.lineIds = new ArrayList<>();
    }

    // 确保 getter 和 setter 正确（@Data 通常会自动生成）
    public List<String> getLineIds() {
        return lineIds;
    }

    public void setLineIds(List<String> lineIds) {
        this.lineIds = lineIds;
    }

    public boolean isTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(boolean isTransfer) {
        this.isTransfer = isTransfer;
    }
}