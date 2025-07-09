package com.fps.service.ipml;

import com.fps.dao_jw.HistoryDao;
import com.fps.pojo.Flow;
import com.fps.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryDao historyDao;

    @Override
    public List<Flow> findAll(String station_name, Integer mode) {
        List<Flow> flowList = historyDao.findAll(station_name);
        if (mode == null || mode == 0) {
            return flowList;
        }

        int intervalMinutes = mode == 1 ? 30 : 60;
        return filterFlowsByInterval(flowList, intervalMinutes);
    }

    private List<Flow> filterFlowsByInterval(List<Flow> originalList, int intervalMinutes) {
        if (originalList.isEmpty()) {
            return originalList;
        }

        List<Flow> filteredList = new ArrayList<>();
        LocalDateTime lastTime = originalList.get(originalList.size() - 1).getTime();

        for (int i = originalList.size() - 1; i >= 0; i--) {
            Flow currentFlow = originalList.get(i);
            LocalDateTime currentTime = currentFlow.getTime();

            long minutesDiff = java.time.Duration.between(currentTime, lastTime).toMinutes();

            if (minutesDiff % intervalMinutes == 0) {
                filteredList.add(0, currentFlow);
                lastTime = currentTime;
            }
        }

        return filteredList;
    }
}
