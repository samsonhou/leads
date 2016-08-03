CREATE TABLE LM_TASK_SCORE
(
  ID             NUMBER(10),
  CLIENT_ID      NUMBER(10),
  USER_ID        NUMBER(10),
  MONTH          VARCHAR2(10 BYTE),
  OVERTIME_TYPE  VARCHAR2(2 BYTE),
  SCORE          NUMBER(3),
  SCORE_TYPE     VARCHAR2(2 BYTE),
  CREATE_DATE    DATE                           DEFAULT sysdate
);

COMMENT ON TABLE LM_TASK_SCORE IS '������ֱ�';

COMMENT ON COLUMN LM_TASK_SCORE.ID IS '����ID';

COMMENT ON COLUMN LM_TASK_SCORE.CLIENT_ID IS '����ID';

COMMENT ON COLUMN LM_TASK_SCORE.USER_ID IS '�û�ID';

COMMENT ON COLUMN LM_TASK_SCORE.MONTH IS '���£�yyyy-mm��';

COMMENT ON COLUMN LM_TASK_SCORE.OVERTIME_TYPE IS '��ʱ���� 1:24Сʱ  2:96Сʱ  3:192Сʱ';

COMMENT ON COLUMN LM_TASK_SCORE.SCORE IS '����';

COMMENT ON COLUMN LM_TASK_SCORE.SCORE_TYPE IS '�۷����� 1���� 2�ӷ�(����ת�ɵ�)';

COMMENT ON COLUMN LM_TASK_SCORE.CREATE_DATE IS '����ʱ��';



CREATE UNIQUE INDEX PK_LM_TASK_SCORE ON LM_TASK_SCORE
(ID);


ALTER TABLE LM_TASK_SCORE ADD (
  CONSTRAINT PK_LM_TASK_SCORE
  PRIMARY KEY
  (ID)
  USING INDEX PK_LM_TASK_SCORE
  ENABLE VALIDATE);