CREATE TABLE T_TEST_TEST
(
  ID               NUMBER(10)                   NOT NULL,
  CONTENT          VARCHAR2(1000 BYTE),
  TYPE             VARCHAR2(10 BYTE),
  PARAMS_NUM       VARCHAR2(10 BYTE),
  STATUS           VARCHAR2(5 BYTE),
  REMARK           VARCHAR2(200 BYTE),
  CREATED_TIME     DATE,
  CREATED_USER_ID  NUMBER(10),
  UPDATED_TIME     DATE,
  UPDATED_USER_ID  NUMBER(10)
);
