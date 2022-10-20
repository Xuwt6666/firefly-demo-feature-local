
create table demo_config (
  config_id         char(24)     not null,
  config_name       varchar(100)  default '',
  config_key        varchar(100)  default '',
  config_value      varchar(100)  default '',
  config_type       char(1)        default 'N',
  create_by         varchar(64)   default '',
  create_time       varchar(19)   default '',
  remark            varchar(500)  default null
);
alter table demo_config add constraint pk_demo_config primary key (config_id);

 --样例数据
INSERT INTO demo_config(config_id, config_name, config_key, config_value, config_type, create_by, create_time, remark) VALUES ('A00000000000000000000001', '密码设置复杂度', 'sys.user.passwordMode11', '0', 'Y', 'admin', '2022-07-08 09:12:28','测试样例');
INSERT INTO demo_config(config_id, config_name, config_key, config_value, config_type, create_by, create_time, remark) VALUES ('A00000000000000000000002', '登录错误次数', 'sys.user.numOfLoginErr111', '5', 'N', 'admin', '2022-07-08 13:29:13','测试样例');


 ---样例模块菜单相关数据配置
INSERT INTO sys_rescource(ID_, PARENT_ID, NAME_, NAME_CN, KIND_, STATUS_, ORDER_, URL_, COMPONENT_, PERMS_, VISIBLE_, CREATE_TIME, CREATOR_, IMAGE_, MEMO_, TARGET_, TENANTID) VALUES ('DEMO_001', '0', 'system', '样例子应用', '5', '1', 1, '/demo', NULL, NULL, '0', '2022-07-08 09:12:28', 'admin', NULL, NULL, NULL, 'Firefly-admin');
INSERT INTO sys_rescource(ID_, PARENT_ID, NAME_, NAME_CN, KIND_, STATUS_, ORDER_, URL_, COMPONENT_, PERMS_, VISIBLE_, CREATE_TIME, CREATOR_, IMAGE_, MEMO_, TARGET_, TENANTID) VALUES ('DEMO_00101', 'DEMO_001', 'demo_conf', '系统配置', '0', '1', 2, 'system/config', 'system/config/index', 'demo:config:list', '0', '2022-07-08 09:14:10', 'admin', 'sysconfig', NULL, NULL, 'Firefly-admin');
INSERT INTO sys_rescource(ID_, PARENT_ID, NAME_, NAME_CN, KIND_, STATUS_, ORDER_, URL_, PERMS_, VISIBLE_, CREATE_TIME, CREATOR_, IMAGE_, MEMO_, TARGET_, TENANTID) VALUES ('DEMO_PERMS_01', 'DEMO_00101', 'demoConfQuery', '系统配置查询', '6', '1', 1, '', 'demo:config:query', '0', '2022-07-08 09:40:28', 'admin', NULL, NULL, NULL, 'Firefly-admin');
INSERT INTO sys_rescource(ID_, PARENT_ID, NAME_, NAME_CN, KIND_, STATUS_, ORDER_, URL_, PERMS_, VISIBLE_, CREATE_TIME, CREATOR_, IMAGE_, MEMO_, TARGET_, TENANTID) VALUES ('DEMO_PERMS_02', 'DEMO_00101', 'demoConfAdd', '系统配置新增', '6', '1', 2, '', 'demo:config:add', '0', '2022-07-08 09:40:28', 'admin', NULL, NULL, NULL, 'Firefly-admin');
INSERT INTO sys_rescource(ID_, PARENT_ID, NAME_, NAME_CN, KIND_, STATUS_, ORDER_, URL_, PERMS_, VISIBLE_, CREATE_TIME, CREATOR_, IMAGE_, MEMO_, TARGET_, TENANTID) VALUES ('DEMO_PERMS_03', 'DEMO_00101', 'demoConfEdit', '系统配置修改', '6', '1', 3, '', 'demo:config:edit', '0', '2022-07-08 09:40:28', 'admin', NULL, NULL, NULL, 'Firefly-admin');
INSERT INTO sys_rescource(ID_, PARENT_ID, NAME_, NAME_CN, KIND_, STATUS_, ORDER_, URL_, PERMS_, VISIBLE_, CREATE_TIME, CREATOR_, IMAGE_, MEMO_, TARGET_, TENANTID) VALUES ('DEMO_PERMS_04', 'DEMO_00101', 'demoConfRemove', '系统配置删除', '6', '1', 4, '', 'demo:config:remove', '0', '2022-07-08 09:40:28', 'admin', NULL, NULL, NULL, 'Firefly-admin');
INSERT INTO sys_rescource(ID_, PARENT_ID, NAME_, NAME_CN, KIND_, STATUS_, ORDER_, URL_, PERMS_, VISIBLE_, CREATE_TIME, CREATOR_, IMAGE_, MEMO_, TARGET_, TENANTID) VALUES ('DEMO_PERMS_05', 'DEMO_00101', 'demoConfExport', '系统配置导出', '6', '1', 5, '', 'demo:config:export', '0', '2022-07-08 09:40:28', 'admin', NULL, NULL, NULL, 'Firefly-admin');



 --样例模块数据字典配置
INSERT INTO sys_dataroute(ID_, PARENT_ID, LABEL_, ORDER_, TENANTID) VALUES ('SYSDATAROUTE_TEST', 'SYSDATAROUTE001', '测试', 2, 'Firefly-admin');
INSERT INTO sys_datacache(ID_, PARENT_KEY, KEY_, NAME_, ISLOADED, PARENT_ID, TENANTID) VALUES ('DEMO_CONFIG_TYPE_01', '', 'demo_config_type', '系统内置', '1', 'SYSDATAROUTE_TEST', 'Firefly-admin');
INSERT INTO sys_datainfo(ID_, KEY_, LABEL_, VALUE_, ORDER_, PROPERTY_, EXTEND_, TENANTID, DATACACHE_ID, PARENT_VALUE) VALUES ('DEMO_CONF_TYPE_Y', 'demo_config_type', '是', 'Y', 1, '', '', 'Firefly-admin', NULL, '');
INSERT INTO sys_datainfo(ID_, KEY_, LABEL_, VALUE_, ORDER_, PROPERTY_, EXTEND_, TENANTID, DATACACHE_ID, PARENT_VALUE) VALUES ('DEMO_CONF_TYPE_N', 'demo_config_type', '否', 'N', 2, '', '', 'Firefly-admin', NULL, '');

