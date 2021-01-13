package cn.edu.pku.demo;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

public class DockerDemo {

    private static String dockerHost = "tcp://127.0.0.1:2375";

    private static DockerClient dockerClient;

    static {
        dockerClient = connectDocker();
    }

    public static DockerClient connectDocker() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost)
                .build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        Info info = dockerClient.infoCmd().exec();
        String infoStr = JSONObject.toJSONString(info);
        System.out.println("docker的环境信息如下：==================");
        System.out.println(info);
        System.out.println("=====================================");
        return dockerClient;
    }

    public static CreateContainerResponse createContainer(String containerName, String imageName) {
        //映射端口 8088->80
        ExposedPort tcp80 = ExposedPort.tcp(80);
        Ports portBinding = new Ports();
        portBinding.bind(tcp80, Ports.Binding.bindPort(8094));

        CreateContainerResponse response = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(HostConfig.newHostConfig().withPortBindings(portBinding))
                .withExposedPorts(tcp80)
                .withCmd("/bin/sh")
                .exec();
        return response;
    }

    public void startContainer(String containerId){
        //client.startContainerCmd(containerId).exec();
        dockerClient.startContainerCmd(containerId);
    }

    public static void stopContainer(String containerId){
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public static void removeContainer(String containerId){
        dockerClient.removeContainerCmd(containerId).exec();
    }

    public static void main(String[] args) {
        DockerDemo dockerClientService = new DockerDemo();
        String containerName = "test7";
        String image = "nginx";
        CreateContainerResponse response = createContainer(containerName, image);
        System.out.println(response.getId());

//        dockerClientService.startContainer("e87b9f42108b");
    }
}
