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

	/**����ͻ��˲�ȡ�����л�����ķ�����Ӧ�Ĵ�ʩ
	ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());	
	Note note = (Note)objIn.readObject();			
	System.out.println("KeyWord: " + note.getKeyWord() + "\n" + "note: " + note.getNote());	
	objIn.close();		
	**/			
	private BufferedReader reader;
	private ServerSocket server;
	private Socket socket;
	private int contol=0;//��������
	void getserver()
	{
		try{
			this.server=new ServerSocket(52112);
			System.out.println("�������׽����Ѿ������ɹ���");
			while(true)//���ͻ�������֮��������ȡ�ͻ��� ��Ϣ����������������ر����ӣ�ѭ���ȴ���һ�οͻ�������
			{
				System.out.println("�ȴ��ͻ�������");
				socket=server.accept();
				reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));//��socket��������reader
				this.getClientMessage();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void getClientMessage(){
		try{
				//��ÿͻ�����Ϣ
				String clientMessage=reader.readLine();//�ͻ����������
				String[] s=clientMessage.split("#");//����#�ָ����ݣ���һ����Ȼ�ǲ�����ʶ
				if(s[0].equals("a")){
					//a������ζ��д��Note��������
					Note note=new Note(s[1],s[2]);
					//���ݿ������Noteд�����ݿ�
					
					//���Դ���
					System.out.println(note.getKeyWord()+"    "+note.getNote());
				}else if(s[0].equals("b")){
					//b������ζ���Ƿ�������ѯ����������һ���ؼ��֣���������������һ��Note��ȥ��
					Note note=new Note("���Թؼ���","���Աʼ�����");
					//���ݿ��ѯ����s[1],������һ��Note����
					ObjectOutputStream oops=new ObjectOutputStream(socket.getOutputStream());
					oops.writeObject(note);
					oops.flush();
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			if(reader!=null){
				reader.close();  //�ر�reader��
			}
			if(socket!=null){
				socket.close();  //�ر�socket��
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
			MyNoteServer server=new MyNoteServer();
			server.getserver();
	}

}
