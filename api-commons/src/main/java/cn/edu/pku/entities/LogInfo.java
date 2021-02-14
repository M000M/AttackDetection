package cn.edu.pku.entities;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "detection-log", type = "_doc")
public class LogInfo implements Serializable {

    private String path;

    private String host;

    private String message;
}
