-- ユーザー
CREATE TABLE users (
      id UUID NOT NULL
    , email character varying(256) NOT NULL
    , user_name character varying(64) NOT NULL
    , role character varying(32) NOT NULL
    , status character varying(32) NOT NULL
    , version integer NOT NULL
    , created_at timestamptz(0) NOT NULL
    , updated_at timestamptz(0) NOT NULL
    , CONSTRAINT users_PKC PRIMARY KEY (id)
) ;

CREATE UNIQUE INDEX unique_email
    ON users(email) WHERE status <> 'Deleted';

COMMENT ON TABLE users IS 'ユーザー';
COMMENT ON COLUMN users.id IS 'ID';
COMMENT ON COLUMN users.email IS 'メールアドレス';
COMMENT ON COLUMN users.user_name IS 'ユーザー名';
COMMENT ON COLUMN users.role IS 'ロール';
COMMENT ON COLUMN users.status IS 'ステータス';
COMMENT ON COLUMN users.version IS 'バージョン';
COMMENT ON COLUMN users.created_at IS '作成日時';
COMMENT ON COLUMN users.updated_at IS '更新日時';