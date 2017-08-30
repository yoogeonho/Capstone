package yghproject;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

interface INIT_MENU
{
	int INPUT = 1, EXIT =2;
}


interface INPUT_SELECT
{
	int NORMAL=1, UNIV=2, COMPANY =3;
}

class MenuChoiceException extends Exception
{
	int wrongChoice;
	
	public MenuChoiceException(int choice)
	{
		super("�߸��� ������ �̷������ϴ�. " );
		wrongChoice = choice;
	}
	
	public void showWrongChoice()
	{
		
		System.out.println(wrongChoice +"�� �ش��ϴ� ������ �������� �ʽ��ϴ� . ");
	}
}

class PhoneInfo implements Serializable
{
String name; //�̸�
String phoneNumber; //��ȭ��ȣ


	public PhoneInfo(String name, String num){
		this.name = name;
		phoneNumber = num;
	}
	
	public void showPhoneInfo(){
		
		System.out.println("name : " + name);
		System.out.println("phone : " + phoneNumber);
	}
	
	public String toString()
	{
		return "name : "+name+'\n'+"phone : "+phoneNumber+'\n';
	}
	
	public int hashCode(){
		return name.hashCode();    // �������� �ؽð� ��ȯ
	}
	
	public boolean equals(Object obj)
	{
		PhoneInfo cmp = (PhoneInfo)obj;
		if(name.compareTo(cmp.name)==0)
			return true;
		else
			return false;
	}
}


class PhoneUnivInfo extends PhoneInfo{
	
	String major;
	int year;
	
	public PhoneUnivInfo(String name , String num , String major ,int year){
		super(name,num);
		this.major = major;
		this.year = year;
	}
	
	public void showPhoneInfo(){
		super.showPhoneInfo();
		System.out.println("major : " + major);
		System.out.println("year : " + year);
	}
}


class PhoneCompanyInfo extends PhoneInfo{
	String company;
	public PhoneCompanyInfo(String name , String num, String company){
		super(name,num);
		this.company = company;
	}
	
	public void showPhoneInfo(){
		super.showPhoneInfo();
		System.out.println("company : " + company);
	}
}


class PhoneBookManager{ //���е��� Ŭ������ ������ ��� �ִ� Ŭ������ �����.
	
	/*final int MAX_CNT = 100; //�ִ� 100����� ����.
	PhoneInfo[] infoStorage = new PhoneInfo[MAX_CNT]; //100���� �����ϱ� ���� �迭 ����
	int curCnt=0; //���� �迭�� ��ġ*/
	private final File dataFile = new File("PhoneBook.dat");
	
	HashSet<PhoneInfo> infoStorage = new HashSet<PhoneInfo>();

	static PhoneBookManager inst = null;
	public static PhoneBookManager createManagerInst(){
		if(inst == null)
			inst = new PhoneBookManager();
		
		return inst;
	}
	
	private PhoneBookManager()
	{
		
		readFromFile();
	}
	
	private PhoneInfo readFriendInfo()   //ģ��
	{
		System.out.print("�̸� : ");
		String name = MenuViewer.Keyboard.nextLine();
		System.out.print("��ȭ��ȣ : " );
		String phone = MenuViewer.Keyboard.nextLine();
		return new PhoneInfo(name , phone);
		
		
	}
	
	private PhoneInfo readUnivFriendInfo() //���е���
	{
		System.out.print("�̸� : " );
		String name = MenuViewer.Keyboard.nextLine();
		System.out.print("��ȭ��ȣ : " );
		String phone = MenuViewer.Keyboard.nextLine();
		System.out.print("���� : ");
		String major = MenuViewer.Keyboard.nextLine();
		System.out.print("�г� : " );
		int year = MenuViewer.Keyboard.nextInt();
		MenuViewer.Keyboard.nextLine();
		
		return new PhoneUnivInfo(name,phone,major,year);
		
	}
	
	
	private PhoneInfo readCompanyFriendInfo()   //ȸ�絿��
	{
		System.out.print("�̸� : " );
		String name = MenuViewer.Keyboard.nextLine();
		System.out.print("��ȭ��ȣ : " );
		String phone = MenuViewer.Keyboard.nextLine();
		System.out.print("ȸ�� : ");
		String company = MenuViewer.Keyboard.nextLine();
		
		return new PhoneCompanyInfo(name,phone,company);
		
	}
	
