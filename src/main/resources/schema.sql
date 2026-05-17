CREATE TABLE applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    contact_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    plan_name VARCHAR(100) NOT NULL
);

-- インポートアカウント中間テーブル
-- アップロードされたCSVファイルから読み込まれたアカウント情報を一時保存
CREATE TABLE import_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    upload_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    account_id VARCHAR(100) NOT NULL,
    permission_level INT
);

CREATE INDEX idx_import_account_file_name ON import_account(file_name);
CREATE INDEX idx_import_account_status ON import_account(status);
CREATE INDEX idx_import_account_upload_date ON import_account(upload_date);

-- インポートアカウント更新中間テーブル
-- アップロードされたファイルの取込履歴を管理
CREATE TABLE import_account_update (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL UNIQUE,
    upload_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    count INT
);

CREATE INDEX idx_import_account_update_file_name ON import_account_update(file_name);
CREATE INDEX idx_import_account_update_status ON import_account_update(status);
CREATE INDEX idx_import_account_update_upload_date ON import_account_update(upload_date);