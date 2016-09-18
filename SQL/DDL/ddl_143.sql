-- Create table
create table T_MILEAGE_CAR
(
  ID          NUMBER(10) not null,
  CUSTOMERID  NUMBER(10),
  CUSTOMERTEL VARCHAR2(20),
  CARVIN      VARCHAR2(30),
  BRANDTYPE   VARCHAR2(30),
  DELIVERDATE DATE
);
-- Add comments to the table 
comment on table T_MILEAGE_CAR
  is '里程信息用车表';
-- Add comments to the columns 
comment on column T_MILEAGE_CAR.ID
  is 'ID';
comment on column T_MILEAGE_CAR.CUSTOMERID
  is '客户ID';
comment on column T_MILEAGE_CAR.CUSTOMERTEL
  is '客户电话';
comment on column T_MILEAGE_CAR.CARVIN
  is '车架号';
comment on column T_MILEAGE_CAR.BRANDTYPE
  is '品牌型号';
comment on column T_MILEAGE_CAR.DELIVERDATE
  is '交车日期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MILEAGE_CAR
  add constraint PK_T_MILEAGE_CAR primary key (ID);


create sequence S_MILEAGE_CAR;



-- Create table
create table T_MILEAGE_CUSTOMER
(
  ID                NUMBER(10) not null,
  SUBSIDIARY        VARCHAR2(20),
  STORE             VARCHAR2(50),
  CUSTOMERNAME      VARCHAR2(20),
  TEL               VARCHAR2(20),
  INVESTIGATOR      VARCHAR2(20),
  INVESTIGATORSEX   CHAR(1),
  INVESTIGATIONDATE DATE,
  CONNECTSTATUS     VARCHAR2(100)
);
-- Add comments to the table 
comment on table T_MILEAGE_CUSTOMER
  is '里程信息客户表';
-- Add comments to the columns 
comment on column T_MILEAGE_CUSTOMER.ID
  is 'ID';
comment on column T_MILEAGE_CUSTOMER.SUBSIDIARY
  is '子公司';
comment on column T_MILEAGE_CUSTOMER.STORE
  is '门店';
comment on column T_MILEAGE_CUSTOMER.CUSTOMERNAME
  is '客户名称';
comment on column T_MILEAGE_CUSTOMER.TEL
  is '客户电话';
comment on column T_MILEAGE_CUSTOMER.INVESTIGATOR
  is '调研人';
comment on column T_MILEAGE_CUSTOMER.INVESTIGATORSEX
  is '调研人性别 0 女 1 男';
comment on column T_MILEAGE_CUSTOMER.INVESTIGATIONDATE
  is '调研日期';
comment on column T_MILEAGE_CUSTOMER.CONNECTSTATUS
  is '接通情况';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MILEAGE_CUSTOMER
  add constraint PK_T_MILEAGE_CUSTOMER primary key (ID);

create sequence S_MILEAGE_CUSTOMER;



-- Create table
create table T_MILEAGE_CUSTOMER_MILEAGE
(
  ID          NUMBER(10) not null,
  CARID       NUMBER(10),
  MILEAGE     NUMBER,
  LASTMILEAGE NUMBER,
  ISREMIND    CHAR(1)
);

-- Add comments to the columns 
comment on column T_MILEAGE_CUSTOMER_MILEAGE.ID
  is 'ID';
comment on column T_MILEAGE_CUSTOMER_MILEAGE.CARID
  is '车辆ID';
comment on column T_MILEAGE_CUSTOMER_MILEAGE.MILEAGE
  is '行驶里程';
comment on column T_MILEAGE_CUSTOMER_MILEAGE.LASTMILEAGE
  is '上次行驶里程';
comment on column T_MILEAGE_CUSTOMER_MILEAGE.ISREMIND
  is '是否已提醒 0 未提醒 1 已提醒';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MILEAGE_CUSTOMER_MILEAGE
  add constraint PK_T_CUSTOMER_MILEAGE primary key (ID);

create sequence S_MILEAGE_CUSTOMER_MILEAGE;



-- Create table
create table T_MILEAGE_SATISFACTION
(
  ID           NUMBER(10) not null,
  CUSTOMERID   NUMBER(10),
  CUSTOMERTEL  VARCHAR2(20),
  QUESTIONID   NUMBER(10),
  ANSWER       VARCHAR2(100),
  DETAILANSWER VARCHAR2(300)
);

-- Add comments to the table 
comment on table T_MILEAGE_SATISFACTION
  is '客户满意度调查表';
-- Add comments to the columns 
comment on column T_MILEAGE_SATISFACTION.ID
  is 'ID';
comment on column T_MILEAGE_SATISFACTION.CUSTOMERID
  is '客户ID';
comment on column T_MILEAGE_SATISFACTION.CUSTOMERTEL
  is '客户电话';
comment on column T_MILEAGE_SATISFACTION.QUESTIONID
  is '对应问题';
comment on column T_MILEAGE_SATISFACTION.ANSWER
  is '答案';
comment on column T_MILEAGE_SATISFACTION.DETAILANSWER
  is '答案描述';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MILEAGE_SATISFACTION
  add constraint PK_T_SATISFACTION primary key (ID);

create sequence S_MILEAGE_SATISFACTION;



-- Create table
create table T_SATISFACTION_QUESTION
(
  ID          NUMBER(10) not null,
  SHORTNAME   VARCHAR2(20),
  QUESTION    VARCHAR2(500),
  OPTIONA     VARCHAR2(100),
  OPTIONB     VARCHAR2(100),
  OPTIONC     VARCHAR2(100),
  OPTIOND     VARCHAR2(100),
  OPTIONE     VARCHAR2(100),
  INPUTOPTION CHAR(1)
);

-- Add comments to the table 
comment on table T_SATISFACTION_QUESTION
  is '满意度调研问题';
-- Add comments to the columns 
comment on column T_SATISFACTION_QUESTION.ID
  is 'ID';
comment on column T_SATISFACTION_QUESTION.SHORTNAME
  is '问题代号';
comment on column T_SATISFACTION_QUESTION.QUESTION
  is '问题';
comment on column T_SATISFACTION_QUESTION.OPTIONA
  is '选项A';
comment on column T_SATISFACTION_QUESTION.OPTIONB
  is '选项B';
comment on column T_SATISFACTION_QUESTION.OPTIONC
  is '选项C';
comment on column T_SATISFACTION_QUESTION.OPTIOND
  is '选项D';
comment on column T_SATISFACTION_QUESTION.OPTIONE
  is '选项E';
comment on column T_SATISFACTION_QUESTION.INPUTOPTION
  is '需要手动填写的选项';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SATISFACTION_QUESTION
  add constraint PK_T_QUESTION primary key (ID);

alter table lm_client_import add channel varchar2(100);
comment on column lm_client_import.channel is '渠道明细';