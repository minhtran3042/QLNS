import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class Admin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin frame = new Admin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Admin() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Admin ad = new Admin();
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("tat form");
				Admin ad = new Admin();
				ad.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("QU\u1EA2N L\u00DD");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(200, 10, 165, 44);
		contentPane.add(lblNewLabel);
		
		JButton btnStaff = new JButton("Qu\u1EA3n l\u00FD Nh\u00E2n Vi\u00EAn");
		btnStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QLNV staff = new QLNV();
				staff.main(null);
			}
		});
		btnStaff.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnStaff.setBounds(44, 64, 181, 37);
		contentPane.add(btnStaff);
		
		JButton btnUser = new JButton("Qu\u1EA3n L\u00FD Ng\u01B0\u1EDDi D\u00F9ng");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QLUSER user = new QLUSER();
				user.main(null);
			}
		});
		btnUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUser.setBounds(235, 64, 207, 37);
		contentPane.add(btnUser);
		
		JButton btnDepartment = new JButton("Qu\u1EA3n L\u00FD Ph\u00F2ng Ban");
		btnDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			QLPB pb = new QLPB();
			pb.main(null);
			}
		});
		btnDepartment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDepartment.setBounds(44, 164, 181, 37);
		contentPane.add(btnDepartment);
		
		JButton btnSalary = new JButton("T\u00EDnh L\u01B0\u01A1ng");
		btnSalary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			SALARY luong = new SALARY();
			luong.main(null);
			}
		});
		btnSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSalary.setBounds(235, 164, 204, 37);
		contentPane.add(btnSalary);
		
		JButton btnQLDA = new JButton("Qu\u1EA3n L\u00FD D\u1EF1 \u00C1n");
		btnQLDA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			QLDA da = new QLDA();
			da.main(null);
			}
		});
		btnQLDA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnQLDA.setBounds(129, 117, 204, 37);
		contentPane.add(btnQLDA);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
