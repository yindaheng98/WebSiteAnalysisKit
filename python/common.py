"""一些公用的函数"""
from config import dbc#导入配置文件
cursor=dbc.cursor()

def query_fetch(SQL):
    print(SQL)
    cursor.execute(SQL)
    return cursor.fetchall()

def execute_commit(SQL):
    print(SQL)
    cursor.execute(SQL)
    dbc.commit()

def insert_datas(SQL_f,datas):
    for data in datas:
        SQL=SQL_f%data
        execute_commit(SQL)
