package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", schedule.getName());
        parameters.put("password", schedule.getPassword());
        parameters.put("title", schedule.getTitle());
        parameters.put("content", schedule.getContent());
        parameters.put("created_at", schedule.getCreated_at());
        parameters.put("modified_at", schedule.getModified_at());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new ScheduleResponseDto(
                key.longValue(), schedule.getName(), schedule.getTitle(),
                schedule.getContent(), schedule.getCreated_at(), schedule.getModified_at()
        );
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules(String name, String modified_at) {
        return jdbcTemplate.query("select * from schedule where name = ? || modified_at = ?", scheduleRowMapper());
    }

    @Override
    public Optional<Schedule> findScheduleById(long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateSchedule(Long id, String name, long password, String title, String content, String modified_at) {
        return jdbcTemplate.update(
                "update schedule set name = ?, title = ?, content = ?,modified_at = ? where id = ?",
                name, title, content, modified_at, id);
    }

    @Override
    public int deleteSchedule(long id, long password) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {

        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("created_at"),
                        rs.getString("modified_at")
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("password"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("created_at"),
                        rs.getString("modified_at")
                );
            }
        };
    }
}
