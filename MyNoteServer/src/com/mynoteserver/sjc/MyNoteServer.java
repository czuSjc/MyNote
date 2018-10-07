package com.mynoteserver.sjc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;


public class MyNoteServer {

	/**如果客户端采取传序列化对象的方法对应的措施
	ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());	
	Note note = (Note)objIn.readObject();			
	System.out.println("KeyWord: " + note.getKeyWord() + "\n" + "note: " + note.getNote());	
	objIn.close();		
	**/			
	private BufferedReader reader;
	private ServerSocket server;
	private Socket socket;
	private int contol=0;//结束控制
	void getserver()
	{
		try{
			this.server=new ServerSocket(52112);
			System.out.println("服务器套接字已经创建成功。");
			while(true)//但客户端连接之后，启动获取客户端 信息方法，操作结束后关闭连接，循环等待下一次客户端请求
			{
				System.out.println("等待客户机连接");
				socket=server.accept();
				reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));//将socket的流传给reader
				this.getClientMessage();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void getClientMessage(){
		try{
				//获得客户端信息
				String clientMessage=reader.readLine();//客户端输出的流
				String[] s=clientMessage.split("#");//根据#分割数据，第一个必然是操作标识
				if(s[0].equals("a")){
					//a类型意味着写入Note类型数据
					Note note=new Note(s[1],s[2]);
					//数据库操作将Note写入数据库
					
					//测试代码
					System.out.println(note.getKeyWord()+"    "+note.getNote());
				}else if(s[0].equals("b")){
					//b类型意味着是服务器查询操作，给出一个关键字，期望服务器给出一个Note回去。
					Note note=new Note("测试关键词","测试笔记内容");
					//数据库查询操作s[1],并传回一个Note对象。
					ObjectOutputStream oops=new ObjectOutputStream(socket.getOutputStream());
					oops.writeObject(note);
					oops.flush();
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			if(reader!=null){
				reader.close();  //关闭reader流
			}
			if(socket!=null){
				socket.close();  //关闭socket流
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
			MyNoteServer server=new MyNoteServer();
			server.getserver();
	}

}
