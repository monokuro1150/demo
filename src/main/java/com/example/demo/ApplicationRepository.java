package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * =========================
 * DB操作を担当するクラス（Repository）
 * =========================
 *
 * ・データの保存（INSERT）
 * ・データの取得（SELECT）
 * ・データの削除（DELETE）
 *
 * → Controllerから呼ばれて、DBとやり取りする役割
 */
@Repository
public class ApplicationRepository {

    // DB操作を行うためのクラス
    private final JdbcTemplate jdbcTemplate;

    // コンストラクタ（Springが自動でJdbcTemplateを入れてくれる）
    public ApplicationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * =========================
     * DBのデータ → Javaオブジェクトに変換
     * =========================
     *
     * ResultSet（DBの結果）を
     * ApplicationDataに変換する処理
     */
    private final RowMapper<ApplicationData> rowMapper = (rs, rowNum) -> {
        ApplicationData data = new ApplicationData();

        // DBのカラム名 → Javaのフィールドにセット
        data.setId(rs.getLong("id"));
        data.setCompanyName(rs.getString("company_name"));
        data.setContactName(rs.getString("contact_name"));
        data.setEmail(rs.getString("email"));
        data.setPlanName(rs.getString("plan_name"));

        return data;
    };

    /*
     * =========================
     * データ登録（INSERT）
     * =========================
     *
     * フォームで入力された内容をDBに保存する
     */
    public void insert(ApplyForm form) {

        // SQL文（データを追加）
        String sql = """
            INSERT INTO applications (company_name, contact_name, email, plan_name)
            VALUES (?, ?, ?, ?)
            """;

        // ? に値を入れて実行
        jdbcTemplate.update(
                sql,
                form.getCompanyName(),
                form.getContactName(),
                form.getEmail(),
                form.getPlanName()
        );
    }

    /*
     * =========================
     * 全件取得（SELECT）
     * =========================
     *
     * DBから全データを取得して
     * ApplicationDataのリストで返す
     */
    public List<ApplicationData> findAll() {

        // 新しい順に並べる（id DESC）
        String sql = "SELECT id, company_name, contact_name, email, plan_name FROM applications ORDER BY id DESC";

        // SQL実行 → rowMapperで変換 → Listで返す
        return jdbcTemplate.query(sql, rowMapper);
    }

    /*
     * =========================
     * 削除（DELETE）
     * =========================
     *
     * 指定されたidのデータを削除する
     */
    public void delete(Long id) {

        String sql = "DELETE FROM applications WHERE id = ?";

        // ? に id を入れて削除
        jdbcTemplate.update(sql, id);
    }
}