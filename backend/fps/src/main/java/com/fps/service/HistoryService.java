package com.fps.service;

import com.fps.pojo.Flow;

import java.util.List;

public interface HistoryService {
    public List<Flow> findAll(String station_name, Integer mode);
}
