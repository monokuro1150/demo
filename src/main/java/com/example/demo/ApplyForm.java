package com.example.demo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
 * =========================
 * フォーム入力用クラス（Form）
 * =========================
 *
 * ・画面（HTML）から送られてきた入力値を受け取る
 * ・バリデーション（入力チェック）を行う
 *
 * Controllerで受け取るときに使われる
 */
public class ApplyForm {

    /*
     * =========================
     * 入力項目 + バリデーション
     * =========================
     */

    // 会社名（未入力NG）
    @NotBlank(message = "会社名は必須です")
    private String companyName;

    // 担当者名（未入力NG）
    @NotBlank(message = "担当者名は必須です")
    private String contactName;

    // メールアドレス
    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレス形式で入力してください") // 形式チェック
    private String email;

    // プラン名（未選択NG）
    @NotBlank(message = "プラン名は必須です")
    private String planName;

    /*
     * =========================
     * getter（値を取得）
     * =========================
     */

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

    /*
     * =========================
     * setter（値をセット）
     * =========================
     */

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