CREATE TABLE SMS_CONFIG
(
  ID               NUMBER(10)                   NOT NULL,
  SMS_NAME         VARCHAR2(50 BYTE),
  USER_NAME        VARCHAR2(50 BYTE),
  USER_PWD         VARCHAR2(50 BYTE),
  URL              VARCHAR2(200 BYTE),
  CREATED_TIME     DATE                         DEFAULT sysdate,
  CREATED_USER_ID  NUMBER(10),
  UPDATED_TIME     DATE,
  UPDATED_USER_ID  NUMBER(10)
);

COMMENT ON TABLE SMS_CONFIG IS '短信平台配置表';

COMMENT ON COLUMN SMS_CONFIG.ID IS '数据ID';

COMMENT ON COLUMN SMS_CONFIG.SMS_NAME IS '平台名称';

COMMENT ON COLUMN SMS_CONFIG.USER_NAME IS '用户名';

COMMENT ON COLUMN SMS_CONFIG.USER_PWD IS '用户密码';

COMMENT ON COLUMN SMS_CONFIG.URL IS '服务地址';

COMMENT ON COLUMN SMS_CONFIG.CREATED_TIME IS '创建时间';

COMMENT ON COLUMN SMS_CONFIG.CREATED_USER_ID IS '创建人ID';

COMMENT ON COLUMN SMS_CONFIG.UPDATED_TIME IS '更新时间';

COMMENT ON COLUMN SMS_CONFIG.UPDATED_USER_ID IS '更新人ID';



CREATE UNIQUE INDEX PK_SMS_CONFIG ON SMS_CONFIG
(ID);


ALTER TABLE SMS_CONFIG ADD (
  CONSTRAINT PK_SMS_CONFIG
  PRIMARY KEY
  (ID)
  USING INDEX PK_SMS_CONFIG
  ENABLE VALIDATE);
