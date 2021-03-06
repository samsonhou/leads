CREATE TABLE T_JOBCONF
(
  ID              NUMBER(10),
  JOBNAME         VARCHAR2(100 BYTE),
  JOBGROUP        VARCHAR2(100 BYTE),
  JOBSTATUS       VARCHAR2(10 BYTE),
  CRONEXPRESSION  VARCHAR2(50 BYTE),
  DESCS           VARCHAR2(500 BYTE),
  CLSNAME         VARCHAR2(200 BYTE)
);


CREATE UNIQUE INDEX PK_T_JOB_CONF ON T_JOBCONF
(ID);


ALTER TABLE T_JOBCONF ADD (
  CONSTRAINT PK_T_JOB_CONF
  PRIMARY KEY
  (ID)
  USING INDEX PK_T_JOB_CONF
  ENABLE VALIDATE);
