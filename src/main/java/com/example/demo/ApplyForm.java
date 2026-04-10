package com.example.demo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ApplyForm {

    @NotBlank(message = "会社名は必須です")
    private String companyName;

    @NotBlank(message = "担当者名は必須です")
    private String contactName;

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレス形式で入力してください")
    private String email;

    @NotBlank(message = "プラン名は必須です")
    private String planName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}