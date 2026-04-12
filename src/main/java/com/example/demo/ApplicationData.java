package com.example.demo;

/*
 * =========================
 * 申込データを表すクラス（モデル）
 * =========================
 *
 * DBから取得したデータや、
 * 画面に表示するデータをまとめるためのクラス
 *
 * 1レコード（1件分の申込情報）を表す
 */
public class ApplicationData {

    // =========================
    // フィールド（データ本体）
    // =========================

    private Long id;              // 主キー（データの識別用）
    private String companyName;  // 会社名
    private String contactName;  // 担当者名
    private String email;        // メールアドレス
    private String planName;     // プラン名

    // =========================
    // getter（値を取得する）
    // =========================

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    public String getPlanName() {
        return planName;
    }

    // =========================
    // setter（値をセットする）
    // =========================

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}