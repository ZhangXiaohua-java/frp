package element.io.utils;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
public class SocketUtils {


	/**
	 * @param host 主机,也可以是域名,后面再做域名的解析
	 * @param port 端口
	 * @return Socket连接实例
	 * @throws IOException
	 */
	public static Socket createSocket(String host, int port) throws IOException {
		Socket socket = new Socket(host, port);
		socket.setKeepAlive(true);
		socket.setReuseAddress(true);
		return socket;
	}




}
