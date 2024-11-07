package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return List.of();
    }

    @Override
    public Schedule findScheduleById(long id) {
        return null;
    }

    @Override
    public void deleteSchedule(long id) {

    }
}
