package com.furkancantavukcu.bank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PaymentListingPage extends DatabaseConnect {

	private JPanel panel;
	public JFrame frmPaymentListingPage;
	private JTextField txtPersonelID;
	private JTextField txtFileNo;
	private JTextField txtBalanceUsageDate;
	private JTextField txtBalance;
	private JTextField txtOnlineDate;
	private JTextField txtNumberOfDays;
	private JTextField txtInterest;
	private JTextField txtTax;
	private JButton btnBack;
	private Date onlineDate = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static String fileNo;
	private JButton btnSave;
	private double interest,tax;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentListingPage window = new PaymentListingPage();
					window.frmPaymentListingPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PaymentListingPage() {
		super();
		createScreen();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		getData(fileNo);
		
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AccountListingPage.main(null);
				frmPaymentListingPage.dispose();
			}
		});
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DatabaseQueries dQueries=new DatabaseQueries();
				dQueries.updateData(interest, tax, fileNo);
				AccountListingPage.main(null);
				frmPaymentListingPage.dispose();
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

			txtPersonelID.setText(list.get(0).getPersonalID());
			txtFileNo.setText(list.get(0).getFileNo());
			txtBalanceUsageDate.setText(list.get(0).getBalanceUsageDate().toString());
			txtBalance.setText(String.valueOf(list.get(0).getBalanceUsed()));
			txtOnlineDate.setText(dateFormat.format(onlineDate));
			
			Date tarih1,tarih2;
			long fark=0;
			try {
				tarih1 = dateFormat.parse(txtBalanceUsageDate.getText());
				tarih2 = dateFormat.parse(txtOnlineDate.getText()); 
				fark = tarih2.getTime() - tarih1.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txtNumberOfDays.setText(String.valueOf(TimeUnit.DAYS.convert(fark, TimeUnit.MILLISECONDS)));
			interest=Double.valueOf(txtBalance.getText())*list.get(0).getInterestRate()*Double.valueOf(txtNumberOfDays.getText())/36000;
			
			txtInterest.setText(String.valueOf(String.format("%.5g%n",interest)));
			
			tax=interest*5/100;
			
			txtTax.setText(String.valueOf(String.format("%.5g%n",tax)));
			
		}
	}

	public static void setFileNo(String fileNo) {
		PaymentListingPage.fileNo = fileNo;
	}
	
	private void createScreen() {
		frmPaymentListingPage = new JFrame();
		frmPaymentListingPage.setTitle("Payment Listing Page");
		frmPaymentListingPage.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\eclipse\\eclipse-workspace\\InternProject\\src\\Icon\\bank2.png"));
		frmPaymentListingPage.setBounds(100, 100, 360, 415);
		frmPaymentListingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setBackground(Color.CYAN);
		frmPaymentListingPage.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel label1 = new JLabel("Personal ID:");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label1.setBounds(37, 29, 115, 20);
		panel.add(label1);
		
		JLabel label2 = new JLabel("File No:");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label2.setBounds(47, 60, 105, 20);
		panel.add(label2);
		
		JLabel label3 = new JLabel("Balance Usage Date:");
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label3.setBounds(10, 91, 142, 20);
		panel.add(label3);
		
		JLabel label4 = new JLabel("Balance:");
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label4.setBounds(47, 121, 105, 20);
		panel.add(label4);
		
		JLabel label5 = new JLabel("Online Date:");
		label5.setHorizontalAlignment(SwingConstants.RIGHT);
		label5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label5.setBounds(47, 152, 105, 20);
		panel.add(label5);
		
		JLabel label6 = new JLabel("Number of Days:");
		label6.setHorizontalAlignment(SwingConstants.RIGHT);
		label6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label6.setBounds(47, 183, 105, 20);
		panel.add(label6);
		
		JLabel label7 = new JLabel("Interest:");
		label7.setHorizontalAlignment(SwingConstants.RIGHT);
		label7.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label7.setBounds(37, 214, 115, 20);
		panel.add(label7);
		
		JLabel label8 = new JLabel("Tax:");
		label8.setHorizontalAlignment(SwingConstants.RIGHT);
		label8.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label8.setBounds(47, 245, 105, 20);
		panel.add(label8);
		
		txtPersonelID = new JTextField();
		txtPersonelID.setEditable(false);
		txtPersonelID.setColumns(10);
		txtPersonelID.setBounds(162, 29, 120, 20);
		panel.add(txtPersonelID);
		
		txtFileNo = new JTextField();
		txtFileNo.setEditable(false);
		txtFileNo.setColumns(10);
		txtFileNo.setBounds(162, 61, 120, 20);
		panel.add(txtFileNo);
		
		
		
		txtBalanceUsageDate = new JTextField();
		txtBalanceUsageDate.setEditable(false);
		txtBalanceUsageDate.setColumns(10);
		txtBalanceUsageDate.setBounds(162, 92, 120, 20);
		panel.add(txtBalanceUsageDate);
		
		txtBalance = new JTextField();
		txtBalance.setEditable(false);
		txtBalance.setColumns(10);
		txtBalance.setBounds(162, 122, 120, 20);
		panel.add(txtBalance);
		
		
		
		txtOnlineDate = new JTextField();
		txtOnlineDate.setEditable(false);
		txtOnlineDate.setColumns(10);
		txtOnlineDate.setBounds(162, 153, 120, 20);
		panel.add(txtOnlineDate);
		
		txtNumberOfDays = new JTextField();
		txtNumberOfDays.setEditable(false);
		txtNumberOfDays.setColumns(10);
		txtNumberOfDays.setBounds(162, 184, 120, 20);
		panel.add(txtNumberOfDays);
		
		
		
		txtInterest = new JTextField();
		txtInterest.setEditable(false);
		txtInterest.setColumns(10);
		txtInterest.setBounds(162, 215, 120, 20);
		panel.add(txtInterest);
		
		txtTax = new JTextField();
		txtTax.setEditable(false);
		txtTax.setColumns(10);
		txtTax.setBounds(162, 246, 120, 20);
		panel.add(txtTax);
		
		
		
		btnBack = new JButton("Back");
		btnBack.setBounds(124, 322, 89, 23);
		panel.add(btnBack);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(114, 288, 115, 23);
		panel.add(btnSave);
	}
}
