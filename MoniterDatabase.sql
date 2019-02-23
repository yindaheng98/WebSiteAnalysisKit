drop database MoniterData;
create database MoniterData;
use MoniterData;
create table 访问记录
(
时间 datetime not null,
IP地址 varchar(64) not null,
设备型号 varchar(64) not null,
`SESSION编号` varchar(64),
所在页面 varchar(255) not null,
用户 varchar(255),
事件类型 varchar(255) not null,
事件描述 varchar(255)
);
create user MoniterData@localhost identified by 'MoniterData';
grant select,update,insert on MoniterData.* to MoniterData@localhost;
flush privileges;