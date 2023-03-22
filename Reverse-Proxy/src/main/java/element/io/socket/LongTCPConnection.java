package element.io.socket;

import element.io.utils.INetUtils;
import element.io.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
public class LongTCPConnection implements Runnable {


	private Socket socket;

	public LongTCPConnection(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			socket.setKeepAlive(true);
			InputStream inputStream = socket.getInputStream();
			String str = IOUtils.readStr(inputStream);
			String hostInfo = INetUtils.getHostInfo(socket.getRemoteSocketAddress());
			System.out.println("主机: " + hostInfo + "发送消息{ " + str + " }");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
