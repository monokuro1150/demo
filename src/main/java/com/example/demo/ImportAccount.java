package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * インポートアカウント用の中間テーブルエンティティ
 * アップロードされたCSVファイルの情報と、インポート対象のアカウント情報を管理
 */
@Entity
@Table(name = "import_account")
public class ImportAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ファイル名
    @Column(name = "file_name", nullable = false)
    private String fileName;

    // アップロード日時
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    // ステータス（PENDING: 待機中, COMPLETED: 完了）
    @Column(name = "status", nullable = false)
    private String status; // "PENDING" or "COMPLETED"

    // アカウントID
    @Column(name = "account_id", nullable = false)
    private String accountId;

    // 権限レベル（1-5）
    @Column(name = "permission_level")
    private Integer permissionLevel;

    // =========================
    // コンストラクタ
    // =========================

    public ImportAccount() {
    }

    public ImportAccount(String fileName, LocalDateTime uploadDate, String status, 
                        String accountId, Integer permissionLevel) {
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.status = status;
        this.accountId = accountId;
        this.permissionLevel = permissionLevel;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}
