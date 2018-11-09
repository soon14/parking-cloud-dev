MAC

brew install ruby

brew install postgresql

Pg_config: /usr/local/Cellar/postgresql/9.3.3/bin/pg_config

gem install pg -- --with-pg-config=/Applications/Postgres.app/Contents/MacOS/bin/pg_config



LINUX

yum install postgresql-libs

yum install postgresql-devel

gem install pg

安装好pg 扩展后 
ruby initexecuted.rb  建立存放执行表的表
ruby makesql.rb 执行database中的sql（makesq.rb中的database 的路径要正确）

