package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * インポートアカウント用リポジトリ
 * DBとのやり取りを管理
 */
@Repository
public interface ImportAccountRepository extends JpaRepository<ImportAccount, Long> {

    /**
     * ファイル名で検索
     */
    List<ImportAccount> findByFileName(String fileName);

    /**
     * ステータスで検索
     */
    List<ImportAccount> findByStatus(String status);

    /**
     * ファイル名とステータスで検索
     */
    List<ImportAccount> findByFileNameAndStatus(String fileName, String status);

    /**
     * ファイル名のデータをすべて削除
     */
    void deleteByFileName(String fileName);
}
