import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	public static JTextField txtUsename;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String getMD5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < array.length;++i) {
				sb.append( Integer.toHexString(array[i] & 0xFF | 0x100 ).substring(1, 3));
			}
			return sb.toString();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 719, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u0110\u0103ng Nh\u1EADp");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(251, 38, 170, 61);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tên Đăng Nhập");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(97, 136, 146, 37);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mật Khẩu");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(97, 232, 146, 37);
		contentPane.add(lblNewLabel_1_1);
		
		txtUsename = new JTextField();
		txtUsename.setBounds(219, 136, 275, 37);
		contentPane.add(txtUsename);
		txtUsename.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setToolTipText("");
		txtPassword.setBounds(219, 232, 275, 37);
		contentPane.add(txtPassword);
		
		JButton btnLogin = new JButton("Đăng Nhập");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //https://giasutinhoc.vn/labs/lab-lap-trinh-java/lap-trinh-swing-trong-java/
			Connection conn = null;
			PreparedStatement ps =null;
			PreparedStatement ps2=null;
			ResultSet rs2 = null;
			ResultSet rs = null;
			int getlv;
			
			try {
				Connect a = new Connect();//lay ket noi toi co du lieu
				conn = a.getConnection();
				String sql  = "select * from login where usename= ? and pass=?";
				String level = "select level from login where usename=?";
				ps2 = conn.prepareStatement(level);
				ps2.setString(1, txtUsename.getText());
				rs2 = ps2.executeQuery();
				ps = conn.prepareStatement(sql);
				ps.setString(1, txtUsename.getText());
				ps.setString(2, new String(txtPassword.getPassword())); //getMD5(new String(txtPassword.getPassword()))
				rs = ps.executeQuery();
				//System.out.println();
				//System.out.println(getMD5(new String(txtPassword.getPassword())));//test
				if(rs.next()) {
					getlv = rs.getInt(4);
					System.out.println(getlv);
					System.out.println(getlv == 1 );
					if(getlv == 1) {
						Admin admin = new Admin();
						admin.setVisible(true);
					}else if(getlv == 0) {
						//JOptionPane.showMessageDialog(rootPane, "sai quyền truy cập");
						//ViewNV view = new ViewNV();
						//view.main(null);
						try {
							String usename = txtUsename.getText();
							PreparedStatement ps3 = conn.prepareStatement("select nv.TenNV, pb.TenPB,pb.DuAn, sl.luongcb, sl.ngaylv,sl.thuong, sl.phat , sl.tongluong from((NhanVien nv join QLPB pb on nv.MaPB = pb.MaPB)join salary sl on nv.MaNV = sl.MaNV) join login lg on lg.MaNV  = nv.MaNV where lg.usename = ?");
							ps3.setString(1,usename);
							ResultSet rs3 = ps3.executeQuery();
							while(rs3.next()) {
								String space = "                                                                                                                                      ";
								String lineSep = System.lineSeparator();
								StringBuilder result = new StringBuilder();
								result.append("Thông tin nhân viên: ").append(space).append(lineSep).append(lineSep);
								result.append("Tên Nhân Viên: ").append(rs3.getString(1)).append("").append(lineSep);
								result.append("Tên Phòng Ban: ").append(rs3.getString(2)).append(lineSep);
								result.append("Dự Án: ").append(rs3.getString(3)).append(lineSep);
								result.append("Lương Cơ Bản: ").append(rs3.getString(4)).append(lineSep);
								result.append("Số Ngày Làm Việc: ").append(rs3.getString(5)).append(lineSep);
								result.append("Thưởng: ").append(rs3.getString(6)).append(lineSep);
								result.append("Phạt: ").append(rs3.getString(7)).append(lineSep);
								if(((rs3.getInt(4)/26)*rs3.getInt(5)+rs3.getInt(6)-rs3.getInt(7)) < 10000000) {
									result.append("không thuế").append(lineSep);
								} else {
									result.append("có thuế").append(lineSep);
								}
								result.append("Tổng Lương: ").append(rs3.getString(8));
								
								JOptionPane.showMessageDialog(null, result.toString(), "Thông tin nhân viên", JOptionPane.INFORMATION_MESSAGE );
							}
						} catch(Exception ex) {
							System.out.println(ex.toString());
							
						}
						//In4staff in4 = new In4staff();
						//in4.txtUsename.setText(txtUsename.getText());
						//in4.main(null);
					}
				}else {
					JOptionPane.showMessageDialog(rootPane, "Sai Thông tin đăng nhập");
				}
			} catch (Exception ex) {
				System.out.println(ex.toString());
				ex.printStackTrace();
			}finally {
				try {
					if (conn !=null) {
						conn.close();
					}
					if(ps != null) {
						ps.close();
					}
					if(rs != null) {
						rs.close();
					}
				}catch(Exception ex2) {
					ex2.printStackTrace();
				}
			}
			String usename = txtUsename.getText();
			String password = new String(txtPassword.getPassword());
			StringBuilder sb = new StringBuilder();
			if(usename.equals("")) {
				sb.append("use name is empty \n");
			}
			if(password.equals("")) {
				sb.append("pass word is empty \n");
			}
			if(sb.length()>0) {
				JOptionPane.showMessageDialog(rootPane,sb.toString());
				return;
			}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogin.setBounds(219, 311, 116, 50);
		contentPane.add(btnLogin);
		
		JButton btnReset = new JButton("Đặt Lại");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			txtUsename.setText("");
			txtPassword.setText("");
				//QLNV fqlnv = new QLNV();
			//fqlnv.main(null); //goi form qlnv
				/*try {
					Connect com = new Connect();
					//System.out.println(Login.txtUsename.getText());
					Connection conn = com.getConnection();
					String  get = txtUsename.getText();
					System.out.println(""+"'"+get+"'"+"");
					PreparedStatement ps2 = conn.prepareStatement("select MaNV from login where usename = '"+txtUsename.getText()+"'");
					ResultSet rs2 = ps2.executeQuery();
					//String get = Login.txtUsename.getText();
					//String getMaNV = "select MaNV from login where usename = \'"+get+"\'";
					//System.out.println(getMaNV);
					
					while(rs2.next()) {
					//String sql = ;
					PreparedStatement test = conn.prepareStatement("select * from login where MaNV = ?");
					test.setString(1, rs2.getString(1));
					ResultSet rstest = test.executeQuery();
					while(rstest.next()) {
					System.out.println(rstest.getString(1));	
					}
					}
					}catch(Exception ex) {
					
				}*/
				
				
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnReset.setBounds(378, 311, 116, 50);
		contentPane.add(btnReset);
	}
}
