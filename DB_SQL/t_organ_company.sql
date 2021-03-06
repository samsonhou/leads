CREATE TABLE T_ORGAN_COMPANY
(
  ORGAN_ID     VARCHAR2(10 BYTE)                NOT NULL,
  PARENT_ID    VARCHAR2(10 BYTE),
  NAME         VARCHAR2(200 BYTE),
  ABBR_NAME    VARCHAR2(200 BYTE),
  ADDRESS      VARCHAR2(500 BYTE),
  STATUS       VARCHAR2(2 BYTE),
  CREATE_DATE  DATE                             DEFAULT sysdate,
  ORGAN_CODE   VARCHAR2(20 BYTE)                NOT NULL,
  ORGAN_LEVEL  VARCHAR2(5 BYTE)
);

COMMENT ON TABLE T_ORGAN_COMPANY IS '机构';

COMMENT ON COLUMN T_ORGAN_COMPANY.ORGAN_CODE IS '组织代码';

COMMENT ON COLUMN T_ORGAN_COMPANY.ORGAN_LEVEL IS '机构级别1总公司2二级机构3三级机构';



CREATE UNIQUE INDEX IDX_ORGAN_ID ON T_ORGAN_COMPANY
(ORGAN_ID);


CREATE UNIQUE INDEX PK_COMPANY_ORGAN_ID ON T_ORGAN_COMPANY
(ORGAN_ID, ORGAN_CODE);


ALTER TABLE T_ORGAN_COMPANY ADD (
  CONSTRAINT PK_COMPANY_ORGAN_ID
  PRIMARY KEY
  (ORGAN_ID, ORGAN_CODE)
  USING INDEX PK_COMPANY_ORGAN_ID
  ENABLE VALIDATE);
