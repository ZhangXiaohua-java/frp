package element.io.config;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.setting.GroupedMap;
import cn.hutool.setting.Setting;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
@Slf4j
@Data
public class ConfigurationProperties {

	/* 是否运行在服务端 */
	private boolean server = true;

	/* 服务端端口,后面可以做成配置对象 */
	private int serverPort = 9090;

	/* 客户端端口 */
	private int clientPort = 10090;

	private List<RemoteNode> nodes;


	public void loadProperties() {
		log.debug("load properties ...");
		//此处要求必须存在application.setting配置文件必须存在,否则程序初始化失败...
		ClassPathResource classPathResource = new ClassPathResource("application.setting");
		Setting setting = new Setting(classPathResource.getAbsolutePath());
		GroupedMap groupedMap = setting.getGroupedMap();
		LinkedHashMap<String, String> serverMap = groupedMap.get("server");
		LinkedHashMap<String, String> clientMap = groupedMap.get("client");
		Objects.requireNonNull(serverMap, "initialize failed,requires server configuration items");
		Objects.requireNonNull(clientMap, "initialize failed,requires client configuration items");
		String s = serverMap.get("server.server");
		if ("true".equals(s)) {
			log.info("当前运行模式: Server");
			// 加载服务端的配置
			server = true;
			String portStr = serverMap.get("server.port");
			if (!"".equals(portStr)) {
				serverPort = Integer.parseInt(portStr);
			}
		} else {
			log.info("当前运行模式: Client");
			server = false;
			// 加载客户端的配置
			String localPort = clientMap.get("client.local.server.port");
			if (localPort != null && !"".equals(localPort)) {
				clientPort = Integer.parseInt(localPort);
			}
			// 加载远程节点的配置信息
			String nodeGroupStr = clientMap.get("client.remote.server");
			if (nodeGroupStr == null || "".equals(nodeGroupStr)) {
				throw new IllegalArgumentException("please specified remote nodes connection info");
			}
			this.nodes = new ArrayList<>();
			String[] nodesStr = nodeGroupStr.split(",");
			for (String nodeStr : nodesStr) {
				String[] nodeInfo = nodeStr.split(":");
				this.nodes.add(new RemoteNode(nodeInfo[0], Integer.parseInt(nodeInfo[1])));
			}
			// 去重
			this.nodes = this.nodes.stream().distinct().collect(Collectors.toList());
			log.info("加载出的远程节点信息{}", this.nodes);
		}
		log.debug("properties load over");
	}


}
