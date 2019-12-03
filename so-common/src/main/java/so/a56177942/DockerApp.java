package so.a56177942;
import java.io.IOException;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.RestartContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public class DockerApp {
	
	public static void main(String[] args) throws IOException {
		DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
//				.withDockerHost("unix:///var/run/docker.sock").build();
		DockerClientBuilder builder = DockerClientBuilder.getInstance(config);
		try (DockerClient dockerClient = builder.build()) {
			restartContainers(dockerClient);
		}
	}

	private static void restartContainers(DockerClient dockerClient) {
		dockerClient.listContainersCmd().exec().stream()
			.map(Container::getId)
			.map(dockerClient::restartContainerCmd)
			.forEach(RestartContainerCmd::exec);
	}
}
