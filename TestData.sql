insert into moniterdata.事件记录(时间, IP地址, 设备型号, `SESSION编号`, 所在页面, 用户, 事件类型, 事件描述)
values
('2019-02-22 13:45','192.168.31.85','aaa',null,'yindaheng98.top','yin','访问页面',null),
('2019-02-24 13:45','192.168.31.85','aaa',null,'yindaheng98.top','yin','访问页面',null),
('2019-02-23 13:45','192.168.31.85','aaa',null,'yindaheng98.top/a','yin','访问页面',null),
('2019-02-23 13:25','192.168.31.85','aaa',null,'yindaheng98.top','yin','访问页面',null),
('2019-02-23 13:35','192.168.31.85','aaa',null,'yindaheng98.top','yin','访问页面',null),
('2019-02-25 13:45','192.168.31.85','aaa',null,'yindaheng98.top/a','yin','访问页面',null),
('2019-02-23 16:25','192.168.31.85','aaa',null,'yindaheng98.top','yin','访问页面',null),
('2019-02-23 15:35','192.168.31.85','aaa',null,'yindaheng98.top','yin','访问页面',null),
('2019-02-25 14:45','192.168.31.85','aaa',null,'yindaheng98.top','yin','访问页面',null);

insert into moniterdata.日访问量(时间,访问量)
values
('2019-02-22',100),
('2019-02-21',200);

insert into moniterdata.小时访问量(时间,访问量)
values
('2019-02-22 17:00',100),
('2019-02-21 17:00',200);