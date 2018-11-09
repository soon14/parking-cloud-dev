## `postgis`的安装
> [官方文档](http://postgis.net/install/)

### 通过`yum`安装的`postgres`
[参考文档](http://www.postgresonline.com/journal/archives/362-An-almost-idiots-guide-to-install-PostgreSQL-9.5,-PostGIS-2.2-and-pgRouting-2.1.0-with-Yum.html)
#### 查看`postgresql`的版本
#### 安装`postgresql`的`yum`源
[下载yum源的rpm包](https://yum.postgresql.org/)
#### 查找对应版本的`postgis`
```bash
ps -ef|grep postgis2 | grep ${version}
```
#### 安装
```bash
yum install 刚才查到的
```
### 编译安装的`postgres`

> 这里是以`postgres9.6` + `postgis2.41`进行安装的

#### 下载对应的版本的`postgis`
[下载地址]()
#### 安装依赖
```bash
yum install geos geos-devel proj proj-devel gdal gdal-devel libxml2 libxml2 json-c json-c-devel SFCGAL SFCGAL-devel
```
#### 编译安装
```bash
./configure --prefix=path-to-pgsql \
--with-pgconfig=path-to-your-pg_config

make && make install
```
#### 测试是否安装成功
```bash
使用postgres连接数据库
执行sql:
select * from pg_available_extensions where name like 'postgis%';
```
如果出现如下图所示，则表示安装成功
![](http://oco6fs57t.bkt.clouddn.com/20171104150978710865537.png)
#### 切换到使用的数据库，在数据库中安装`postgis`扩展
```bash
create extension postgis;
```
#### `docker`
> `docker`直接拉取`mdillon/postgis:9.6`这个镜像

在拉取成功之后也是要在使用的数据库中安装`postgis`扩展，具体步骤如编译安装所示。