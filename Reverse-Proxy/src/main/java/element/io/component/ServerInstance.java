package element.io.component;

import element.io.config.ConfigurationProperties;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
public class ServerInstance {

	/* 唯一的ServerSocket */
	private static volatile ServerSocket serverSocket;

	/* 监视对象,双重检查 */
	private static Object monitor = new Object();

	private static final ConfigurationProperties configurationProperties = new ConfigurationProperties();


	static {
		// TODO 加载配置文件
		configurationProperties.loadProperties();
	}

	/**
	 * @return 初始化ServerSocket
	 * @throws IOException
	 */
	public static ServerSocket getServerSocket() throws IOException {
		if (Objects.isNull(serverSocket)) {
			synchronized (monitor) {
				if (Objects.isNull(serverSocket)) {
					serverSocket = new ServerSocket(configurationProperties.isServer() ? configurationProperties.getServerPort() : configurationProperties.getClientPort());
					// 释放ServerSocket的钩子函数
					Runtime.getRuntime().addShutdownHook(new Thread(() -> {
						try {
							if (serverSocket != null && !serverSocket.isClosed()) {
								System.out.println("释放了服务端的Socket资源");
								serverSocket.close();
							}
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}));
				}
			}
		}
		return serverSocket;
	}


}
