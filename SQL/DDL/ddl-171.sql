-- Create table
create table T_USER_MOOR
(
  ID              NUMBER(10) not null,
  USER_ID         NUMBER(10),
  AGENT_ID        VARCHAR2(30),
  CREATED_TIME    DATE default SYSDATE,
  CREATED_USER_ID NUMBER(10),
  UPDATED_TIME    DATE,
  UPDATED_USER_ID NUMBER(10),
  REMARK          VARCHAR2(100)
);
-- Add comments to the table 
comment on table T_USER_MOOR
  is '七陌用户映射表';
-- Add comments to the columns 
comment on column T_USER_MOOR.ID
  is '数据ID';
comment on column T_USER_MOOR.USER_ID
  is '用户ID';
comment on column T_USER_MOOR.AGENT_ID
  is '七陌用户ID';
comment on column T_USER_MOOR.CREATED_TIME
  is '创建时间';
comment on column T_USER_MOOR.CREATED_USER_ID
  is '创建人ID';
comment on column T_USER_MOOR.UPDATED_TIME
  is '更新时间';
comment on column T_USER_MOOR.UPDATED_USER_ID
  is '更新人ID';
comment on column T_USER_MOOR.REMARK
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_USER_MOOR
  add constraint PK_T_USER_MOOR primary key (ID);

create sequence s_user_moor_id start with 100;