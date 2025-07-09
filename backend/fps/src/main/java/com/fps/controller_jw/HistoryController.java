package com.fps.controller_jw;

import com.fps.pojo.Flow;
import com.fps.pojo.FlowData;
import com.fps.pojo.Result;
import com.fps.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/api/flows")
    public Result<FlowData> list(@RequestParam("station_name") String station_name,
                                 @RequestParam("mode") Integer mode) throws Exception {


        System.out.println("Received station_name: " + station_name);
        System.out.println("Received mode: " + mode);
        List<Flow> flowList = historyService.findAll(station_name, mode);
        // 包装数据
        FlowData flowData = new FlowData();
        flowData.setFlows(flowList);

        // 返回统一格式结果
        return Result.success(flowData);
    }
}