	public void inputData() throws MenuChoiceException{//������ �Է� �޼ҵ�
		
			System.out.println("������ �Է��� �����մϴ�..");
			System.out.println("1. �Ϲ� , 2. ����, 3. ȸ��");
			System.out.print("����>>");
			int choice = MenuViewer.Keyboard.nextInt();
			MenuViewer.Keyboard.nextLine();
			PhoneInfo info = null;
			
			if(choice < INPUT_SELECT.NORMAL || choice>INPUT_SELECT.COMPANY)
				throw new MenuChoiceException(choice);
	
	  switch(choice)
	  {
	  
	  case INPUT_SELECT.NORMAL:
		  info = readFriendInfo();
		  break;
	  
	  case INPUT_SELECT.UNIV:
		  info = readUnivFriendInfo();
		  break;
	  
	  case INPUT_SELECT.COMPANY:
		  info = readCompanyFriendInfo();
		  break;
	  }
	//  infoStorage[curCnt++] = info;
	  boolean isAdded = infoStorage.add(info);
	  if(isAdded==true)
	  System.out.println("������ �Է��� �Ϸ�Ǿ����ϴ� . \n");
	  else
		  System.out.println("�̹� ����� �������Դϴ�. \n");
	}
	  
		public String searchData(String name)
		{ 
			                                    //������ �˻� �޼ҵ�
		
			PhoneInfo info = search(name);
			/*int dataIdx = search(Name);
			if(dataIdx < 0 ){
				System.out.println("�ش��ϴ� �����Ͱ� �������� �ʽ��ϴ� . \n");
			}
			else
			{
				infoStorage[dataIdx].showPhoneInfo();
				System.out.println("������ �˻��� �Ϸ�Ǿ����ϴ� . \n");
			}*/
			
			if(info==null)
			return null;
			else
				return info.toString();
		}
		
        public boolean deleteData(String name){
        	                                    //������ ���� �޼ҵ�
        	
        /*	int dataIdx = search(name);
        	if(dataIdx <0)
        	{
        		System.out.println("�ش��ϴ� �����Ͱ� �������� �ʽ��ϴ�. \n");
        	}
        	else
        	{
        		for(int idx =dataIdx; idx<(curCnt-1); idx++)
        			infoStorage[idx] = infoStorage[idx+1]; //������ �̵�
        		curCnt--;
        		System.out.println("������ ������ �Ϸ�Ǿ����ϴ�.\n");
        	}*/
        	Iterator<PhoneInfo> itr = infoStorage.iterator();
        	while(itr.hasNext())
        	{
        		PhoneInfo curInfo = itr.next();
        		if(name.compareTo(curInfo.name)==0){
        			itr.remove();
  
        		return true;
        		}
        	}
        	return false;
        }
        
        private PhoneInfo search(String name)
        {
        	Iterator<PhoneInfo> itr = infoStorage.iterator();
        	while(itr.hasNext())
        	{
        		PhoneInfo curInfo = itr.next();
        		if(name.compareTo(curInfo.name)==0)
        			return curInfo;
        	}
        	return null;
        }
        
        public void storeToFile()
        {
        	try{
        		FileOutputStream file = new FileOutputStream(dataFile);
        		ObjectOutputStream out = new ObjectOutputStream(file);
        		
        		Iterator<PhoneInfo> itr = infoStorage.iterator();
        		while(itr.hasNext())
        			out.writeObject(itr.next());
        		out.close();
        	}
        	
        	catch(IOException e)
        	{
        		e.printStackTrace();
        	}
        }
        
        public void readFromFile()
        {
        	if(dataFile.exists() == false)
        		return;
        	try
        	{
        		FileInputStream file = new FileInputStream(dataFile);
        		ObjectInputStream in = new ObjectInputStream(file);
        		
        		while(true)
        		{
        			PhoneInfo info = (PhoneInfo)in.readObject();
        			if(info==null)
        				break;
        			infoStorage.add(info);
        		}
        		in.close();
        }
        	catch(IOException e)
        	{
        		return;
        	}
        	
        	catch(ClassNotFoundException e)
        	{
        		return;
        	}
        }
}

class MenuViewer
{
	public static Scanner Keyboard =new Scanner(System.in);
	
	public static void showMenu()
	{
		System.out.println("�����ϼ���...");
		System.out.println("1. ������ �Է�");
		//System.out.println("2. ������ �˻�");
		//System.out.println("3. ������ ����");
		System.out.println("2. ���α׷� ����");
		System.out.print("���� : ");
	}
}


class SearchEventHandler implements ActionListener{
 JTextField searchField;
 JTextArea textArea;
 
 public SearchEventHandler(JTextField field, JTextArea area)
 {
	 searchField =field;
	 textArea = area;
 }
 
