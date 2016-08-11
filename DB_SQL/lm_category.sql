CREATE TABLE LM_CATEGORY
(
  ID     NUMBER(20)                             NOT NULL,
  TITLE  VARCHAR2(50 BYTE),
  PID    NUMBER(20)
);

COMMENT ON TABLE LM_CATEGORY IS 'ҵ������';



CREATE UNIQUE INDEX PK_LM_CATEGORY ON LM_CATEGORY
(ID);


ALTER TABLE LM_CATEGORY ADD (
  CONSTRAINT PK_LM_CATEGORY
  PRIMARY KEY
  (ID)
  USING INDEX PK_LM_CATEGORY
  ENABLE VALIDATE);
