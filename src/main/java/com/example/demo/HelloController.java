package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/*
 * =========================
 * 画面と処理をつなぐクラス（Controller）
 * =========================
 *
 * ・URLにアクセスされたときの処理を書く
 * ・画面（HTML）にデータを渡す
 * ・Repositoryを呼んでDB操作する
 */
@Controller
public class HelloController {

    // DB操作用（Repository）
    private final ApplicationRepository applicationRepository;

    // コンストラクタ（Springが自動で入れてくれる）
    public HelloController(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /*
     * =========================
     * トップページ表示
     * =========================
     */
    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html を表示
    }

    /*
     * =========================
     * 申込フォーム表示
     * =========================
     */
    @GetMapping("/apply")
    public String applyForm(Model model) {

        // 空のフォームを作ってHTMLに渡す
        model.addAttribute("applyForm", new ApplyForm());

        return "apply"; // templates/apply.html
    }

    /*
     * =========================
     * フォーム送信（登録処理）
     * =========================
     */
    @PostMapping("/apply")
    public String applySubmit(
            @Valid @ModelAttribute ApplyForm applyForm, // 入力値を受け取る + バリデーション
            BindingResult bindingResult,               // エラー情報
            Model model) {

        // 入力エラーがある場合
        if (bindingResult.hasErrors()) {
            return "apply"; // フォーム画面に戻る
        }

        // DBに保存
        applicationRepository.insert(applyForm);

        // 完了画面にデータを渡す
        model.addAttribute("companyName", applyForm.getCompanyName());
        model.addAttribute("contactName", applyForm.getContactName());
        model.addAttribute("email", applyForm.getEmail());
        model.addAttribute("planName", applyForm.getPlanName());

        return "complete"; // templates/complete.html
    }

    /*
     * =========================
     * 完了画面（直接アクセス用）
     * =========================
     */
    @GetMapping("/complete")
    public String complete() {
        return "complete";
    }

    /*
     * =========================
     * 一覧表示
     * =========================
     */
    @GetMapping("/applications")
    public String applications(Model model) {

        // DBから全データ取得
        model.addAttribute("applicationList", applicationRepository.findAll());

        return "applications"; // templates/applications.html
    }

    /*
     * =========================
     * 削除処理
     * =========================
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        // idを使って削除
        applicationRepository.delete(id);

        // 一覧画面にリダイレクト（再読み込み）
        return "redirect:/applications";
    }
}