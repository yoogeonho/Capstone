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
		super("잘못된 선택이 이뤄졌습니다. " );
		wrongChoice = choice;
	}
	
	public void showWrongChoice()
	{
		
		System.out.println(wrongChoice +"에 해당하는 선택은 존재하지 않습니다 . ");
	}
}

class PhoneInfo implements Serializable
{
String name; //이름
String phoneNumber; //전화번호


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
		return name.hashCode();    // 동일한지 해시값 반환
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


class PhoneBookManager{ //대학동기 클래스에 정보를 담고 있는 클래스를 상속함.
	
	/*final int MAX_CNT = 100; //최대 100명까지 포함.
	PhoneInfo[] infoStorage = new PhoneInfo[MAX_CNT]; //100명을 저장하기 위한 배열 선언
	int curCnt=0; //현재 배열의 위치*/
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
	
	private PhoneInfo readFriendInfo()   //친구
	{
		System.out.print("이름 : ");
		String name = MenuViewer.Keyboard.nextLine();
		System.out.print("전화번호 : " );
		String phone = MenuViewer.Keyboard.nextLine();
		return new PhoneInfo(name , phone);
		
		
	}
	
	private PhoneInfo readUnivFriendInfo() //대학동기
	{
		System.out.print("이름 : " );
		String name = MenuViewer.Keyboard.nextLine();
		System.out.print("전화번호 : " );
		String phone = MenuViewer.Keyboard.nextLine();
		System.out.print("전공 : ");
		String major = MenuViewer.Keyboard.nextLine();
		System.out.print("학년 : " );
		int year = MenuViewer.Keyboard.nextInt();
		MenuViewer.Keyboard.nextLine();
		
		return new PhoneUnivInfo(name,phone,major,year);
		
	}
	
	
	private PhoneInfo readCompanyFriendInfo()   //회사동료
	{
		System.out.print("이름 : " );
		String name = MenuViewer.Keyboard.nextLine();
		System.out.print("전화번호 : " );
		String phone = MenuViewer.Keyboard.nextLine();
		System.out.print("회사 : ");
		String company = MenuViewer.Keyboard.nextLine();
		
		return new PhoneCompanyInfo(name,phone,company);
		
	}
	
	public void inputData() throws MenuChoiceException{//데이터 입력 메소드
		
			System.out.println("데이터 입력을 시작합니다..");
			System.out.println("1. 일반 , 2. 대학, 3. 회사");
			System.out.print("선택>>");
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
	  System.out.println("데이터 입력이 완료되었습니다 . \n");
	  else
		  System.out.println("이미 저장된 데이터입니다. \n");
	}
	  
		public String searchData(String name)
		{ 
			                                    //데이터 검색 메소드
		
			PhoneInfo info = search(name);
			/*int dataIdx = search(Name);
			if(dataIdx < 0 ){
				System.out.println("해당하는 데이터가 존재하지 않습니다 . \n");
			}
			else
			{
				infoStorage[dataIdx].showPhoneInfo();
				System.out.println("데이터 검색이 완료되었습니다 . \n");
			}*/
			
			if(info==null)
			return null;
			else
				return info.toString();
		}
		
        public boolean deleteData(String name){
        	                                    //데이터 삭제 메소드
        	
        /*	int dataIdx = search(name);
        	if(dataIdx <0)
        	{
        		System.out.println("해당하는 데이터가 존재하지 않습니다. \n");
        	}
        	else
        	{
        		for(int idx =dataIdx; idx<(curCnt-1); idx++)
        			infoStorage[idx] = infoStorage[idx+1]; //앞으로 이동
        		curCnt--;
        		System.out.println("데이터 삭제가 완료되었습니다.\n");
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
		System.out.println("선택하세요...");
		System.out.println("1. 데이터 입력");
		//System.out.println("2. 데이터 검색");
		//System.out.println("3. 데이터 삭제");
		System.out.println("2. 프로그램 종료");
		System.out.print("선택 : ");
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
		 textArea.append("해당하는 데이터가 존재하지 않습니다 . \n");
	 }
	 else
	 {
		 textArea.append("찾으시는 정보를 알려드립니다 . \n");
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
			 textArea.append("데이터 삭제를 완료하였습니다. \n");
		 }
		 else
		 
			 textArea.append("해당하는 데이터가 존재하지 않습니다 . \n");
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
				 
				 MenuViewer.Keyboard.nextLine(); //Enter가 입력될 때까지 기다리며 사용자의 입력 기회가 없어지는 것을 방지해주는 역할을 함.
				 
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
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				 return;                  //break가 아닌 return값을 아무것도 설정하지 않아야 종료된다.	
				 }
			 }
			catch(MenuChoiceException e){
				e.showWrongChoice();
				System.out.println("메뉴 선택을 처음부터 다시 진행합니다.\n");
			}
		 }
	 }
}
 