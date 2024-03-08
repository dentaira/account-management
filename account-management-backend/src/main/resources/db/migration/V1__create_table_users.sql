-- ユーザー
CREATE TABLE users (
      id CHAR(36) NOT NULL COMMENT 'ID'
    , login_id VARCHAR(100) NOT NULL COMMENT 'ログインID'
    , email VARCHAR(256) NOT NULL COMMENT 'メールアドレス'
    , user_name VARCHAR(100) NOT NULL COMMENT 'ユーザー名'
    , role VARCHAR(20) NOT NULL COMMENT 'ロール'
    , status VARCHAR(20) NOT NULL COMMENT 'ステータス'
    , created_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '作成日時'
    , updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時'
    , CONSTRAINT users_PKC PRIMARY KEY (id)
) COMMENT 'ユーザー' ;

CREATE INDEX unique_login_id
    ON users(login_id);

CREATE INDEX unique_email
    ON users(email);