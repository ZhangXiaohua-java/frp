package element.io.socket;

import element.io.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 张晓华
 * @date 2023-3-21
 */
public class OnceConnection implements Runnable {

	private Socket socket;

	public OnceConnection(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		String str = null;
		try {
			str = IOUtils.readStr(socket.getInputStream());
			System.out.println("获取到的数据: " + str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
