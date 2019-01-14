drop database MoniterData;
create database MoniterData;
use MoniterData;
create table 访问记录
(
时间 datetime not null,
IP地址 varchar(64),
设备型号 varchar(64),
`SESSION编号` varchar(64),
所在页面 varchar(255),
点击元素 varchar(255) not null
);
create user MoniterData@localhost identified by 'MoniterData';
grant select,update,insert on MoniterData.* to MoniterData@localhost;
flush privileges;