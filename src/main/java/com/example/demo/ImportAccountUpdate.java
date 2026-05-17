package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * インポートアカウント更新用の中間テーブルエンティティ
 * アップロードされたCSVファイルの処理状況を管理
 */
@Entity
@Table(name = "import_account_update")
public class ImportAccountUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ファイル名
    @Column(name = "file_name", nullable = false)
    private String fileName;

    // アップロード日時
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    // ステータス（PENDING: 待機中, EXECUTED: 実行済み, CANCELLED: キャンセル済み）
    @Column(name = "status", nullable = false)
    private String status; // "PENDING", "EXECUTED", "CANCELLED"

    // レコード数
    @Column(name = "count")
    private Integer count;

    // =========================
    // コンストラクタ
    // =========================

    public ImportAccountUpdate() {
    }

    public ImportAccountUpdate(String fileName, LocalDateTime uploadDate, String status, Integer count) {
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.status = status;
        this.count = count;
    }

    // =========================
    // Getter / Setter
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
