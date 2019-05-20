package com.aura.hbase.day07;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *  客户端程序
 */
public class DateTimeClient {

	public static void main(String[] args) {
		
		
		try {
			Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
			
			handleRequest(socket);
			
		} catch (Exception e) {
			System.out.println("连接服务器异常");
			
		}
	}

	private static void handleRequest(Socket socket) {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			
			outputStream = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(outputStream, true);
			
			
			// 模拟聊微信去了。
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			/**
			 * 先发送请求到服务器
			 */
			pw.println("getNow");
			System.out.println("发送请求成功");
			
			
			/**
			 * 处理接收的结果
			 */
			String result = br.readLine();
			System.out.println("接收服务器返回回来的处理结果成功： " + result);
			
			
		} catch (IOException e) {
			System.out.println("获取输入输出流进行数据读写异常");
		}
		
		
		try {
			outputStream.close();
			inputStream.close();
			socket.close();
		} catch (IOException e) {
			System.exit(0);
		}
		
	}
}
