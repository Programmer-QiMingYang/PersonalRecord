### MySql安装记录

----
![Mysql](https://img.shields.io/static/v1?label=Mysql&message=5.7.24-winx64.zip&color=brightgreen)&nbsp;&nbsp;





#### 1、下载mysql

我这里下载的是mysql-5.7.24-winx64.zip

#### 2、解压缩到文件

例如：解压到：`D:\DevelopTools\MySQL\`，修改文件名字mysql5724

#### 3、配置MYSQL_PATH,path添加mysql路径

1. 设置path路径`win+R`-->`sysdm.cpl` --> 高级 --> 环境变量 --> （系统变量）新建；`MYSQL_HOME=D:\DevelopTools\MySQL\mysql5724`；
2. 在path中添加：`%MYSQL_HOME%\bin`
3. mysql5.7中没有data目录，也没有my.ini；新建data文件夹，创建my.ini，内容如下：

```ini
[client]
port=3306
default-character-set=utf8
	
[mysqld] 
# 设置为自己MYSQL的安装目录 
basedir=D:\DevelopTools\MySQL\mysql5724
# 设置为MYSQL的数据目录 
datadir=D:\DevelopTools\MySQL\mysql5724\data
# 端口
port=3306
# 编码utf-8
character_set_server=utf8
sql_mode=NO_ENGINE_SUBSTITUTION,NO_AUTO_CREATE_USER
#开启查询缓存
explicit_defaults_for_timestamp=true
skip-grant-tables
```

*注意：如果出现3543错误，可以使用`mysqld --console`将日志打印在控制台*

#### 4、初始化

输入命令初始化：

```shell
mysqld --initialize-insecure --user=mysql
#mysqld --initialize-insecure 这个方法初始化完后，root用户无密码
#mysqld --initialize --console 这个方法初始化完后，root用户生成随机密码。密码 是console中输出的一段字符串（记住该字符串）
```

#### 5、mysql 安装问题

由于找不到MSVCR120.dll,无法继续执行代码.重新安装程序可能会解决此问题。直接下载vcredist即可

#### 6、安装

```shell
mysqld --install #出现Service successfully installed 就是安装成功
```

#### 7、登陆错误

```shell
mysql -u root -p #没有密码，直接登陆
#返回”Can't connect to MySQL server on localhost (10061)”错误。意思大概就是：无法连接到“本地主机”（10061）上的MySQL服务器，需要启动mysql服务：使用net start mysql，然后重新登陆
```

#### 8、改密码

如果是选择的无初始密码，那么我们还需要设置密码。

```shell
mysql -u root -p
#设置密码的格式set password for 用户名@localhost=password('新密码');
#下面我们设置密码为123456abc
set password for root@localhost=password('123456abc');
quit #退出
mysql -u root -p
#回车之后提示输入密码，就是刚才设置的密码，登陆成功就ok了
```

#### （9）ERROR 1290 (HY000): The MySQL server is running with the --skip-grant-tables option so it cannot execute this statement         

出现这个问题，只需要：`flush privileges`即可

#### 10、查看编码

```mysql
show variables like 'character%';##查看mysql编码
```

​		关于mysql编码查看具体可以参考这一篇文章：

```http
https://blog.csdn.net/Gincharl/article/details/82591068
```

