package javaManageTest;

import java.awt.Color;
import java.awt.Container;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import SQL.connection;

public class MemberManagement extends JPanel {

	MemberManagement() {
		this.setBackground(Color.white);
		
		//이름 검색해서 주문 번호와 책재목 가격 구매날짜가 나와야 함
		
		JLabel l1 = new JLabel("회원아이디 : ");
		JTextField tf1 = new JTextField(10);
		JButton btn1 = new JButton("검색");
		JButton btn2 = new JButton("수정");
		
		Panel p = new Panel();
		p.setLayout(null);
		p.setSize(450, 400);

		JTextArea p2 = new JTextArea();

		p2.setBounds(100, 20, 300, 100);
		p2.removeAll(); // 리스트 내용을 전부 제거한다.
		p2.setEnabled(false);
		
		List li = new List();
		li.setBounds(0, 130, 450, 150);
		li.add("     이름   |   나이  |   패스워드   |   아이디                          회원 리스트   ");
		
		add(l1);
		add(tf1);
		add(btn1);
		add(btn2);
		p.add(p2);
		add(p);
		p.add(li);
		
		
		
		try{
        	Connection con = connection.makeConnection();
    		String sql = null;
    		ResultSet rs = null; 
    		PreparedStatement pstmt = null;
    		
    		sql = "select * from members";
    		pstmt = con.prepareStatement(sql);
    		
    		rs = pstmt.executeQuery();
    		
    		while (rs.next()) {
    			
    			String str = "  " +  rs.getNString(3) + "  |       " + rs.getInt(4) +  "  |           " + rs.getNString(2) + "  |  " + rs.getNString(1) ;
                li.add(str); // 리스트에 데이터를 추가한다.
    		}

        }catch(SQLException sqle){
			JOptionPane.showMessageDialog(this, "책 불러오기 실패", "불러오기 실패", 0);
        }
		
		
		
		
		
		
		
		ActionListener listener1 = e -> {

			if(e.getSource() == btn1) {			// 입력완료 누르면
				
				String id = tf1.getText();
				
				Connection con = connection.makeConnection();
	    		String sql = null;
	    		ResultSet rs = null; 
	    		PreparedStatement pstmt = null;
	    		
	    		sql = "select * from members where id = '" + id + "';";
	    		
	    		try {
	    			pstmt = con.prepareStatement(sql);
	    			pstmt.execute();
	    			rs = pstmt.executeQuery();
	    			rs.next();
	    			
	    			
	    			p2.setText("아이디\t\t" + rs.getNString(1) + "\n" +
	    						"패스워드\t\t" + rs.getNString(2) + "\n" +
	    						"이   름\t\t" + rs.getNString(3) + "\n" +
	    						"나   이\t\t" + rs.getInt(4) + "\n"
	    						);

	    			JOptionPane.showMessageDialog(this, "고객 검색 성공", "검색성공", 1);
	    			
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "고객 검색 실패", "검색실패", 0);
					e1.printStackTrace();
				}
			} else if(e.getSource() == btn2) {
				new MemberFix();
			}
		};
		btn1.addActionListener(listener1);
		btn2.addActionListener(listener1);
	}
}
