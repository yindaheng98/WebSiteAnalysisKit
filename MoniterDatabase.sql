set names utf8;
drop database if exists MoniterData;
create database MoniterData DEFAULT CHARACTER SET utf8;
use MoniterData;
create table 事件记录
(
编号 bigint primary key auto_increment,
时间 datetime not null,
IP地址 varchar(64) not null,
设备型号 text not null,
`SESSION编号` varchar(64),
所在页面 varchar(255) not null,
用户 varchar(255),
事件类型 varchar(255) not null,
事件描述 text
);
drop user if exists 'MoniterData'@'%';
create user 'MoniterData'@'%' identified by 'MoniterData';
grant create,select,update,insert on MoniterData.* to 'MoniterData'@'%';
flush privileges;