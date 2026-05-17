package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * アカウント更新画面のコントローラー
 * 権限の一括更新機能を提供
 */
@Controller
@RequestMapping("/account")
public class AccountUpdateController {

    @Autowired
    private ImportAccountService importAccountService;

    @Autowired
    private ImportAccountUpdateRepository importAccountUpdateRepository;

    // ================================
    // 画面初期表示
    // ================================

    /**
     * アカウント更新画面を表示
     * 
     * - ログインユーザーの権限取得
     * - アップロード可能か判定
     * - 最新のcsv取込履歴（DB）取得
     */
    @GetMapping("/update")
    public String accountUpdateForm(Model model) {
        // 1. ログインユーザー情報を取得（ここでは仮のデータを使用）
        String loginUserName = "sample_user"; // 実際はセキュリティコンテキストから取得
        int userPermissionLevel = 4; // 権限レベル

        // 2. アップロード可能か判定
        // 権限レベル3以上でアップロード可能とする（例）
        boolean canUpload = userPermissionLevel >= 3;

        // 3. 最新のcsv取込履歴を取得（最新8件）
        var importHistory = importAccountService.getLatestHistory(8);

        // モデルに設定
        model.addAttribute("loginUserName", loginUserName);
        model.addAttribute("usePermissionLevel", userPermissionLevel);
        model.addAttribute("canUpload", canUpload);
        model.addAttribute("importHistory", importHistory);

        return "account-update"; // templates/account-update.html を表示
    }

    // ================================
    // CSVアップロード処理
    // ================================

    /**
     * CSVファイルをアップロード
     * 
     * - フォーマットチェック
     * - エラー時：トランザクション：ロールバック
     * - 正常時：インポートアカウント中間テーブルに登録
     */
    @PostMapping("/import/upload")
    @ResponseBody
    public Map<String, Object> uploadCSV(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            // サービスレイヤーの呼び出し
            ImportAccountService.Result result = importAccountService.uploadCSV(file);

            response.put("success", result.isSuccess());
            response.put("message", result.getMessage());

            return response;

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "エラーが発生しました: " + e.getMessage());
            return response;
        }
    }

    // ================================
    // キャンセル処理
    // ================================

    /**
     * ファイルのアップロードをキャンセル
     * 
     * - インポートアカウント更新中間テーブルのデータを削除
     * - インポートアカウント中間テーブルのデータを削除
     * - トランザクション：コミット
     * - 画面再表示
     */
    @PostMapping("/import/cancel")
    @ResponseBody
    public Map<String, Object> cancelUpload() {
        Map<String, Object> response = new HashMap<>();

        try {
            // サービスレイヤーの呼び出し
            ImportAccountService.Result result = importAccountService.cancelUpload();

            response.put("success", result.isSuccess());
            response.put("message", result.getMessage());

            return response;

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "エラーが発生しました: " + e.getMessage());
            return response;
        }
    }

    // ================================
    // その他のメソッド
    // ================================

    /**
     * 実行ボタン（保留中）
     * 後で実装予定
     */
    @PostMapping("/import/execute")
    @ResponseBody
    public Map<String, Object> executeImport() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "この機能は準備中です");
        return response;
    }
}
