-- Create table
create table LM_DATA_ERP
(
  ID              NUMBER(10) not null,
  CLIENT_ID       NUMBER(10),
  ORDER_NO        VARCHAR2(40),
  CLIENT_NAME     VARCHAR2(50),
  CLIENT_TEL      VARCHAR2(20),
  ORGAN_CODE      VARCHAR2(20),
  SOURCE_CODE     VARCHAR2(20),
  SOURCE_NAME     VARCHAR2(40),
  CREATED_TIME    DATE default SYSDATE,
  CREATED_USER_ID NUMBER(10),
  UPDATED_TIME    DATE,
  UPDATED_USER_ID NUMBER(10),
  REMARK          VARCHAR2(100)
);
-- Add comments to the table 
comment on table LM_DATA_ERP
  is 'erp推送数据';
-- Add comments to the columns 
comment on column LM_DATA_ERP.ID
  is '数据ID';
comment on column LM_DATA_ERP.CLIENT_ID
  is '线索ID';
comment on column LM_DATA_ERP.ORDER_NO
  is '订单号';
comment on column LM_DATA_ERP.CLIENT_NAME
  is '客户姓名';
comment on column LM_DATA_ERP.CLIENT_TEL
  is '客户电话';
comment on column LM_DATA_ERP.ORGAN_CODE
  is '机构编码';
comment on column LM_DATA_ERP.SOURCE_CODE
  is '来源编码';
comment on column LM_DATA_ERP.SOURCE_NAME
  is '来源名称';
comment on column LM_DATA_ERP.CREATED_TIME
  is '创建时间';
comment on column LM_DATA_ERP.CREATED_USER_ID
  is '创建人ID';
comment on column LM_DATA_ERP.UPDATED_TIME
  is '更新时间';
comment on column LM_DATA_ERP.UPDATED_USER_ID
  is '更新人ID';
comment on column LM_DATA_ERP.REMARK
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table LM_DATA_ERP
  add constraint PK_LM_DATA_ERP primary key (ID);
-- create sequence
create sequence s_data_erp_id;
-- add cloumn to lm_client
alter table lm_client add to_erp varchar2(2);
comment on column lm_client.to_erp is '是否推送erp';
