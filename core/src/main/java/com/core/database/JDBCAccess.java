package com.core.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCAccess {
    private JdbcTemplate jdbcTemplate;

    public <T> List<T> find(String sql, RowMapper<T> rowMapper, Object... params) {
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    public <T> T findUniqueResult(String sql, RowMapper<T> rowMapper, Object... params) {
        return jdbcTemplate.queryForObject(sql, params, rowMapper);
    }

    public Integer findInteger(String sql, Object... params) {
        return jdbcTemplate.queryForInt(sql, params);
    }

    public String findString(String sql, Object... params) {
        return jdbcTemplate.queryForObject(sql, params, new SimpleRowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getString(1);
            }
        });
    }

    public int execute(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    public int[] batchExecute(String sql, List<Object[]> params) {
        int totalUpdatedRows = 0;
        int[] results = jdbcTemplate.batchUpdate(sql, params);
        for (int updatedRows : results) {
            totalUpdatedRows += updatedRows;
        }
        return results;
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
