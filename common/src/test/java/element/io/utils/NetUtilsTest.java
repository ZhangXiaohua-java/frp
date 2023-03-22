package element.io.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author 张晓华
 * @date 2023-3-22
 */

public class NetUtilsTest {


	@Test
	public void hostInfoTest() {
		InetSocketAddress socketAddress = InetSocketAddress.createUnresolved("192.168.123.42", 8080);
		String hostInfo = INetUtils.getHostInfo(socketAddress);
		System.out.println(hostInfo);
		Pair<String, Integer> pair = INetUtils.getHostAndPort(socketAddress);
		System.out.println(pair);
		System.out.println(pair.getLeft());
		System.out.println(pair.getRight());
	}


	@Test
	public void ParseDomainTest() throws UnknownHostException {
		INetUtils.parseDomainName("www.baidu.com");
	}

	@Test
	public void canReachTest() throws IOException {
		boolean res = INetUtils.canConnect2("www.baidu.com", 3000);
		System.out.println(res);
		res = INetUtils.canConnect2RemoteServer("www.ab1564561561c.com", 80, 3000);
		System.out.println(res);
	}




}
