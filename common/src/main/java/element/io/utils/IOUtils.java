package element.io.utils;

import element.io.ex.SerializeException;
import lombok.Cleanup;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
public final class IOUtils {


	/* 缓冲区大小 */
	public static int bufferSize = 1024;


	/**
	 * @param inputStream 字节流
	 * @return 带字符缓冲区的流
	 */
	public static BufferedReader getBufferReader(InputStream inputStream) {
		Objects.requireNonNull(inputStream, "inputStream can't be null");
		return new BufferedReader(new InputStreamReader(inputStream));
	}


	/**
	 * @param outputStream 字节流
	 * @return 带字符缓冲区的流
	 */
	public static BufferedWriter getBufferWriter(OutputStream outputStream) {
		Objects.requireNonNull(outputStream, "inputStream can't be null");
		return new BufferedWriter(new OutputStreamWriter(outputStream));
	}


	/**
	 * @param inputStream 字节流
	 * @return 将流中的数据读取到一个字符串中
	 */
	public static String readStr(InputStream inputStream) throws IOException {
		@Cleanup BufferedReader bufferReader = getBufferReader(inputStream);
		StringBuffer buffer = new StringBuffer();
		String temp = null;
		try {
			while ((temp = bufferReader.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new SerializeException(e.getMessage());
		}
		return buffer.toString();
	}


	/**
	 * @param inputStream 字节流
	 * @return 将输入流中的数据写入到字节数组中
	 * @throws IOException
	 */
	public static byte[] readBytes(InputStream inputStream) throws IOException {
		Objects.requireNonNull(inputStream, "inputStream can't be null");
		// TODO 这里还需要判断扩容,网络数据传输和本地文件的读取是不太一样的.
		byte[] bytes = new byte[bufferSize];
		int index = 0;
		byte[] buffer = new byte[bufferSize];
		int len = -1;
		while ((len = inputStream.read(buffer)) != -1) {
			if (index >= bytes.length - 1) {
				bytes = Arrays.copyOf(bytes, bytes.length * 2);
			}
			System.arraycopy(buffer, 0, bytes, index, len);
			index += len;
		}
		// 消除掉多扩容之后的无效数据的影响
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == 0 && i != bytes.length - 3 && bytes[i + 1] == 0 && bytes[i + 2] == 0) {
				bytes = Arrays.copyOf(bytes, i);
				break;
			}
		}
		inputStream.close();
		return bytes;
	}


	/**
	 * @param outputStream 输出流
	 * @param content      响应的信息
	 */
	public static void writeContent(OutputStream outputStream, String content) throws IOException {
		BufferedWriter bufferWriter = getBufferWriter(outputStream);
		bufferWriter.write(content);
		bufferWriter.flush();
		bufferWriter.close();
	}


	public static void writeBytes(OutputStream outputStream, byte[] bytes) throws IOException {
		outputStream.write(bytes);
		outputStream.flush();
	}


}
