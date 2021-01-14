package cn.edu.pku.demo;

import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
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
        System.out.println(infoStr);
        System.out.println("=====================================");
        return dockerClient;
    }

    public static CreateContainerResponse createContainer(String containerName, String imageName) {
        //映射端口 8088->80
        ExposedPort tcp80 = ExposedPort.tcp(80);
        Ports portBinding = new Ports();
        portBinding.bind(tcp80, Ports.Binding.bindPort(9003));

        CreateContainerResponse response = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(HostConfig.newHostConfig().withPortBindings(portBinding))
                .withExposedPorts(tcp80)
                .exec();
        String name = getContainerName(response.getId());
        System.out.println("Create container " + name + " success.");
        return response;
    }

    public static void startContainer(String containerId){
        String name = getContainerName(containerId);
        System.out.println("Start " + name + " Success.");
        dockerClient.startContainerCmd(containerId).exec();
    }

    public static void stopContainer(String containerId){
        String name = getContainerName(containerId);
        System.out.println("Stop " + name + " Success.");
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public static void removeContainer(String containerId){
        String name = getContainerName(containerId);
        System.out.println("Remove " + name + " Success.");
        dockerClient.removeContainerCmd(containerId).exec();
    }

    public static String getContainerName(String containerId) {
        InspectContainerResponse inspect = dockerClient.inspectContainerCmd(containerId).exec();
        return inspect.getName().replace("/", "");
    }

    public static void main(String[] args) {
//        String containerName = "nginx05";
//        String image = "nginx";
//        CreateContainerResponse response = createContainer(containerName, image);
//        System.out.println(response);

//        startContainer("1d3c22bef4384a37ceb12559906ecf18caa8866b1b9a1fdfeb4f488c362ed976");

//        stopContainer("1d3c22bef4384a37ceb12559906ecf18caa8866b1b9a1fdfeb4f488c362ed976");

        removeContainer("1d3c22bef4384a37ceb12559906ecf18caa8866b1b9a1fdfeb4f488c362ed976");

//        String name = getContainerName("1d3c22bef4384a37ceb12559906ecf18caa8866b1b9a1fdfeb4f488c362ed976");
//        System.out.println(name);
    }
}
