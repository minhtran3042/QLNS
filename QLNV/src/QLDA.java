import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class QLDA extends JFrame {

	private JPanel contentPane;
	private static JTable tableDA;
	private static JTextField txtMaDA;
	private static JTextField txtTenDA;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QLDA frame = new QLDA();
					frame.setVisible(true);
					LoadData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	static DefaultTableModel tbn = new DefaultTableModel();
	private static JComboBox cbTenPB;
	private static JCheckBox chbN;
	private JTextField txtTK;
	public static void LoadData() {
		try {
			Connect com = new Connect();
			Connection conn = com.getConnection();
			int number;
			Vector row, column;
			row = new Vector();
			column = new Vector();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from QLDA");
			ResultSetMetaData metadata = rs.getMetaData();
			Statement stpb = conn.createStatement();
			ResultSet rspb = stpb.executeQuery("Select TenPB from QLPB");
			number = metadata.getColumnCount();
			for(int i = 1; i <= number;i++) {
				column.add(metadata.getColumnName(i));
			}
			tbn.setColumnIdentifiers(column);
			while(rs.next()) {
				row = new Vector();
				for(int i = 1;i <= number;i++) {
					row.addElement(rs.getString(i));
				}
				tbn.addRow(row);
				tableDA.setModel(tbn);		
			}		
			while(rspb.next()) {
				row = new Vector();
				cbTenPB.addItem(rspb.getString(1));
			}
			tableDA.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(tableDA.getSelectedRow() >= 0) {
						txtMaDA.setText(tableDA.getValueAt(tableDA.getSelectedRow(), 0)+"");
						txtTenDA.setText(tableDA.getValueAt(tableDA.getSelectedRow(),2)+"");
						cbTenPB.setSelectedItem(tableDA.getValueAt(tableDA.getSelectedRow(), 3)+"");
						if(tableDA.getValueAt(tableDA.getSelectedRow(), 4).equals("Có")) {
							chbN.setSelected(true);
						}
						else {
							chbN.setSelected(false);
						}
					}
				}
			});
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
	}
	/**
	 * Create the frame.
	 */
	public QLDA() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(69, 326, 501, 138);
		contentPane.add(scrollPane);
		
		tableDA = new JTable();
		tableDA.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, Boolean.TRUE},
				{null, null, null, null},
			},
			new String[] {
				"M\u00E3 D\u1EF1 \u00C1n", "T\u00EAn D\u1EF1 \u00C1n", "T\u00EAn Ph\u00F2ng Ban", "\u0110ang Nh\u1EADn"
			}
		));
		scrollPane.setViewportView(tableDA);
		
		JLabel lblNewLabel = new JLabel("M\u00E3 D\u1EF1 \u00C1n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(69, 120, 83, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("T\u00EAn Ph\u00F2ng Ban");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(69, 183, 111, 33);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("T\u00EAn D\u1EF1 \u00C1n");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(331, 120, 83, 33);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("\u0110ang Nh\u1EADn");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(370, 183, 97, 33);
		contentPane.add(lblNewLabel_1_2);
		
		JButton btThem = new JButton("Th\u00EAm");
		btThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect com = new Connect();
					Connection conn = com.getConnection();
					PreparedStatement ps = conn.prepareStatement("insert into QLDA values(?,?,?,?,?)");
					Statement stpb = conn.createStatement();
					ResultSet rspb = stpb.executeQuery("Select MaPB from QLPB where TenPB ='"+cbTenPB.getSelectedItem().toString()+"'");
					ps.setString(1, txtMaDA.getText());
					ps.setString(3, txtTenDA.getText());
					ps.setString(4, cbTenPB.getSelectedItem().toString());
					if(chbN.isSelected()) {
						ps.setString(5, "Có");
					}
					else {
						ps.setString(5, "Không");
					}
					while(rspb.next()) {
						ps.setString(2, rspb.getString(1));
					}
					int themtc = ps.executeUpdate();
					if(themtc > 0) {
						JOptionPane.showMessageDialog(rootPane, "Thêm Thành Công");
						tbn.setRowCount(0);
						LoadData();
					}
				}
				catch(SQLException ex) {
					JOptionPane.showMessageDialog(rootPane, "Không Thêm Được");
					System.out.println(ex.toString());
				}
				txtMaDA.setText("");
				txtTenDA.setText("");
				cbTenPB.setSelectedItem("");
			}
		});
		btThem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btThem.setBounds(75, 269, 105, 33);
		contentPane.add(btThem);
		
		JButton btSua = new JButton("S\u1EEDa");
		btSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect com = new Connect();
					Connection conn = com.getConnection();
					PreparedStatement ps2 = conn.prepareStatement("update QLDA set TenDA=?, TenPB=?, checkDA=? where MaDA=?");
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery("Select MaDA from QLDA where TenDA ='"+cbTenPB.getSelectedItem().toString()+"'");
					ps2.setString(4, txtMaDA.getText());
					ps2.setString(1, txtTenDA.getText());
					ps2.setString(2, cbTenPB.getSelectedItem().toString());
					if(chbN.isSelected()) {
						ps2.setString(3, "Có");
					}
					else {
						ps2.setString(3, "Không");
					}
					int suatc = ps2.executeUpdate();
					if(suatc >0) {
						JOptionPane.showMessageDialog(rootPane, "Sửa Thành Công");
						tbn.setRowCount(0);
						LoadData();			
					}
				}
				catch(SQLException ex) {
					System.out.println(ex);
					JOptionPane.showMessageDialog(rootPane, "Không sửa được!");
				}
			}	
		});
		
		btSua.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btSua.setBounds(262, 269, 111, 33);
		contentPane.add(btSua);
		
		JButton btXoa = new JButton("X\u00F3a");
		btXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect com = new Connect();
					Connection conn = com.getConnection();
					PreparedStatement ps3 = conn.prepareStatement("delete QLDA where MaDA=?");
					ps3.setString(1, txtMaDA.getText());
					System.out.println(txtMaDA.getText());
					if(JOptionPane.showConfirmDialog(rootPane, "Xac Nhan Xoa?","Dong Y", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						int xoa = ps3.executeUpdate();
						tbn.setRowCount(0);
						LoadData();
					}
				}
				catch(Exception ex) {
					System.out.println(ex.toString());
				}
			}
		});
		btXoa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btXoa.setBounds(434, 269, 111, 33);
		contentPane.add(btXoa);
		
		chbN = new JCheckBox("C\u00F3");
		chbN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chbN.setBounds(473, 193, 97, 23);
		contentPane.add(chbN);
		
		txtMaDA = new JTextField();
		txtMaDA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMaDA.setBounds(162, 118, 152, 35);
		contentPane.add(txtMaDA);
		txtMaDA.setColumns(10);
		
		txtTenDA = new JTextField();
		txtTenDA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTenDA.setColumns(10);
		txtTenDA.setBounds(424, 118, 152, 33);
		contentPane.add(txtTenDA);
		
		cbTenPB = new JComboBox();
		cbTenPB.setBounds(178, 183, 152, 34);
		contentPane.add(cbTenPB);
		
		txtTK = new JTextField();
		txtTK.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTK.setBounds(133, 52, 240, 33);
		contentPane.add(txtTK);
		txtTK.setColumns(10);
		
		JButton btTK = new JButton("Tìm Kiếm");
		btTK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(txtTK.getText());
				try {
					Connect com = new Connect();
					Connection conn = com.getConnection();
					PreparedStatement ps1 = conn.prepareStatement("select *from QLDA where TenDA=?");
					ps1.setString(1, txtTK.getText());
					ResultSet tk = ps1.executeQuery();
					if(tk.next()){
						JOptionPane.showMessageDialog(rootPane, "Tìm thành công");
						for(int i = 0; i < tableDA.getRowCount();i++) {
							if(tableDA.getValueAt(i, 2).toString().toLowerCase().contains(txtTK.getText().toLowerCase())) {
								txtMaDA.setText(tableDA.getValueAt(i, 0).toString());
								txtTenDA.setText(tableDA.getValueAt(i, 2).toString());
								cbTenPB.setSelectedItem(tableDA.getValueAt(i, 3).toString());
								if((tableDA.getValueAt(i, 4)).equals("Có")) {
									chbN.setSelected(true);
								}		
								else {
									chbN.setSelected(false);
								}
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(rootPane, "Không tìm được");
					}
				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(rootPane, "Lỗi!");
				}
				txtTK.setText("");
			}	
		});
		btTK.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btTK.setBounds(408, 53, 105, 32);
		contentPane.add(btTK);
		
		JLabel lblNewLabel_2 = new JLabel("Quản Lý Dự Án");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(222, 10, 216, 33);
		contentPane.add(lblNewLabel_2);
	}
}
