package cn.edu.pku.demo;

import cn.edu.pku.utils.JsonUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.command.ListVolumesResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.util.Collections;
import java.util.List;

public class DockerDemo {

//    private static String dockerHost = "tcp://127.0.0.1:2375";
    private static String dockerHost = "tcp://192.168.1.80:2375";

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

        Volume volume = new Volume("/var/cowrie01"); //容器里面的路径

        CreateContainerResponse response = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(HostConfig.newHostConfig().withPortBindings(portBinding))
                .withExposedPorts(tcp80)
                .withHostConfig(new HostConfig().withBinds(new Bind("/log/cowrie", volume)))
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
        CreateContainerResponse response = createContainer("cowrie01", "cowrie/cowrie");
        startContainer(response.getId());
    }
}
