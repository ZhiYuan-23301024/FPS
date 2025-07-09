package com.fps.dao_jw.ipml;

import cn.hutool.core.io.IoUtil;
import com.fps.dao_jw.HistoryDao;
import com.fps.pojo.Flow;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HistoryDaoImpl implements HistoryDao {
    @Override
    public List<Flow> findAll(String station_name) {
        String URL = "jdbc:mysql://localhost:3306/term";
        String username = "root";
        String password = "123456";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Flow> flowList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, username, password);

            if (!station_name.matches("[a-zA-Z0-9_]+")) {
                throw new IllegalArgumentException("Invalid table name");
            }

            String sql = "SELECT passengerFlow, theoryTime FROM " + station_name;

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                Flow flow = new Flow(
                        rs.getInt("passengerFlow"),
                        rs.getTimestamp("theoryTime").toLocalDateTime()
                );
                flowList.add(flow);
            }
            return flowList;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException("Database error", se);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error", e);
        } finally {
            try {
                if (rs != null) {rs.close();}
                if (ps != null) {ps.close();}
                if (conn != null) {conn.close();}
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
