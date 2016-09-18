update t_organ_company set organ_level = '4' where organ_level='3';

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('100', '2', '辽宁', '辽宁', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('101', '2', '四川', '四川', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('102', '2', '陕西', '陕西', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('103', '2', '甘肃', '甘肃', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('104', '2', '山西', '山西', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('105', '2', '重庆', '重庆', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('106', '2', '云南', '云南', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('150', '1', '北京', '北京', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('151', '1', '福建', '福建', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('152', '1', '广东', '广东', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('153', '1', '广西', '广西', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('154', '1', '河北', '河北', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('155', '1', '湖北', '湖北', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('156', '1', '江苏', '江苏', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('157', '1', '辽宁', '辽宁', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('158', '1', '山东', '山东', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('159', '1', '陕西', '陕西', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('160', '1', '四川', '四川', '', '1', sysdate, 'JY', '3');

insert into t_organ_company (ORGAN_ID, PARENT_ID, NAME, ABBR_NAME, ADDRESS, STATUS, CREATE_DATE, ORGAN_CODE, ORGAN_LEVEL)
values ('161', '1', '浙江', '浙江', '', '1', sysdate, 'JY', '3');

update t_organ_company t
   set t.parent_id = '100'
 where t.name in
       ('沈阳铁西店', '沈阳辽中店', '锦州凌河店', '南昌东湖店', '鞍山铁东店', '九江浔阳店', '烟台机场路店');
update t_organ_company t
   set t.parent_id = '101'
 where t.name in ('成都青羊店', '成都金牛店', '成都益州店', '南充高坪店', '德阳旌阳店');
update t_organ_company t
   set t.parent_id = '102'
 where t.name in ('武汉江汉店', '西安未央店', '渭南东风店', '荆州武德店', '西安户县店');
update t_organ_company t
   set t.parent_id = '103'
 where t.name in ('兰州城关店', '兰州雁滩店', '宝鸡渭滨店', '天水麦积店', '武威万通店', '定西安定店',
        '酒泉高新店', '白银长安店', '陇南武都店', '永登城关店');

update t_organ_company t
   set t.parent_id = '104'
 where t.name in ('太原杏花岭店', '保定莲池店', '太原清徐店', '郑州金水店', '临汾国贸店', '长治解放西店',
        '洛阳西工店', '石家庄裕华店', '朔州华源店', '西宁城西店', '银川金凤店', '驻马店驿城店',
        '邢台桥东店', '运城盐湖店', '许昌魏都店');

update t_organ_company t
   set t.parent_id = '105'
 where t.name in ('遵义汇川店', '重庆渝北店', '重庆永川店', '合肥高新店', '临沂兰山店', '重庆九龙坡店',
        '济南工北店', '铜仁碧江店', '重庆万州店', '宜宾翠屏店', '重庆涪陵店');

update t_organ_company t
   set t.parent_id = '106'
 where t.name in ('玉溪红塔店', '昆明官渡店');

update t_organ_company t
   set t.parent_id = '150'
 where t.name in ('北京朝阳店', '廊坊广阳店', '太原君威店', '呼和浩特首府店', '包头青山店');

update t_organ_company t
   set t.parent_id = '151'
 where t.name in ('广州天河店', '厦门湖里店', '福州台江店', '漳州龙文店', '泉州丰泽店');
update t_organ_company t
   set t.parent_id = '152'
 where t.name in ('佛山南海店', '汕头龙湖店', '中山中环店', '珠海香洲店');
update t_organ_company t
   set t.parent_id = '153'
 where t.name in ('深圳宝安店', '长沙雨花店', '贵阳观山湖店', '南宁青秀店', '海口龙华店', '东莞南城店',
        '惠州风尚店', '桂林育才店', '柳州华泰店');
update t_organ_company t
   set t.parent_id = '154'
 where t.name in ('天津南开店', '石家庄新华店', '张家口桥东店');
update t_organ_company t
   set t.parent_id = '155'
 where t.name in ('武汉沌口店', '襄阳樊城店', '宜昌伍家岗店', '衡阳华新店', '株洲芦淞店');
update t_organ_company t
   set t.parent_id = '156'
 where t.name in ('苏州姑苏店', '扬州邗江店', '无锡滨湖店', '徐州泉山店');
update t_organ_company t
   set t.parent_id = '157'
 where t.name in ('大连中山店', '唐山路南店', '秦皇岛迎宾店');
update t_organ_company t
   set t.parent_id = '158'
 where t.name in ('青岛市南店', '济南万达店', '潍坊奎文店', '淄博高新店', '德州天翔店');

update t_organ_company t
   set t.parent_id = '159'
 where t.name in ('兰州东方红店', '烟台芝罘店', '邯郸丛台店', '西安凤城店', '聊城东昌府店', '乌鲁木齐天山店');
update t_organ_company t
   set t.parent_id = '160'
 where t.name in ('成都锦江店', '昆明五华店', '绵阳涪城店', '重庆渝中店');
update t_organ_company t
   set t.parent_id = '161'
 where t.name in
       ('杭州江干店', '南京建邺店', '宁波鄞州店', '金华金东店', '温州鹿城店', '台州椒江店', '南昌青云谱店');