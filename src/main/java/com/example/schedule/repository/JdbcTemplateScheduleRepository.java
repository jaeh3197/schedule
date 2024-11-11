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
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    //Jdbc 호출
    private final JdbcTemplate jdbcTemplate;

    //생성자 주입
    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //일정 저장 하고 반환
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

    //일정 전체 조회
    @Override
    public List<ScheduleResponseDto> getAllSchedules(String name, String modified_at) {
        return jdbcTemplate.query("select * from schedule where name = ? or modified_at = date(?)", scheduleRowMapper(), name, modified_at);
    }

    //일정 단건 조회
    @Override
    public Optional<Schedule> findScheduleById(long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperWithPassword(), id);
        return result.stream().findAny();
    }

    //일정이 없을 경우 예외 처리
    @Override
    public Schedule findScheduleByIdOrElseThrow(long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperWithPassword(), id);
        return result.stream()
                .findAny()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id)
                );
    }

    //일정 수정
    @Override
    public int updateSchedule(Long id, String name, long password, String title, String content, String modified_at) {
        return jdbcTemplate.update(
                "update schedule set name = ?, title = ?, content = ?,modified_at = ? where id = ?",
                name, title, content, modified_at, id);
    }

    //일정 삭제
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

    private RowMapper<Schedule> scheduleRowMapperWithPassword() {
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
