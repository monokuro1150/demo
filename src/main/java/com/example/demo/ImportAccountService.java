package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * インポートアカウント用サービス
 * CSVファイルの処理、バリデーション、DB操作を担当
 */
@Service
public class ImportAccountService {

    @Autowired
    private ImportAccountRepository importAccountRepository;

    @Autowired
    private ImportAccountUpdateRepository importAccountUpdateRepository;

    // ================================
    // CSVアップロード処理
    // ================================

    /**
     * CSVファイルをアップロードして検証
     * 
     * @param file アップロードされたCSVファイル
     * @return 成功時: {success: true, message: "..."}, エラー時: {success: false, message: "..."}
     */
    @Transactional
    public Result uploadCSV(MultipartFile file) {
        try {
            // 1. ファイル基本チェック
            if (file == null || file.isEmpty()) {
                return Result.error("ファイルが選択されていません");
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.endsWith(".csv")) {
                return Result.error("CSVファイルのみサポートされています");
            }

            // 2. ファイル内容のパース
            List<AccountData> accountDataList = parseCSV(file);
            if (accountDataList.isEmpty()) {
                return Result.error("CSVファイルは1行以上のデータが必要です");
            }

            // 3. フォーマットチェック
            List<String> formatErrors = validateCSVFormat(accountDataList);
            if (!formatErrors.isEmpty()) {
                return Result.error("フォーマットエラー: " + String.join(", ", formatErrors));
            }

            // 4. 既存のインポートアカウントデータを削除
            importAccountRepository.deleteByFileName(fileName);

            // 5. インポートアカウント中間テーブルに登録
            for (AccountData data : accountDataList) {
                ImportAccount importAccount = new ImportAccount(
                    fileName,
                    LocalDateTime.now(),
                    "PENDING",
                    data.getAccountId(),
                    data.getPermissionLevel()
                );
                importAccountRepository.save(importAccount);
            }

            // 6. インポートアカウント更新中間テーブルに登録
            ImportAccountUpdate update = new ImportAccountUpdate(
                fileName,
                LocalDateTime.now(),
                "PENDING", // PENDING: 待機中
                accountDataList.size()
            );
            importAccountUpdateRepository.save(update);

            return Result.success("CSVファイルをアップロードしました (" + accountDataList.size() + "件)");

        } catch (Exception e) {
            return Result.error("エラーが発生しました: " + e.getMessage());
        }
    }

    // ================================
    // キャンセル処理
    // ================================

    /**
     * アップロードをキャンセル（中間テーブルのデータ削除）
     */
    @Transactional
    public Result cancelUpload() {
        try {
            // 最新のPENDING状態のレコードを取得
            List<ImportAccountUpdate> pendingList = importAccountUpdateRepository.findByStatus("PENDING");
            
            if (pendingList.isEmpty()) {
                return Result.error("キャンセル対象のアップロードがありません");
            }

            // 最新のレコードを取得（複数ある場合は最初の1件）
            ImportAccountUpdate latest = pendingList.get(0);
            String fileName = latest.getFileName();

            // インポートアカウント中間テーブルのデータ削除
            importAccountRepository.deleteByFileName(fileName);

            // インポートアカウント更新中間テーブルのデータ削除
            importAccountUpdateRepository.deleteByFileName(fileName);

            return Result.success("ファイルのアップロードをキャンセルしました");

        } catch (Exception e) {
            return Result.error("キャンセル処理に失敗しました: " + e.getMessage());
        }
    }

    // ================================
    // ヘルパーメソッド
    // ================================

    /**
     * CSVファイルをパース
     */
    private List<AccountData> parseCSV(MultipartFile file) throws Exception {
        List<AccountData> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // 空行またはコメント行をスキップ
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    try {
                        String accountId = parts[0].trim();
                        int permissionLevel = Integer.parseInt(parts[1].trim());

                        AccountData data = new AccountData();
                        data.setAccountId(accountId);
                        data.setPermissionLevel(permissionLevel);
                        data.setLineNumber(lineNumber);

                        result.add(data);
                    } catch (NumberFormatException e) {
                        // パースエラーはバリデーションで検出される
                    }
                }
            }
        }

        return result;
    }

    /**
     * CSVフォーマット検証
     */
    private List<String> validateCSVFormat(List<AccountData> accountDataList) {
        List<String> errors = new ArrayList<>();

        for (AccountData data : accountDataList) {
            // アカウントID検証
            if (data.getAccountId() == null || data.getAccountId().trim().isEmpty()) {
                errors.add("行" + data.getLineNumber() + ": アカウントIDが空です");
                continue;
            }

            // 権限レベル検証（1-5の範囲）
            if (data.getPermissionLevel() == null || 
                data.getPermissionLevel() < 1 || data.getPermissionLevel() > 5) {
                errors.add("行" + data.getLineNumber() + ": 権限レベルは1-5で指定してください");
            }
        }

        return errors;
    }

    /**
     * 最新のインポート履歴を取得（8件まで）
     */
    public List<ImportAccountUpdate> getLatestHistory(int limit) {
        List<ImportAccountUpdate> all = importAccountUpdateRepository.findByOrderByUploadDateDesc();
        return all.size() > limit ? all.subList(0, limit) : all;
    }

    // ================================
    // 内部クラス
    // ================================

    /**
     * CSV行のデータ構造
     */
    private static class AccountData {
        private String accountId;
        private Integer permissionLevel;
        private int lineNumber;

        // Getter / Setter
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

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }
    }

    // ================================
    // 処理結果を表すクラス
    // ================================

    public static class Result {
        private boolean success;
        private String message;

        public Result(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public static Result success(String message) {
            return new Result(true, message);
        }

        public static Result error(String message) {
            return new Result(false, message);
        }

        // Getter
        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
