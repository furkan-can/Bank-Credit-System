package com.furkancantavukcu.bank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddBalancePage extends DatabaseConnect {

	public JFrame frmAddBalance;
	private JTextField txtPersonelID;
	private JTextField txtLimit;
	private JTextField txtAccountOpenDate;
	private JTextField txtInterestRate;
	private JTextField txtInterestPeriod;
	private JTextField txtBalance;
	private JTextField txtFileNo;
	private JTextField txtAccountNo;
	private JTextField txtBranchNo;
	private JTextField txtAccountType;
	private JPanel panel;
	private JButton btnAddBalance;
	private static String fileNo;
	private int limit;
	private int oldBalance;
	private JButton btnBackListingPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBalancePage window = new AddBalancePage();
					window.frmAddBalance.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddBalancePage() {
		super();
		createScreen();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		getData(fileNo);

		if (fileNo == null) {
			txtBalance.setEnabled(false);
			txtBalance.setEditable(false);
			btnAddBalance.setEnabled(false);
		}
		btnAddBalance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!txtBalance.getText().equals("") && !txtBalance.getText().equals("0")) {
					int newBalance = Integer.valueOf(txtBalance.getText()) + oldBalance;
					if (newBalance <= limit) {
						
						DatabaseQueries dQueries=new DatabaseQueries();
						dQueries.updateData(fileNo, newBalance);
						AccountListingPage.main(null);
						frmAddBalance.setVisible(false);
					} else {
						if (newBalance < limit) {

							if (txtAccountType.getText().equals("BCH")) {
								JOptionPane.showMessageDialog(panel, "The balance you have used before: " + oldBalance
										+ "  ₺ " + "\nYour Balance Limit is " + limit + " ₺ "
										+ "\nThe balance you want is " + txtBalance.getText() + " ₺ "
										+ "\nWith the current balance, the balance cannot be taken because your limit has been exceeded."
										+ "\nThe entered balance value cannot be more than the limit value.");
							} else {
								JOptionPane.showMessageDialog(panel, "The balance you have used before: " + oldBalance
										+ " $ " + "\nYour Balance Limit is " + limit + " $ "
										+ "\nThe balance you want is " + txtBalance.getText() + " $ "
										+ "\nWith the current balance, the balance cannot be taken because your limit has been exceeded."
										+ "\nThe entered balance value cannot be more than the limit value.");
							}
						}else {
							JOptionPane.showMessageDialog(panel, "Your Balance Limit has been reached. The balance cannot be increased.");
						}

					}
				} else {
					JOptionPane.showMessageDialog(panel, "The Balance area cannot be left blank.");
				}
			}
		});

		btnBackListingPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AccountListingPage.main(null);
				frmAddBalance.dispose();
			}
		});
	}

	private void getData(String fileNo) {
		if (fileNo != null) {
			ArrayList<List> list = new ArrayList<>();
			String query = "SELECT customer.PersonalID , customer.Name , customer.LastName , customer.Hometown , account.BranchNo , account.AccountNo , account.AccountLetter, account.AccountType , account.AccountOpenDate , accountinfo.FileNo , accountinfo.MaturityStartDate , accountinfo.MaturityDeadline , accountinfo.BalanceUsed , accountinfo.MaxLimit , accountinfo.InterestRate , accountinfo.BalanceUsageDate , accountinfo.UsageInterest , accountinfo.Tax , accountinfo.InterestPeriod FROM `accountinfo` INNER JOIN `account` ON account.AccountID=accountinfo.AccountID INNER JOIN `customer` ON customer.PersonalID=account.PersonelID WHERE accountinfo.FileNo='"
					+ fileNo + "';";

			try {

				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				List newlist;

				while (rs.next()) {

					newlist = new List(rs.getString("PersonalID"), rs.getString("Name"), rs.getString("LastName"),
							rs.getString("Hometown"), String.valueOf(rs.getInt("BranchNo")),
							String.valueOf(rs.getInt("AccountNo")), rs.getString("AccountLetter"),
							rs.getInt("AccountType"), rs.getDate("AccountOpenDate"), rs.getString("FileNo"),
							rs.getDate("MaturityStartDate"), rs.getDate("MaturityDeadline"), rs.getInt("BalanceUsed"),
							rs.getInt("MaxLimit"), rs.getDouble("InterestRate"), rs.getDate("BalanceUsageDate"),
							rs.getDouble("UsageInterest"), rs.getDouble("Tax"), rs.getString("InterestPeriod"));

					list.add(newlist);

				}

			} catch (Exception e) {
			}

			txtAccountNo.setText(list.get(0).getAccountNo());
			txtAccountOpenDate.setText(String.valueOf(list.get(0).getAccountOpenDate()));
			if (list.get(0).getAccountType() == 0) {
				txtAccountType.setText("BCH");
			} else {
				txtAccountType.setText("DVZ");
			}
			txtBranchNo.setText(list.get(0).getBranchNo());
			txtFileNo.setText(list.get(0).getFileNo());
			txtInterestPeriod.setText(list.get(0).getInterestPeriod());
			txtInterestRate.setText(String.valueOf(list.get(0).getInterestRate()));
			txtLimit.setText(String.valueOf(list.get(0).getLimit()));
			limit = list.get(0).getLimit();
			txtPersonelID.setText(list.get(0).getPersonalID());
			oldBalance = list.get(0).getBalanceUsed();
		}
	}

	public static void setFileNo(String getFileNo) {
		AddBalancePage.fileNo = getFileNo;
	}

	private void createScreen() {
		frmAddBalance = new JFrame();
		frmAddBalance.setTitle("Add Balance Page");
		frmAddBalance.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("D:\\eclipse\\eclipse-workspace\\InternProject\\src\\Icon\\bank2.png"));
		frmAddBalance.setBounds(100, 100, 326, 468);
		frmAddBalance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBackground(Color.CYAN);
		frmAddBalance.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel label1 = new JLabel("Personal ID:");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label1.setBounds(10, 11, 115, 20);
		panel.add(label1);

		JLabel label2 = new JLabel("File No:");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label2.setBounds(20, 42, 105, 20);
		panel.add(label2);

		JLabel label3 = new JLabel("Account No:");
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label3.setBounds(20, 73, 105, 20);
		panel.add(label3);

		JLabel label4 = new JLabel("Branch No:");
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label4.setBounds(20, 103, 105, 20);
		panel.add(label4);

		JLabel label5 = new JLabel("Account Type:");
		label5.setHorizontalAlignment(SwingConstants.RIGHT);
		label5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label5.setBounds(20, 134, 105, 20);
		panel.add(label5);

		JLabel label6 = new JLabel("Limit:");
		label6.setHorizontalAlignment(SwingConstants.RIGHT);
		label6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label6.setBounds(20, 165, 105, 20);
		panel.add(label6);

		JLabel label7 = new JLabel("Account Open Date:");
		label7.setHorizontalAlignment(SwingConstants.RIGHT);
		label7.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label7.setBounds(10, 196, 115, 20);
		panel.add(label7);

		JLabel label8 = new JLabel("Interest Rate:");
		label8.setHorizontalAlignment(SwingConstants.RIGHT);
		label8.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label8.setBounds(20, 227, 105, 20);
		panel.add(label8);

		JLabel label9 = new JLabel("Interest Period:");
		label9.setHorizontalAlignment(SwingConstants.RIGHT);
		label9.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label9.setBounds(20, 258, 105, 20);
		panel.add(label9);

		JLabel label10 = new JLabel("Balance:");
		label10.setHorizontalAlignment(SwingConstants.RIGHT);
		label10.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label10.setBounds(20, 289, 105, 20);
		panel.add(label10);

		txtPersonelID = new JTextField();
		txtPersonelID.setEditable(false);
		txtPersonelID.setBounds(135, 11, 120, 20);
		panel.add(txtPersonelID);
		txtPersonelID.setColumns(10);

		txtLimit = new JTextField();
		txtLimit.setEditable(false);
		txtLimit.setColumns(10);
		txtLimit.setBounds(135, 166, 120, 20);
		panel.add(txtLimit);

		txtAccountOpenDate = new JTextField();
		txtAccountOpenDate.setEditable(false);
		txtAccountOpenDate.setColumns(10);
		txtAccountOpenDate.setBounds(135, 197, 120, 20);
		panel.add(txtAccountOpenDate);

		txtInterestRate = new JTextField();
		txtInterestRate.setEditable(false);
		txtInterestRate.setColumns(10);
		txtInterestRate.setBounds(135, 228, 120, 20);
		panel.add(txtInterestRate);

		txtInterestPeriod = new JTextField();
		txtInterestPeriod.setEditable(false);
		txtInterestPeriod.setColumns(10);
		txtInterestPeriod.setBounds(135, 259, 120, 20);
		panel.add(txtInterestPeriod);

		txtBalance = new JTextField();
		txtBalance.setColumns(10);
		txtBalance.setBounds(135, 290, 120, 20);
		panel.add(txtBalance);
		txtBalance.addKeyListener(new KeyListNumber());

		txtFileNo = new JTextField();
		txtFileNo.setEditable(false);
		txtFileNo.setColumns(10);
		txtFileNo.setBounds(135, 43, 120, 20);
		panel.add(txtFileNo);

		txtAccountNo = new JTextField();
		txtAccountNo.setEditable(false);
		txtAccountNo.setColumns(10);
		txtAccountNo.setBounds(135, 74, 120, 20);
		panel.add(txtAccountNo);

		txtBranchNo = new JTextField();
		txtBranchNo.setEditable(false);
		txtBranchNo.setColumns(10);
		txtBranchNo.setBounds(135, 104, 120, 20);
		panel.add(txtBranchNo);

		txtAccountType = new JTextField();
		txtAccountType.setEditable(false);
		txtAccountType.setColumns(10);
		txtAccountType.setBounds(135, 135, 120, 20);
		panel.add(txtAccountType);

		btnAddBalance = new JButton("Add Balance");
		btnAddBalance.setBounds(93, 357, 115, 23);
		panel.add(btnAddBalance);

		btnBackListingPage = new JButton("Back");
		btnBackListingPage.setBounds(108, 391, 85, 23);
		panel.add(btnBackListingPage);
	}
}
