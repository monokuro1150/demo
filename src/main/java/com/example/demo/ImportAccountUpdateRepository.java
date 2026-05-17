package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * インポートアカウント更新用リポジトリ
 * アップロード情報の管理
 */
@Repository
public interface ImportAccountUpdateRepository extends JpaRepository<ImportAccountUpdate, Long> {

    /**
     * 最新のN件を取得
     */
    List<ImportAccountUpdate> findByOrderByUploadDateDesc();

    /**
     * ファイル名で検索
     */
    Optional<ImportAccountUpdate> findByFileName(String fileName);

    /**
     * ステータスで検索
     */
    List<ImportAccountUpdate> findByStatus(String status);

    /**
     * ファイル名で削除
     */
    void deleteByFileName(String fileName);
}
