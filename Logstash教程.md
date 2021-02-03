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
input {
	stdin { }
}

filter {
	grok {
		match => { "message" => "%{COMBINEEDAPACHELOG}" }
	}
	data {
		match => [ "timestamp", "dd/MMM/yyyy:HH:mm:ss Z" ]
	}
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























































































































































