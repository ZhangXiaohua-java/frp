package element.io.proxy;

import element.io.socket.LongTCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
public class DispatchHandler {

	private ServerSocket serverSocket;

	private Executor executor;

	public DispatchHandler(ServerSocket serverSocket, Executor executor) {
		this.serverSocket = serverSocket;
		this.executor = executor;
	}

	/**
	 * 采用和Redis一样的思路,单线程创建Socket连接,多线程处理连接
	 *
	 * @throws IOException
	 */
	public void communication() throws IOException {
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("有客户端连接");
				Runnable runnable = new LongTCPConnection(socket);
				CompletableFuture.runAsync(runnable, executor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
