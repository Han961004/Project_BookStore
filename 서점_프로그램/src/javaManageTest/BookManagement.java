package javaManageTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

import com.mysql.cj.x.protobuf.MysqlxPrepare.Execute;

import SQL.connection;

public class BookManagement extends JPanel {
	
	JTextField text, text1, text2, text3, text4, text5;

	BookManagement() {

		this.setBackground(Color.white);

		
		JLabel l1 = new JLabel("책 제목 : ");
		JTextField tf1 = new JTextField(18);
		JButton btn1 = new JButton("검색");
		JButton btn2 = new JButton("수정");
		JButton btn3 = new JButton("추가");
		
		Panel p = new Panel();
		p.setLayout(null);
		p.setSize(450, 400);

		List li = new List();
		li.setBounds(0, 20, 450, 250);
		li.removeAll(); // 리스트 내용을 전부 제거한다.
		
		add(l1);
		add(tf1);
		add(btn1);
		add(btn2);
		add(btn3);
		p.add(li);
		add(p);
		
		
		try{
        	Connection con = connection.makeConnection();
    		String sql = null;
    		ResultSet rs = null; 
    		PreparedStatement pstmt = null;
    		
    		sql = "select * from books";
    		pstmt = con.prepareStatement(sql);
    		
    		rs = pstmt.executeQuery();
    		while (rs.next()) {
    			String str = rs.getNString(1) + "  /  " + rs.getNString(3) + "  /  " + rs.getNString(2);
                li.add(str); // 리스트에 데이터를 추가한다.
    		}

        }catch(SQLException sqle){
			JOptionPane.showMessageDialog(this, "책 불러오기 성공", "등록성공", 0);
        }
		
		
		
		ActionListener listener1 = e -> {
			
			
			if(e.getSource() == btn1) {			// 검색 버튼	 누르면
				
				String bname = tf1.getText();
				Connection con = connection.makeConnection();
	    		String sql = null;
	    		PreparedStatement pstmt = null;
	    		ResultSet rs = null; 
	    		
	    		try {
	    			sql = "select * from books where bname = (?);";
	    			
	    			pstmt = con.prepareStatement(sql);
	    			pstmt.setString(1, bname);
	    			rs = pstmt.executeQuery();
	    			
	    			
	    			if (rs.next()) {
	    				if (rs.getString(2).contentEquals(bname)) {
	    					JOptionPane.showMessageDialog(this, "찾는 책이 있습니다", "검색성공", 1); // 로그인 성공
	    				}
	    			}
	    			else {
    					JOptionPane.showMessageDialog(this, "찾는 책이 없습니다", "검색실패", 0); // 비밀번호 불일치
    				}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, "꿀궭휅뷃엟럻", "에러", 0);
		    		e1.printStackTrace();
				}
			} else if(e.getSource() == btn2) {
				new BookFix();
			} else if(e.getSource() == btn3) {
				new BookAdd();
			}
		};
		btn1.addActionListener(listener1);
		btn2.addActionListener(listener1);
		btn3.addActionListener(listener1);
	}
}
