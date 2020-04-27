package caculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class ShadePanel extends JPanel {
    
    //private static final long serialVersionUID = -2644424271663261406L;
    
    public ShadePanel() {
        super();
        setLayout(null);
    }
    
    @Override
    protected void paintComponent(Graphics g1) {// 重写绘制组件外观
        Graphics2D g = (Graphics2D) g1;
        super.paintComponent(g);// 执行超类方法
        int width = getWidth();// 获取组件大小
        int height = getHeight();
        // 创建填充模式对象
        GradientPaint paint = new GradientPaint(0, 0, Color.CYAN, 0, height,Color.MAGENTA);
        g.setPaint(paint);// 设置绘图对象的填充模式
        g.fillRect(0, 0, width, height);// 绘制矩形填充控件界面
    }
}
 
public class About extends JDialog {
    private static final long serialVersionUID = 4693799019369193520L;
    private JPanel contentPane;
    private Font f1 = new Font("微软雅黑",Font.PLAIN,15);
	private Font f2 = new Font("微软雅黑",Font.PLAIN,20);
	private ImageIcon icon;
	private JLabel label;
    public About() {
        setTitle("Game");//设置窗体标题
        Image img=Toolkit.getDefaultToolkit().getImage("title.png");//窗口图标
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//设置为模态窗口
        setSize(410,380);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// 创建内容面板
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JPanel shadePanel = new JPanel();// 创建背景面板
        contentPane.add(shadePanel, BorderLayout.CENTER);// 添加面板到窗体内容面板
        shadePanel.setLayout(null);
        
    	final JLabel Game = new JLabel("https://github.com/teamYY823");
    	Game.setFont(f2);
    	Game.setBackground(Color.white);
    	Game.addMouseListener(new InternetMonitor());
    	
    	shadePanel.add(Game);
    	Game.setBounds(10, 45, 400, 30);
    	
        
        JTextArea J1 = new JTextArea("版本：2020_04_26_2.0\n开发者：ZXY&YY\n开发语言：Java\n");
        J1.setFocusable(false);
    	J1.setFont(new Font("楷体", Font.PLAIN, 16));
    	J1.setEditable(false);
    	J1.setOpaque(false);//背景透明
    	shadePanel.add(J1);
    	J1.setBounds(10, 180, 400, 180); 
    	J1.setForeground(Color.gray);
	    
 
       
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new About();
	}
}

//链接展示
class InternetMonitor extends MouseAdapter{
	public void mouseClicked(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		String J_temp = JLabel_temp.getText();
		System.out.println(J_temp);
		URI uri ;
			try {
				uri = new URI(J_temp);
				Desktop desk=Desktop.getDesktop();
				if(Desktop.isDesktopSupported() && desk.isSupported(Desktop.Action.BROWSE)){
					try {
						desk.browse(uri);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
	}
	public void mouseEntered(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.red);
	}
	public void mouseExited(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.blue);
	}
}
