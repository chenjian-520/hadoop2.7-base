package com.aura.hbase.nio01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * NIO网络编程的 客户端
 */
public class NIOClient {

	public static void main(String[] args) {

		/**
		 * 得到一个客户端对象
		 */
		NIOClient client = new NIOClient();

		/**
		 * 初始化服务， 启动客户端，发送了请求给服务器
		 */
		client.init();

		/**
		 * 启动监听: 其实就是让 selector不停的轮询
		 * 
		 * 如果轮到有准备就绪的channel 要把这个channel拿出来进行处理（先进行和完成IO操作，然后执行接下来的任务处理）
		 * 
		 * 业务： DateTimeUtil.getNow();
		 */
		client.listen();

	}

	private Selector selector = null;

	private void init() {

		// 核心：获取一个selector和获取一个 SocketChannel

		try {
			selector = Selector.open();
			SocketChannel clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			clientChannel.register(selector, SelectionKey.OP_CONNECT);
			clientChannel.connect(new InetSocketAddress("localhost", 9977));

			System.out.println("3 ------ 客户端发送了建立连接的请求");

		} catch (IOException e) {
			System.out.println("客户端启动异常");
		}

	}

	private void listen() {

		while (true) {

			if(!flag){
				
				try {
					int length = selector.select(2000);

					if (length > 0) {

						Set<SelectionKey> selectedKeys = selector.selectedKeys();
						Iterator<SelectionKey> iterator = selectedKeys.iterator();
						while (iterator.hasNext()) {
							SelectionKey key = iterator.next();
							iterator.remove();
							handleKey(key);
						}
					}
				} catch (IOException e) {
					System.out.println("服务器的多路复用器出现问题");
					System.exit(1);
				}
			}else{
				System.out.println("11 ------ 客户端断开连接  并且 停止客户端程序");
//				break;
				System.exit(1);
			}
		}
	}

	private void handleKey(SelectionKey key) {

		if (key.isValid()) {

			SocketChannel clientChannel = (SocketChannel) key.channel();

			if (key.isConnectable()) {

				boolean isSuccess = false;
				try {
					isSuccess = clientChannel.finishConnect();

					System.out.println("5 ------ client connect serve success !!!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (isSuccess) {

					String[] requestStrArray = new String[] { "getNow", "getServerName", "abcde" };
					Random r = new Random();
					String request = requestStrArray[r.nextInt(requestStrArray.length)];
					System.out.println("6 ------ client  发送请求给  server: " + request);

					/**
					 * 第二，要等待服务器发送响应结果过来
					 */
					try {
						clientChannel.register(selector, SelectionKey.OP_READ);
					} catch (ClosedChannelException e) {
						e.printStackTrace();
					}

					/**
					 * 第一，先发送请求
					 */
					// 这句代码就是在建立了连接之后， 给服务器发送请求
					writeDataToServer(clientChannel, request);

				} else {

					// 如果代码进入到这个分支，就证明， 客户端和服务端建立连接没有完成

					/**
					 * 如果没有建立连接成功，那么有两种方式； 1、重试 2、放弃
					 */
					System.out.println("服务器拒绝连接，我已经放弃");
					System.exit(1);
				}

			}

			// 读取数据
			if (key.isReadable()) {

				// 处理服务器返回来的结果
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				try {
					int length = clientChannel.read(buffer);

					String response = new String(buffer.array(), 0, length);

					System.out.println("10 ------ client receive server's response : " + response);

				} catch (IOException e) {
					e.printStackTrace();
				}

				if (key != null) {
					key.cancel();
					if (key.channel() != null) {
						try {
							key.channel().close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					flag = true;
				}
			}
		}
	}

	// 为flag为true的时候表示 select停止轮询
	// 为什么flag要用  volatile 去修饰？
	// 保证可见性不保证事务性。  就是为了在并发场景中，快速的共享数据。
	private volatile boolean flag = false;

	private void writeDataToServer(SocketChannel clientChannel, String request) {

		// 你在构造这个buffer的时候，是处于写模式下的。
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(request.getBytes());

		// 把buffer由写模式 切换到 读模式
		buffer.flip();

		// 把 buffer 中的 数据 发送到 服务端
		try {
			clientChannel.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("client send request to server success !!!  === " + request);
	}
}
