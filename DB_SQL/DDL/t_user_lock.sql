CREATE TABLE T_USER_LOCK
(
  ID           NUMBER(10)                       NOT NULL,
  USER_ID      NUMBER(10),
  MONTH        VARCHAR2(10 BYTE),
  LOCK_SCORE   NUMBER(5),
  LOCK_TIME    DATE,
  LOCK_STATUS  VARCHAR2(2 BYTE)                 DEFAULT 0,
  LOCK_TIMES   NUMBER(5)                        DEFAULT 0,
  UNLOCK_TIME  DATE,
  UNLOCK_USER  VARCHAR2(32 BYTE),
  CREATE_USER  NUMBER(10),
  CREATE_DATE  DATE                             DEFAULT sysdate
);

COMMENT ON TABLE T_USER_LOCK IS '锁定用户信息表';

COMMENT ON COLUMN T_USER_LOCK.ID IS '数据ID';

COMMENT ON COLUMN T_USER_LOCK.USER_ID IS '用户ID';

COMMENT ON COLUMN T_USER_LOCK.MONTH IS '年月（yyyy-mm）';

COMMENT ON COLUMN T_USER_LOCK.LOCK_SCORE IS '锁定用户积分';

COMMENT ON COLUMN T_USER_LOCK.LOCK_TIME IS '锁定时间';

COMMENT ON COLUMN T_USER_LOCK.LOCK_STATUS IS '锁定状态 1:锁定  0:解锁';

COMMENT ON COLUMN T_USER_LOCK.LOCK_TIMES IS '加锁次数';

COMMENT ON COLUMN T_USER_LOCK.UNLOCK_TIME IS '解锁时间';

COMMENT ON COLUMN T_USER_LOCK.UNLOCK_USER IS '解锁执行用户';

COMMENT ON COLUMN T_USER_LOCK.CREATE_USER IS '创建人ID';

COMMENT ON COLUMN T_USER_LOCK.CREATE_DATE IS '创建时间';



CREATE UNIQUE INDEX PK_T_USER_LOCK ON T_USER_LOCK
(ID);


ALTER TABLE T_USER_LOCK ADD (
  CONSTRAINT PK_T_USER_LOCK
  PRIMARY KEY
  (ID)
  USING INDEX PK_T_USER_LOCK
  ENABLE VALIDATE);
