-- 会員
CREATE TABLE members (
      member_id UUID NOT NULL
    , company_name character varying(64) NOT NULL
    , department_name character varying(32)
    , status character varying(32) NOT NULL
    , email character varying(256) NOT NULL
    , version integer NOT NULL
    , created_at timestamptz(0) NOT NULL
    , updated_at timestamptz(0) NOT NULL
    , CONSTRAINT members_PKC PRIMARY KEY (member_id)
) ;

COMMENT ON TABLE members IS '会員';
COMMENT ON COLUMN members.member_id IS 'ID';
COMMENT ON COLUMN members.company_name IS '会社名';
COMMENT ON COLUMN members.department_name IS '支店・営業所・部署名';
COMMENT ON COLUMN members.status IS 'ステータス';
COMMENT ON COLUMN members.email IS 'メールアドレス';
COMMENT ON COLUMN members.version IS 'バージョン';
COMMENT ON COLUMN members.created_at IS '作成日時';
COMMENT ON COLUMN members.updated_at IS '更新日時';
        
-- ユーザー
CREATE TABLE users (
      user_id UUID NOT NULL
    , member_id UUID NOT NULL
    , email character varying(256) NOT NULL
    , password text NOT NULL
    , user_name character varying(64) NOT NULL
    , role character varying(32) NOT NULL
    , status character varying(32) NOT NULL
    , version integer NOT NULL
    , created_at timestamptz(0) NOT NULL
    , updated_at timestamptz(0) NOT NULL
    , CONSTRAINT users_PKC PRIMARY KEY (user_id)
) ;

CREATE UNIQUE INDEX unique_email
    ON users(email) WHERE status <> 'Deleted';

COMMENT ON TABLE users IS 'ユーザー';
COMMENT ON COLUMN users.user_id IS 'ID';
COMMENT ON COLUMN users.member_id IS '会員ID';
COMMENT ON COLUMN users.email IS 'メールアドレス';
COMMENT ON COLUMN users.password IS 'パスワード';
COMMENT ON COLUMN users.user_name IS 'ユーザー名';
COMMENT ON COLUMN users.role IS 'ロール';
COMMENT ON COLUMN users.status IS 'ステータス';
COMMENT ON COLUMN users.version IS 'バージョン';
COMMENT ON COLUMN users.created_at IS '作成日時';
COMMENT ON COLUMN users.updated_at IS '更新日時';
