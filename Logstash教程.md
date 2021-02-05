# Logstash教程

## 1. 使用配置文件启动Logstash

```shell
bin/logstash -f logstash-simple.conf
```

##  2. Logstash配置文件的配置规则

将文件内容发送到指定主机上的 ElasticSearch上并同时在stdout上输出。

```
input { 
	stdin { } 
}

output {
  	elasticsearch { 
  		hosts => ["localhost:9200"] 
  	}
  	stdout { 
  		codec => rubydebug 
  	}
}
```



## 3. Configuring Filters

如下配置了两个过滤器：`grok` 和 `date`

```
input { stdin { } }

filter {
  grok {
    match => { "message" => "%{COMBINEDAPACHELOG}" }
  }
  date {
    match => [ "timestamp" , "dd/MMM/yyyy:HH:mm:ss Z" ]
  }
}

output {
  elasticsearch { hosts => ["localhost:9200"] }
  stdout { codec => rubydebug }
}
```



```
input {
  file {
    path => "/tmp/access_log"
    start_position => "beginning"
  }
}

filter {
  if [path] =~ "access" {
    mutate { replace => { "type" => "apache_access" } }
    grok {
      match => { "message" => "%{COMBINEDAPACHELOG}" }
    }
  }
  date {
    match => [ "timestamp" , "dd/MMM/yyyy:HH:mm:ss Z" ]
  }
}

output {
  elasticsearch {
    hosts => ["localhost:9200"]
  }
  stdout { codec => rubydebug }
}
```





## Logstash语法

**1. Logstash设计了自己的DSL，包括有：区域、注释、数据类型（布尔值、字符串、数值、哈希）、条件判断字段引用的等。**



**2. Logstash用 { } 来定义区域，区域内可以包括插件区域定义，你可以在一个区域内定义多个插件。插件区域内则可定义键值对设置；**



**3. 格式、语法、使用方式**

```
# 注释
input {
	...
}

filter {
	...
}

output {
	...
}


## 两个input设置
input {
	file {
		path => "/var/log/messages"
		type => "syslog"
	}
	file {
		path => "/var/log/apache/access.log"
		type => "apache"
	}
}


# 数据类型
## bool类型
debug => true

## string类型
host => "hostname"

## number类型
port => 6789

## array or list
path => ["/var/log/message", "/var/log/*.log"]

## hash类型
match => {
	"field1" => "value1"
	"field2" => "value2"
}

## codec类型
codec => "json"

## 字段引用方式
{
	"agent": "Mozilla/5.0 (compatible; MSIE 9.0)"
	"ip": "192.168.24.44",
	"request": "/index.html"
	"response": {
		"status": 200,
		"types": 52353
	}
	"ua": {
		"os": "windows 7"
	}
}
## 获取字段值
[response][status]
[ua][os]


## 条件判断condition
if EXPRESSION {
	...
} else if EXPRESSION {
	...
} else {
	...
}

==(等于), !=(不等于), <(小于), <=(小于等于), >=(大于等于), =~(匹配正则), !~(不匹配正则)

in(包含), not in(不包含), and(与), or(或), nand(与非), xor(或非)


## 使用环境变量（缺失报错）:
input {
	tcp {
		port => "${TCP_PORT}"
	}
}

## 使用环境变量（缺失使用默认值）:
input {
	tcp {
		port => "${TCP_PORT:54321}"
	}
}
```



**4. Logstash例子**

```
## input 从标准输入流
input { stdin{ } }

## 输入数据之后，如何进行处理
filter {
	## grok: 解析元数据插件，这里从 input 输入进来的所有数据默认都会存放到 "message" 字段中
	## grok提供很多正则表达式，地址为: http://grokdebug.herokuapp.com/patterns
	## 比如: %{COMBINEDAPACHELOG} 表示其中一种正则表达式 Apache的表达式
	grok {
		match => { "message" => "%{COMBINEDAPACHELOG}" }
	}
	## date: 日期格式化
	date {
		match => [ "timestamp" , "dd/MMM/yyyy:HH:mm:ss Z" ]
	}
}

## output 从标准输出流：
output {
	elasticsearch { host => ["192.168.11.35:9200"] }
	stdout { codec => rubydebug }
}
```

**5. file插件使用**

