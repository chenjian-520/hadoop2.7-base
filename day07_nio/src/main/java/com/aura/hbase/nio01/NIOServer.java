package com.aura.hbase.nio01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.aura.hbase.day07.DateTimeUtil;

/**
 * NIO网络编程的 服务端
 */
public class NIOServer {

	public static void main(String[] args) throws Exception {

		/**
		 * 得到一个服务对象
		 */
		NIOServer server = new NIOServer();

		/**
		 * 初始化服务， 启动服务器
		 */
		server.init();

		/**
		 * 启动监听: 其实就是让  selector不停的轮询
		 * 
		 * 如果轮到有准备就绪的channel
		 * 要把这个channel拿出来进行处理（先进行和完成IO操作，然后执行接下来的任务处理）
		 * 
		 * 业务： DateTimeUtil.getNow();
		 */
		server.listen();
	}
	
	private Selector selector = null;

	/**
	 * 初始化服务器
	 */
	private void init() {
		try {
			// 多路复用器
			selector = Selector.open();
			
			// 获取服务对象  Channel
			ServerSocketChannel server  = ServerSocketChannel.open();

			// 把这个channel设置成非阻塞的。
			server.configureBlocking(false);
			
			server.bind(new InetSocketAddress("localhost", 9977));
			
			// OP_ACCEPT  OP_CONNECT  OP_READ   OP_WRITE
			// 等待客户端的连接
			// SelectionKey就包含了两个非常重要的信息： Channel  Event
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("1 ------ server has started ....... ");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

	/**
	 * 不断的执行轮询找出准备就绪的  channel
	 * 然后交给 handleKey方法对这个chanell进行处理
	 * @throws InterruptedException 
	 */
	private void listen() throws InterruptedException {
		
		while(true){
			try {
				
				// 这句代码就表示每隔1s钟轮询一次，查看是否有IO已经准备就绪的连接 
				// 返回值就是准备就绪的连接的个数
				int select = selector.select(3000);
				System.out.println("2 ------ 每隔3s钟执行了一次轮询" + select);
				
				if(select > 0){
					
					/**
					 * 这个key的集合，其实表示的是准备就绪的了的channel的注册事件的集合
					 * 选出准备就绪的已经能拿出来进行处理的channel
					 *
					 * selector中注册的总事件的个数 一定要 >= set的大小
					 */
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					
					/**
					 * 增强for循环和迭代器的区别就是在于   迭代器可以删除元素
					 */
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while(iterator.hasNext()){
						SelectionKey key = iterator.next();
						
						iterator.remove();
						/**
						 * 非常重要的事情： 处理这个Key   这个key中包含了是哪个channel的什么事件响应
						 */
						handleKey(key);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 
	 * 针对每一个channel的任意事件都需要处理
	 */
	private void handleKey(SelectionKey key) throws InterruptedException {

		// 判断还是否合法是否有效
		if(key.isValid()){
			
			if(key.isReadable()){
				
				// 这个 clientChannel 就是  serverChannel.accept();这句代码的返回对象
				SocketChannel clientChannel = (SocketChannel)key.channel();
				
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				// clientChannel这个通道中的数据会被读出来写入到buffer中
				try {
					
					// 获取到了客户端发送过来的请求数据
					int length = clientChannel.read(buffer);
					String request = new String(buffer.array(), 0, length);
					System.out.println("7 ------ server received client's request" + request);
					
					// 拿到了数据之后， 就应该按照这个请求进行处理，得到结果
					String response = handleBusiness(request);
					
					// 服务端就必须要把数据写出到 客户端
					// 谁去写？  写什么？ 
					writeDataToClient(clientChannel, response, buffer);
					
					/**
					 * 当上面这句代码执行成功之后，那么服务端就开始继续往下执行
					 * 
					 * 客户端就有可能很快就接收到了数据，就也开始往下继续执行。
					 * 
					 * 服务端和客户端有可能在同时执行
					 */
					
				} catch (IOException e) {
					System.out.println("读取数据异常");
				}
				
				Thread.sleep(3000);
				
				/**
				 * 代码执行到这个地方以后有两种选择：
				 * 
				 * 1、如果这个连接只用来发送一次请求，那么当server把请求处理完毕，
				 * 把数据响应给client之后，就应该吧这个连接关闭
				 * 
				 * 2、如果这个连接需要和服务端进行连续的交互，那么这个连接不能关闭
				 */
				if(key != null){
					key.cancel();
					if(key.channel() != null){
						try {
							key.channel().close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					System.out.println("10 ------ 对应的客户端的连接已经断开。！！！");
					
//					return;
				}
			}
			
			// 表示可以建立连接
			else if(key.isAcceptable()){
				
				System.out.println("4 ------ server starting connect client ");
				
				// key.channel返回的结果在我们的程序中，只有可能是一下两种情况：
				// ServerSocketChannel  和   socketChannel
				ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
				
				// 这一句代码就相当于 服务端 和 客户端建立了连接
				try {
					// 表示服务端允许客户端建立了连接。如果这句话执行成功，就表示连接建立成功
					// clientChannel 这个对象就是 客户端发起了连接请求之后，
					// 和服务器建立了连接之后存在于服务端的一个Socket对象
					SocketChannel clientChannel = serverChannel.accept();
					clientChannel.configureBlocking(false);
					
					// 注册OP_READ 事件， 等到客户端发送请求过来
					clientChannel.register(selector, SelectionKey.OP_READ);
					
				} catch (IOException e) {
					
					System.out.println("建立连接失败");
				}
			}
			
		}else{
			System.out.println("对方断开连接，或者连接异常");
		}
	}

	/**
	 * @param clientChannel  通道对象
	 * @param response   响应结果
	 * 
	 * 目标： 通过这个通道把结果数据写出到  client
	 */
	private void writeDataToClient(SocketChannel clientChannel, String response, ByteBuffer buffer) {

		// 你在构造这个buffer的时候，是处于写模式下的。
//		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(response.getBytes());

		// 把buffer由写模式 切换到 读模式
		buffer.flip();
		
		// 把 buffer 中的 数据写出到 客户端
		try {
			clientChannel.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("9 ------ server send response to client success !!!");
	}

	// 模拟在处理业务
	private String handleBusiness(String request) {
		String result = "";
		if(request.equals("getNow")){
			result = DateTimeUtil.getNow();
		}else if(request.equals("getServerName")){
			result = DateTimeUtil.getServerName();
		}else{
			result = "xxxxxx";
		}
		System.out.println("8 ------  服务器处理业务成功");
		return result;
	}
}