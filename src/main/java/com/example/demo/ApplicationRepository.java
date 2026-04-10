package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ApplicationData> rowMapper = (rs, rowNum) -> {
        ApplicationData data = new ApplicationData();
        data.setId(rs.getLong("id"));
        data.setCompanyName(rs.getString("company_name"));
        data.setContactName(rs.getString("contact_name"));
        data.setEmail(rs.getString("email"));
        data.setPlanName(rs.getString("plan_name"));
        return data;
    };

    public void insert(ApplyForm form) {
        String sql = """
            INSERT INTO applications (company_name, contact_name, email, plan_name)
            VALUES (?, ?, ?, ?)
            """;
        jdbcTemplate.update(
                sql,
                form.getCompanyName(),
                form.getContactName(),
                form.getEmail(),
                form.getPlanName()
        );
    }

    public List<ApplicationData> findAll() {
        String sql = "SELECT id, company_name, contact_name, email, plan_name FROM applications ORDER BY id DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(Long id) {
    String sql = "DELETE FROM applications WHERE id = ?";
    jdbcTemplate.update(sql, id);
}
}