```
## file插件
input {
	file {
		path => ["/var/log/*.log", "/var/log/message"]
		type => "system"
		start_position => "beginning"
	}
}

## 其他参数
discover_internal  ## 表示每隔多久检测一下文件，默认15秒
exclude            ## 表示排除哪些文件
close_older        ## 文件超过多长时间没有更新，就关闭监听，默认3600s
ignore_older       ## 每次检查文件列表，如果有一个文件，最后修改时间超过这个值，那么就忽略文件，86400s
sincedb_path	   ## sincedb保存文件的位置，默认存在home下（/dev/null）
sincedb_write_internal ## 每隔多久去记录一次，默认15秒
stat_interval	   ## 每隔多久查询一次文件状态，默认1秒
start_position     ## 从头开始读取或则从结尾开始读取
```





# 教程

## 1. 基础知识

### 1.3 Hello World

`cat randdata | awk '{print $2}' | sort | uniq -c | tee sortdata`

电子书地址：http://doc.yonyoucloud.com/doc/logstash-best-practice-cn/get_start/hello_world.html

Logstash就像管道符一样：你**输入**（就像命令行的`cat`）数据，然后处理**过滤**（就像`awk`或者`uniq`之类）数据，最后**输出**（就像`tee`）到其他地方。

当然，实际上，Logstash是用不同的县城来实现这些的。

小贴士：logstash很温馨的给每个线程都取了名字，输入的脚xx，过滤的脚|xx

数据在线程之间以 **事件** 的形式流传。不要叫行，因为logstash可以处理多行事件。



Logstash会给事件添加一些额外信息。最重要的就是 **@timestamp**，用来标记事件的发生事件。

因为这个字段涉及到Logstash的内部流转，所有必须是一个joda对象，如果你尝试自己给一个字符串字段重命名为`@timestamp`的话，Logstash会直接报错。所以，请使用 `filters/date插件`来管理这个特殊字段。

此外，大多数时候，还可以见到另外几个：

- **host** 标记事件发生在哪里；
- **type** 标记事件的唯一类型；
- **tags** 标记事件的某方面实行。这是一个数组，一个数组可以有多个标签；

你可以随意给事件添加字段或者从事件里删除字段。事实上事件就是一个Ruby对象，或则更简单的理解为就是一个哈希也行。

小贴士：每个logstash 过滤插件，都会有四个方法叫：`add_tag`, `remove_tag`, `add_field` 和 `remove_field`。他们在过滤匹配成功时生效。



### 1.4 长期运行

#### 标准的service方式

采用RPM、DEB发行包安装的读者，推荐采用这种方式。发行包内，都自带有sysV或者systemd风格的启动程序/配置，你只需要直接使用即可。

以RPM为例，`/etc/init.d/logstash`脚本中，会加载 `/etc/init.d/functions`库文件，利用其中的`daemon`函数，将logstash进程作为后台程序运行。

所以，你只需要吧自己写好的配置文件，同意放在`/etc/logstash/`目录下（注意目录下所有配置文件都应该是**.conf**结尾，且不能有其他文本文件存在。因为logstash agent启动的时候是读取全文件夹的），然后运行`service logstash start`命令即可。

#### 最基础的nohup方式

#### 更优雅的SCREEN方式

#### 最推荐的daemontools方式

不管是 nohup 还是screen，都不是可以很方便管理的方式，在运维管理一个ELK集群的时候，必须寻找一种尽可能简介的办法。所以，对于需要长期后台运行的大量程序（注意大量，如果就一个进程，还是学习一下怎么写init脚本吧），推荐大家使用一款daemontools工具。

daemontools是一个软件名称，不过配置略复杂。所以这里我其实是用其名称来指代整个同类产品，包括但不限于python实现的supervisord，perl实现的ubic，ruby实现的god等。

以supervisord为例，因为这个出来的比较早，可以直接通过EPEL仓库安装。

```shell
yum -y install supervisord --enablerepo=epe1
```

在 `/etc/supervisord.conf`配置文件里添加内容，定义你要启动的程序：

```
[program:elkpro_1]
environment=LS_HEAP_SIZE=5000m
directory=/opt/logstash
command=/opt/logstash/bin/logstash -f /etc/logstash/pro1.conf --pluginpath /opt/logstash/plugins/ -w 10 -l /var/log/logstash/pro1.log
[program:elkpro_2]
environment=LS_HEAP_SIZE=5000m
directory=/opt/logstash
command=/opt/logstash/bin/logstash -f /etc/logstash/pro2.conf --pluginpath /opt/logstash/plugins/ -w 10 -l /var/log/logstash/pro2.log
```

然后启动 `service supervisord start`即可。

logstash 会以 supervisord 子进程的身份运行，你好可以使用`supervisorctl`命令，单独控制一些咧logstash子进程中某一个进程的启停操作：

```shell
supervisorctl stop elkpro_2
```



































































































































