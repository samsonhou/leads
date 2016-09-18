insert into T_CODE_TYPE (NAME, CODE_TYPE, CREATE_DATE, STATUS, SQL, TYPE)
values ('性别', '1054', sysdate, '1', '', '1');

insert into T_CODE_ITEM (CODE_TYPE, NAME, VALUE, SEQ, STATUS, CREATE_DATE, ASSISTANT, CODE_ITEM_ID)
values ('1054', '男', '1', 1, '1', sysdate, '', 990);

insert into T_CODE_ITEM (CODE_TYPE, NAME, VALUE, SEQ, STATUS, CREATE_DATE, ASSISTANT, CODE_ITEM_ID)
values ('1054', '女', '0', 2, '1', sysdate, '', 991);

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (1, '问题一', '1. 请问您是从什么渠道知道花生好车（捷众普惠）的？
', 'A. 互联网（线上）', 'B. 门店活动（线下）', 'C. 销售人员', 'D. 熟人介绍', 'E. 其他', 'E');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (2, '问题二', '2. 销售人员给您打电话时，是否告诉您他的姓名与门店名称？', 'A. 是', 'B. 没有', 'C. 忘了', '', '', '');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (3, '问题三', '3. 工作人员是否告诉您到门店的详细路线，并在通话中使用礼貌用语？', 'A. 是', 'B. 没有', 'C. 忘了', '', '', '');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (4, '问题四', '4. 当您来店时，销售人员是否马上热情接待您，给您介绍业务？', 'A. 是', 'B. 没有', 'C. 忘了', '', '', '');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (5, '问题五', '5. 当您想了解购车相关信息时，销售人员的解答是否令您满意？', 'A. 满意', 'B. 不满意', 'C. 没感觉', '', '', '');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (6, '问题六', '6. 在您签约成交时，销售人员为您解释清楚各项费用了吗？', 'A. 清楚', 'B. 不清楚', 'C. 怀疑收错', '', '', 'C');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (7, '问题七', '7. 签约后的交车服务是否能够令您满意？', 'A. 满意', 'B. 不满意', 'C. 一般', '', '', 'B');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (8, '问题八', '8. 在本店所接受的服务，总的来说和您的期望相比如何？（您对我们门店的服务满意吗？）', 'A. 超出', 'B. 失望', 'C. 差不多', '', '', '');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (9, '问题九', '9. 请问您会向朋友或他人推荐我们的服务吗？', 'A. 一定会', 'B. 不会', 'C. 看情况', '', '', '');

insert into T_SATISFACTION_QUESTION (ID, SHORTNAME, QUESTION, OPTIONA, OPTIONB, OPTIONC, OPTIOND, OPTIONE, INPUTOPTION)
values (10, '问题十', '10. 您在购车过程中有没有遇到什么不愉快的地方或对我们有何建议？
', '', '', '', '', '', '');