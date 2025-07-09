package com.fps.dao_jw;

import com.fps.pojo.Flow;

import java.util.List;

public interface HistoryDao {
    public List<Flow> findAll(String station_name);
}
