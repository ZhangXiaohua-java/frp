package element.io;

import element.io.component.ExecutorInstance;
import element.io.component.ServerInstance;
import element.io.proxy.DispatchHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
@Slf4j
@ServletComponentScan(basePackages = {"element.io.handler"})
@SpringBootApplication
public class App {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(App.class, args);
		ServerSocket serverSocket = ServerInstance.getServerSocket();
		if (Objects.isNull(serverSocket)) {
			throw new RuntimeException("程序启动失败");
		}
		log(serverSocket);
		initial(serverSocket);
	}


	public static void log(ServerSocket serverSocket) {
		InetSocketAddress socketAddress = (InetSocketAddress) serverSocket.getLocalSocketAddress();
		String host = socketAddress.getHostString();
		int port = socketAddress.getPort();
		log.info("服务启动成功 {}:{} ", host, port);
	}


	public static void initial(ServerSocket serverSocket) throws IOException {
		DispatchHandler dispatchHandler = new DispatchHandler(serverSocket, ExecutorInstance.getExecutor());
		dispatchHandler.communication();
	}


}
