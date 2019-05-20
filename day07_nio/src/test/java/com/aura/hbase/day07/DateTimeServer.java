package com.aura.hbase.day07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  服务端程序
 */
public class DateTimeServer {
	
	private static final int PORT = 9527;

	public static void main(String[] args) throws Exception {
		
		ServerSocket server = new ServerSocket(PORT);


		while(true){
			
			/**
			 * 如果这句代码返回，就证明有一个客户端发送了一个连接请求过来了。
			 * 
			 * 完成连接
			 */
			System.out.println("等待客户端的连接中.......");
			Socket socket = server.accept();
			
			System.out.println("已经有一个客户端连接上");
			new Thread(new TaskThread(socket)).start();
			/**
			 * 最终就两点坏处：
			 * 
			 * 1、本身server就是资源有限，不可能无限制的创建很多的资源。
			 * 
			 * 2、而且还有很多的线程都是闲置（而是处理等待状态中， 没有进行任何的业务处理）的
			 */
			
			
			
			/**
			 * 1、改变服务模式
			 */
		}
		
	}
}

class TaskThread implements Runnable{
	
	private Socket socket;
	
	public TaskThread(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		
		// 针对每一个连接进来的用户都进行对应的处理
		// 我们的处理方式：一个用户连接就创建一个线程进行单独处理
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			
			/**
			 * 读取请求数据
			 */
			inputStream = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			
			System.out.println("正在读取请求....qingkuaidian ");
			
			
			// 阻塞的方法
			// 当前这个方法在执行过程中，一定会等待这个方法的返回
			String request = br.readLine(); // I ： Input
			System.out.println("请求参数是：" + request);
			
			/**
			 * 处理请求得到结果
			 */
			String result = null;
			if(request.equals("getNow")){
				result = DateTimeUtil.getNow();
			}else{
				result = "error";
			}
			
			outputStream = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(outputStream, true);
			
			
			pw.println(result);    // O ： output
			System.out.println("server 返回结果给  client  成功");
			
		} catch (IOException e) {
			System.out.println("程序异常，自杀");
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
