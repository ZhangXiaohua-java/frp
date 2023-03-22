package element.io.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.*;
import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
public class INetUtils {


	/**
	 * @param socketAddress 远程主机的地址信息 ip:port
	 * @return
	 */
	public static String getHostInfo(SocketAddress socketAddress) {
		Objects.requireNonNull(socketAddress, "socketAddress can't be null");
		InetSocketAddress address = (InetSocketAddress) socketAddress;
		String hostName = address.getHostName();
		int port = address.getPort();
		return hostName.concat(":").concat(port + "");
	}


	/**
	 * @param socketAddress 远程主机的地址信息
	 * @return Pair left:host right: port
	 */
	public static Pair<String, Integer> getHostAndPort(SocketAddress socketAddress) {
		Objects.requireNonNull(socketAddress, "socketAddress can't be null");
		InetSocketAddress address = (InetSocketAddress) socketAddress;
		String hostName = address.getHostName();
		int port = address.getPort();
		return new ImmutablePair<>(hostName, port);
	}


	/**
	 * @param domain 域名
	 * @return IP地址
	 * @throws UnknownHostException 无法解析时会抛出此异常
	 */
	public static String parseDomainName(String domain) throws UnknownHostException {
		InetAddress address = InetAddress.getByName(domain);
		return address.getHostAddress();
	}


	/**
	 * @param host    IP
	 * @param timeout 超时时间
	 * @return 测试是否可以连接到该主机
	 */
	public static boolean canConnect2(String host, int timeout) throws IOException {
		InetAddress address = InetAddress.getByName(host);
		return address.isReachable(timeout);
	}


	/**
	 * @param host    IP
	 * @param port    port
	 * @param timeout 超时时间
	 * @return 测试是否可以连接到远程主机的指定端口
	 */
	public static boolean canConnect2RemoteServer(String host, int port, int timeout) {
		Socket socket = null;
		try {
			socket = SocketUtils.createSocket(host, port);
			return socket.isConnected();
		} catch (Exception e) {
			System.err.println("unknown domain name " + e.getMessage());
		}
		return false;
	}




}
