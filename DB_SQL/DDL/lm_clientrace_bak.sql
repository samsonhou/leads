CREATE TABLE LM_CLIENTRACE_BAK
(
  ID      NUMBER(10)                            NOT NULL,
  TITLE   VARCHAR2(2000 BYTE),
  T_ID    NUMBER(8),
  REDATE  DATE,
  U_ID    VARCHAR2(50 BYTE),
  RANK    CHAR(1 BYTE),
  REASON  CHAR(1 BYTE)
);