 public void actionPerformed(ActionEvent e)
 {
	 String name = searchField.getText();
	 PhoneBookManager manager = PhoneBookManager.createManagerInst();
	 String srchResult = manager.searchData(name);
	 if(srchResult == null)
	 {
		 textArea.append("�ش��ϴ� �����Ͱ� �������� �ʽ��ϴ� . \n");
	 }
	 else
	 {
		 textArea.append("ã���ô� ������ �˷��帳�ϴ� . \n");
		 textArea.append(srchResult);
		 textArea.append("\n");
	 }
 }
}



class DeleteEventHandler implements ActionListener{
	 JTextField delField;
	 JTextArea textArea;
	 
	 public DeleteEventHandler(JTextField field, JTextArea area)
	 {
		 delField =field;
		 textArea = area;
	 }
	 
	 public void actionPerformed(ActionEvent e)
	 {
		 String name = delField.getText();
		 PhoneBookManager manager = PhoneBookManager.createManagerInst();
		 boolean isDeleted = manager.deleteData(name);
		 
		 
		 if(isDeleted)
		 {
			 textArea.append("������ ������ �Ϸ��Ͽ����ϴ�. \n");
		 }
		 else
		 
			 textArea.append("�ش��ϴ� �����Ͱ� �������� �ʽ��ϴ� . \n");
		 }
	 }
	 
	 class SearchDelFrame extends JFrame
	 {
		 JTextField srchField = new JTextField(15);
		 JButton srchBtn = new JButton("SEARCH");
		 
		 JTextField delField = new JTextField(15);
		 JButton delBtn = new JButton("DEL");
		 
		 JTextArea textArea = new JTextArea(20,15);
		 
		 public SearchDelFrame()
		 {
			 
			 super();
			 setBounds(100, 200, 330, 450);
			 setLayout(new BorderLayout());
			 Border border = BorderFactory.createEtchedBorder();
			 
			 Border srchBorder = BorderFactory.createTitledBorder(border,"Search");
			 JPanel srchPanel =new JPanel();
			 srchPanel.setBorder(srchBorder);
			 srchPanel.setLayout(new FlowLayout());
			 srchPanel.add(srchField);
			 srchPanel.add(srchBtn);
			 
			 Border delBorder = BorderFactory.createTitledBorder(border,"Delete");
			 JPanel delPanel =new JPanel();
			 delPanel.setBorder(delBorder);
			 delPanel.setLayout(new FlowLayout());
			 delPanel.add(delField);
			 delPanel.add(delBtn);
			 
			 JScrollPane scrollTextArea =new JScrollPane(textArea);
			 Border textBorder = BorderFactory.createTitledBorder(border,"Information Board");
			 scrollTextArea.setBorder(textBorder);
			 
			 
			 add(srchPanel, BorderLayout.NORTH);
			 
			 add(delPanel, BorderLayout.SOUTH);
			 
			 add(scrollTextArea, BorderLayout.CENTER);
			 
			 srchBtn.addActionListener(new SearchEventHandler(srchField, textArea));
			 delBtn.addActionListener(new DeleteEventHandler(delField,textArea));
			 
			 setVisible(true);
			 setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		 
	 }
}
	 
class PhoneInfoBook 
{
		
		 public static void main(String[] args) {
			 
			int choice;
			PhoneBookManager manager =  PhoneBookManager.createManagerInst();
			SearchDelFrame winFrame = new SearchDelFrame();
			 while(true){
				 
			try{	 
				 MenuViewer.showMenu();
				 choice=MenuViewer.Keyboard.nextInt();
				 
				 MenuViewer.Keyboard.nextLine(); //Enter�� �Էµ� ������ ��ٸ��� ������� �Է� ��ȸ�� �������� ���� �������ִ� ������ ��.
				 
				 if(choice<INIT_MENU.INPUT || choice>INIT_MENU.EXIT)
					 throw new MenuChoiceException(choice);
				 
			 switch(choice){
				case INIT_MENU.INPUT:
				manager.inputData();
				break;
				
			/*	case INIT_MENU.SEARCH:
				manager.searchData();	 	
				break;
					 
				case INIT_MENU.DELETE:
				manager.deleteData();	 
				break;*/
				
				case INIT_MENU.EXIT:
				manager.storeToFile();
				System.out.println("���α׷��� �����մϴ�.");
				System.exit(0);
				 return;                  //break�� �ƴ� return���� �ƹ��͵� �������� �ʾƾ� ����ȴ�.	
				 }
			 }
			catch(MenuChoiceException e){
				e.showWrongChoice();
				System.out.println("�޴� ������ ó������ �ٽ� �����մϴ�.\n");
			}
		 }
	 }
}
 