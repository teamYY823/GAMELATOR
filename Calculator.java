package caculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Calculator {
	public static void main(String args[]){
		caculator.MainWindow mainWindow = new MainWindow();
	}
}

class MainWindow extends JFrame{
	//private static final long serialVersionUID = -2080463030646428155L;
	private boolean flag = false;
	private String s,J1temp = "",J4temp = "";
	private JTextArea J1,J4; //多行文本
	private Point point;//记录父窗体在屏幕的坐标
	private int width,height;//父窗体的宽度和高度
	
	public MainWindow(){
		super("Gamelator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";//设置界面外观
		
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		setFocusable(true);
		setLayout(new GridLayout(1,2,2,0));
		
		JPanel workspace = new JPanel();
		add(workspace);
		JPanel historyspace = new JPanel();
		historyspace.setBackground(Color.white);
		add(historyspace);
		
		JMenuBar bar = new JMenuBar();
		bar.setBackground(Color.white);
		setJMenuBar(bar);
		JMenu fileMenu = new JMenu("选项");
		fileMenu.setFont(new Font("微软雅黑",Font.PLAIN,15));
		bar.add(fileMenu);
		JMenuItem helpItem = new JMenuItem("使用须知");
		JMenuItem exitItem = new JMenuItem("退出");
		JMenuItem aboutItem = new JMenuItem("Game");
		fileMenu.add(exitItem);
		fileMenu.add(helpItem);
		fileMenu.add(aboutItem);
		
		JDialog dialog = new JDialog();
		dialog.setModal(true);//设置为模态窗口
		dialog.setTitle("使用须知");
		dialog.setSize(450, 500);
		JTextArea J0 = new JTextArea("* 本程序是一个计算器,可以实现windows自带的科学计算器上的大多数基本运算\n\n"
				+ "* 关于±、√、1/x的使用:请在输入完一个数字时,点击相应按钮,会直接生成相应表达式\n\n"
				+ "* 程序阻断了输入阶段的绝大多数非法输入,非法输入时,会没有响应\n\n"
				+ "* 点击“没用别点”键会正常显示，但对结果无影响XD\n\n"
				+ "* 本程序未设置寄存器寄存中间结果，故无法利用等号后结果继续计算，无法进行加减乘除、乘方外的连续计算");
		J0.setFont(new Font("微软雅黑",Font.PLAIN,20));
		J0.setEditable(false);
		J0.setLineWrap(true);//自动换行
		dialog.add(J0);
	
		helpItem.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
					point = MainWindow.this.getLocation();//获得主窗体在屏幕的坐标
					width = MainWindow.this.getWidth();
					height = MainWindow.this.getHeight();
					dialog.setLocation(
					        point.x + width/2 - dialog.getWidth()/2, 
					        point.y + height/2 - dialog.getHeight()/2);
					dialog.setVisible(true);
				}
			}
		);
		
		aboutItem.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
					new About();
				}
			}
		);
		
		exitItem.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
					setVisible(false);
					System.exit(0);
				}
			}
		);
		
		workspace.setLayout(new BorderLayout());
		//J1是输入显示区
		J1 = new JTextArea(8,10);
		J1.setFont(new Font("微软雅黑",Font.BOLD,20));
		J1.setVisible(true);
		J1.setEditable(false);
		J1.setLineWrap(true);//自动换行
		J1.setText("");
		workspace.add(J1,BorderLayout.NORTH);
		//J2是按键
		JPanel J2 = new JPanel();
		J2.setLayout(new GridLayout(6,4));
		
		ButtonMonitor buttonMonitor = new ButtonMonitor();
		JButton[] button = new JButton[30];
		button[0] = new JButton("ln");
		button[1] = new JButton("log");
		button[2] = new JButton("√");
		button[3] = new JButton("C");
		button[4] = new JButton("CE");
		button[5] = new JButton("x^");
		button[6] = new JButton("1/x"); 
		button[7] = new JButton("(");
		button[8] = new JButton(")");
		button[9] = new JButton("没");
		button[10] = new JButton("7");
		button[11] = new JButton("8");
		button[12] = new JButton("9");
		button[13] = new JButton("/");
		button[14]= new JButton("用");
		button[15] = new JButton("4");
		button[16] = new JButton("5");
		button[17] = new JButton("6");
		button[18] = new JButton("*");
		button[19] = new JButton("别");
		button[20] = new JButton("1");
		button[21] = new JButton("2");
		button[22] = new JButton("3");
		button[23] = new JButton("-");
		button[24] = new JButton("点");
		button[25] = new JButton("±");
		button[26] = new JButton("0");
		button[27] = new JButton(".");
		button[28] = new JButton("+");
		button[29] = new JButton("=");
		for(int i = 0;i < 30;i++)
		{
			button[i].addActionListener(buttonMonitor);
			button[i].setFont(new Font("微软雅黑",Font.PLAIN,20));
			J2.add(button[i]);
		}
		workspace.add(J2);
		
		historyspace.setLayout(new BorderLayout());
		JLabel J3 = new JLabel("历史记录");
		J3.setFont(new Font("微软雅黑",Font.BOLD,13));
		historyspace.add(J3,BorderLayout.NORTH);
		//J4是显示区
		J4 = new JTextArea();
		J4.setFont(new Font("宋体",Font.BOLD,20));
		J4.setEditable(false);
		J4.setVisible(true);
		J4.setLineWrap(true);//自动换行
		J4.setText("");
		JScrollPane J4Scroll = new JScrollPane(J4);
		historyspace.add(J4Scroll,BorderLayout.CENTER);
		
		JButton J5 = new JButton("清除所有历史记录");
		J5.setFont(new Font("微软雅黑",Font.BOLD,13));
		J5.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent e){
				J4.setText("");
			}
		}
		);
		historyspace.add(J5,BorderLayout.SOUTH);
		
		setSize(750,600);
		setMinimumSize(new Dimension(400,300));
		setLocationRelativeTo(null);//显示在屏幕中央
		setVisible(true);
		point = MainWindow.this.getLocation();//获得主窗体在屏幕的坐标
		width = MainWindow.this.getWidth();
		height = MainWindow.this.getHeight();
		dialog.setLocation(
		        point.x + width/2 - dialog.getWidth()/2, 
		        point.y + height/2 - dialog.getHeight()/2);
		dialog.setVisible(true);
	}

	//括号匹配函数
	boolean Match(String s){
		CharStack S = new CharStack();
		int ptr = 0;
		while (ptr != s.length())
		{
			if (s.charAt(ptr) == '(')
			{
				S.Push(s.charAt(ptr));
				ptr++;
			}
			else if (s.charAt(ptr) == ')')
			{
				if (S.EmptyStack())
				{
					return false;
				}
				else
				{
					S.Pop();
					ptr++;
				}
			}
			else
			{
				ptr++;
			}
		}
		if (S.EmptyStack())  
			return true; 
		else
			return false;
	}
	
	//按钮监听器,要考虑多种容错处理
	class ButtonMonitor implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JButton J = (JButton)e.getSource();//获取按钮输入
			int ptr = 0;
			boolean flag3 = false;
			double t;
			String res = "";
			if(flag) 
			{
				J1.setText("");
				flag = false;
			}
						
			if(J.getText() == "CE") 
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else 
				{
					J1temp = J1temp.substring(0,J1temp.length()-1);
					J1.setText(J1temp);
				}
			}
			else if(J.getText() == "=") 
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					if(J1temp.charAt(J1temp.length()-1) == '/'||
					   J1temp.charAt(J1temp.length()-1) == '*'||
					   J1temp.charAt(J1temp.length()-1) == '-'||
					   J1temp.charAt(J1temp.length()-1) == '+'||
					   J1temp.charAt(J1temp.length()-1) == '.'||
					   J1temp.charAt(J1temp.length()-1) == '(')
					{
						JOptionPane.showMessageDialog(null, "表达式存在逻辑错误！", "Error",JOptionPane.WARNING_MESSAGE);
						J4temp = J4.getText();
						J4temp = J4temp + "\n" + J1.getText() + " = " + "ERROR!";
						J4.setText(J4temp);
					}
					else
					{
						J4temp = J4.getText();
						J4temp = J4temp + "\n" + J1.getText() + " = ";
						J4.setText(J4temp);
						s = J1.getText();
						s += '=';//'='为s结尾标志
						if(!Match(s))
						{
							JOptionPane.showMessageDialog(null, "表达式括号不匹配！", "Error",JOptionPane.WARNING_MESSAGE);
							J4temp = J4.getText();
							J4temp += "ERROR!";
							J4.setText(J4temp);
						}
						else
						{
							res = new Expression(s).EvaluateExpression();//每次按下=把计算结果显示在J1区域并且把添加一条历史记录到J4区域
							if(res == "ERROR!")
							{
								J1temp = J1.getText();
								J1temp += " = " + res;
								J1.setText(J1temp);
								J4temp = J4.getText();
								J4temp += res;
								J4.setText(J4temp);
								flag = true;
							}
							else
							{
								//res是string型
								t = Double.valueOf(res);
								if( res.charAt(res.length()-1) == '0' && res.charAt(res.length()-2) == '.')
								{
									J1temp = J1.getText();
									J1temp += " = " + (int)t;
									J1.setText(J1temp);
									J4temp = J4.getText();
									J4temp += (int)t;
									J4.setText(J4temp);
								}
								else
								{
									J1temp = J1.getText();
									J1temp += " = " + t;
									J1.setText(J1temp);
									J4temp = J4.getText();
									J4temp += t;
									J4.setText(J4temp);
								}
								flag = true;
								s = "";
							}
						}
					}
				}
			}
			else if(J.getText() == "±")//按下的是负号
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					ptr = J1temp.length()-1;
					if(J1temp.charAt(ptr)=='('||
					   J1temp.charAt(ptr)==')'||
					   J1temp.charAt(ptr)=='/'||
					   J1temp.charAt(ptr)=='*'||
					   J1temp.charAt(ptr)=='-'||
					   J1temp.charAt(ptr)=='+'||
					   J1temp.charAt(ptr)=='.'){}
					else
					{
						while( (J1temp.charAt(ptr)=='0'||
								J1temp.charAt(ptr)=='1'||
								J1temp.charAt(ptr)=='2'||
								J1temp.charAt(ptr)=='3'||
								J1temp.charAt(ptr)=='4'||
								J1temp.charAt(ptr)=='5'||
								J1temp.charAt(ptr)=='6'||
								J1temp.charAt(ptr)=='7'||
								J1temp.charAt(ptr)=='8'||
								J1temp.charAt(ptr)=='9'||
								J1temp.charAt(ptr)=='.') && ptr >=0 )
						{
							ptr--;
							if( ptr < 0){break;}
						}
						J1temp = J1temp.substring(0, ptr+1) + "(" + "-" + J1temp.substring(ptr+1, J1temp.length()) + ")";
						J1.setText(J1temp);
					}
				}
			}			
			else if(J.getText() == "/"||
					J.getText() == "*"||
					J.getText() == "-"||
					J.getText() == "+")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					if(J1temp.charAt(J1temp.length()-1) == '/'||
					   J1temp.charAt(J1temp.length()-1) == '*'||
					   J1temp.charAt(J1temp.length()-1) == '-'||
					   J1temp.charAt(J1temp.length()-1) == '+'||
					   J1temp.charAt(J1temp.length()-1) == '('||
					   J1temp.charAt(J1temp.length()-1) == '.'){}//不能在这些符号后面输入运算符
					else
					{
						J1temp = J1.getText();
						J1temp += J.getText();
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == ".")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					ptr = J1temp.length()-1;
					while( (J1temp.charAt(ptr)=='0'||
							J1temp.charAt(ptr)=='1'||
							J1temp.charAt(ptr)=='2'||
							J1temp.charAt(ptr)=='3'||
							J1temp.charAt(ptr)=='4'||
							J1temp.charAt(ptr)=='5'||
							J1temp.charAt(ptr)=='6'||
							J1temp.charAt(ptr)=='7'||
							J1temp.charAt(ptr)=='8'||
							J1temp.charAt(ptr)=='9') && ptr >= 0)
					{
						ptr--;
						if(ptr >= 0)
						{
							if(J1temp.charAt(ptr)=='.')
							{
								flag3 = true;
								break;
							}
						}
						else
							break;
					}
					J1temp = J1.getText();
					if(J1temp.charAt(J1temp.length()-1) == '/'||
					   J1temp.charAt(J1temp.length()-1) == '*'||
					   J1temp.charAt(J1temp.length()-1) == '-'||
					   J1temp.charAt(J1temp.length()-1) == '+'||
					   J1temp.charAt(J1temp.length()-1) == '('||
					   J1temp.charAt(J1temp.length()-1) == ')'||
					   J1temp.charAt(J1temp.length()-1) == '.'||flag3){}
					else
					{
						J1temp = J1.getText();
						J1temp += J.getText();
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == "(")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals(""))
				{
					J1.setText("(");
				}
				else
				{
					if(J1temp.charAt(J1temp.length()-1) == '0' ||
					   J1temp.charAt(J1temp.length()-1) == '1' ||
					   J1temp.charAt(J1temp.length()-1) == '2' ||
					   J1temp.charAt(J1temp.length()-1) == '3' ||
					   J1temp.charAt(J1temp.length()-1) == '4' ||
					   J1temp.charAt(J1temp.length()-1) == '5' ||
					   J1temp.charAt(J1temp.length()-1) == '6' ||
					   J1temp.charAt(J1temp.length()-1) == '7' ||
					   J1temp.charAt(J1temp.length()-1) == '8' ||
					   J1temp.charAt(J1temp.length()-1) == '9' ||
					   J1temp.charAt(J1temp.length()-1) =='e'  ||
					   J1temp.charAt(J1temp.length()-1) == '.' ||
					   J1temp.charAt(J1temp.length()-1) == ')' ){}
					else
					{
						J1temp += J.getText();
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == ")")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					if(J1temp.charAt(J1temp.length()-1) == '.' ||
					   J1temp.charAt(J1temp.length()-1) == '/' ||
					   J1temp.charAt(J1temp.length()-1) == '*' ||
					   J1temp.charAt(J1temp.length()-1) == '-' ||
					   J1temp.charAt(J1temp.length()-1) == '+' ||
					   J1temp.charAt(J1temp.length()-1) == '(' ||
					   J1temp == ""){}
					else
					{
						J1temp += J.getText();
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == "√")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					ptr = J1temp.length()-1;
					if(J1temp.charAt(ptr)=='('||
					   J1temp.charAt(ptr)==')'||
					   J1temp.charAt(ptr)=='/'||
					   J1temp.charAt(ptr)=='*'||
					   J1temp.charAt(ptr)=='-'||
					   J1temp.charAt(ptr)=='+'||
					   J1temp.charAt(ptr)=='.'){}
					else
					{
						while( (J1temp.charAt(ptr)=='0'||
								J1temp.charAt(ptr)=='1'||
								J1temp.charAt(ptr)=='2'||
								J1temp.charAt(ptr)=='3'||
								J1temp.charAt(ptr)=='4'||
								J1temp.charAt(ptr)=='5'||
								J1temp.charAt(ptr)=='6'||
								J1temp.charAt(ptr)=='7'||
								J1temp.charAt(ptr)=='8'||
								J1temp.charAt(ptr)=='9'||
								J1temp.charAt(ptr)=='.') && ptr >=0 )
						{
							ptr--;
							if( ptr < 0){break;}
						}
						J1temp = J1temp.substring(0, ptr+1) + "√" + "(" + J1temp.substring(ptr+1, J1temp.length()) + ")";
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == "x^")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					ptr = J1temp.length()-1;
					if(J1temp.charAt(ptr)=='('||
					   J1temp.charAt(ptr)==')'||
					   J1temp.charAt(ptr)=='/'||
					   J1temp.charAt(ptr)=='*'||
					   J1temp.charAt(ptr)=='-'||
					   J1temp.charAt(ptr)=='+'||
					   J1temp.charAt(ptr)=='.'){}
					else
					{
						while( (J1temp.charAt(ptr)=='0'||
								J1temp.charAt(ptr)=='1'||
								J1temp.charAt(ptr)=='2'||
								J1temp.charAt(ptr)=='3'||
								J1temp.charAt(ptr)=='4'||
								J1temp.charAt(ptr)=='5'||
								J1temp.charAt(ptr)=='6'||
								J1temp.charAt(ptr)=='7'||
								J1temp.charAt(ptr)=='8'||
								J1temp.charAt(ptr)=='9'||
								J1temp.charAt(ptr)=='.') && ptr >=0 )
						{
							ptr--;
							if( ptr < 0){break;}
						}
						J1temp = J1temp + "^";
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == "1/x")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					ptr = J1temp.length()-1;
					if(J1temp.charAt(ptr)=='('||
					   J1temp.charAt(ptr)==')'||
					   J1temp.charAt(ptr)=='/'||
					   J1temp.charAt(ptr)=='*'||
					   J1temp.charAt(ptr)=='-'||
					   J1temp.charAt(ptr)=='+'||
					   J1temp.charAt(ptr)=='.'){}
					else
					{
						while( (J1temp.charAt(ptr)=='0'||
								J1temp.charAt(ptr)=='1'||
								J1temp.charAt(ptr)=='2'||
								J1temp.charAt(ptr)=='3'||
								J1temp.charAt(ptr)=='4'||
								J1temp.charAt(ptr)=='5'||
								J1temp.charAt(ptr)=='6'||
								J1temp.charAt(ptr)=='7'||
								J1temp.charAt(ptr)=='8'||
								J1temp.charAt(ptr)=='9'||
								J1temp.charAt(ptr)=='.') && ptr >=0 )
						{
							ptr--;
							if( ptr < 0){break;}
						}
						J1temp = J1temp.substring(0, ptr+1) + "(1/" + J1temp.substring(ptr+1, J1temp.length()) + ")";
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == "log")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					ptr = J1temp.length()-1;
					if(J1temp.charAt(ptr)=='('||
					   J1temp.charAt(ptr)==')'||
					   J1temp.charAt(ptr)=='/'||
					   J1temp.charAt(ptr)=='*'||
					   J1temp.charAt(ptr)=='-'||
					   J1temp.charAt(ptr)=='+'||
					   J1temp.charAt(ptr)=='.'){}
					else
					{
						while( (J1temp.charAt(ptr)=='0'||
								J1temp.charAt(ptr)=='1'||
								J1temp.charAt(ptr)=='2'||
								J1temp.charAt(ptr)=='3'||
								J1temp.charAt(ptr)=='4'||
								J1temp.charAt(ptr)=='5'||
								J1temp.charAt(ptr)=='6'||
								J1temp.charAt(ptr)=='7'||
								J1temp.charAt(ptr)=='8'||
								J1temp.charAt(ptr)=='9'||
								J1temp.charAt(ptr)=='.') && ptr >=0 )
						{
							ptr--;
							if( ptr < 0){break;}
						}
						J1temp = J1temp.substring(0, ptr+1) + "log" + "(" + J1temp.substring(ptr+1, J1temp.length()) + ")";
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == "ln")
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals("")){}
				else
				{
					ptr = J1temp.length()-1;
					if(J1temp.charAt(ptr)=='('||
					   J1temp.charAt(ptr)==')'||
					   J1temp.charAt(ptr)=='/'||
					   J1temp.charAt(ptr)=='*'||
					   J1temp.charAt(ptr)=='-'||
					   J1temp.charAt(ptr)=='+'||
					   J1temp.charAt(ptr)=='.'){}
					else
					{
						while( (J1temp.charAt(ptr)=='0'||
								J1temp.charAt(ptr)=='1'||
								J1temp.charAt(ptr)=='2'||
								J1temp.charAt(ptr)=='3'||
								J1temp.charAt(ptr)=='4'||
								J1temp.charAt(ptr)=='5'||
								J1temp.charAt(ptr)=='6'||
								J1temp.charAt(ptr)=='7'||
								J1temp.charAt(ptr)=='8'||
								J1temp.charAt(ptr)=='9'||
								J1temp.charAt(ptr)=='.') && ptr >=0 )
						{
							ptr--;
							if( ptr < 0){break;}
						}
						J1temp = J1temp.substring(0, ptr+1) + "ln" + "(" + J1temp.substring(ptr+1, J1temp.length()) + ")";
						J1.setText(J1temp);
					}
				}
			}
			else if(J.getText() == "C")
			{
				J1.setText("");
				s = "";
			}			
			else
			{
				J1temp = J1.getText();
				if(J1temp==null || J1temp.equals(""))
				{
					J1temp += J.getText();
					J1.setText(J1temp);
				}
				else
				{
					ptr = J1temp.length()-1;
					if(J1temp.charAt(ptr) == ')'){}
					else
					{
						J1temp += J.getText();
						J1.setText(J1temp);
					}
				}
			}
		}
	}
}	
