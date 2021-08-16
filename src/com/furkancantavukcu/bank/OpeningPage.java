package com.furkancantavukcu.bank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.toedter.calendar.JCalendar;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class OpeningPage extends DatabaseConnect {
	public static JPanel panel;
	private JFrame frmAccountRegistrationPage;
	private JTextField txtPersonelID;
	private JTextField txtName;
	private JTextField txtLastName;
	private JTextField txtHomeTown;
	private JTextField txtBranchNo;
	private JTextField txtAccountOpenDate;
	private JTextField txtInterestRate;
	private JTextField txtLimit;
	private JComboBox<String> cmbxInterestPeriod;
	private JComboBox<String> cmbxAccountType;
	private JComboBox<String> cmbxAccountLetter;
	private JCalendar calendarMaturityStartDate;
	private JCalendar calendarMaturityDeadline;
	private JButton btnSave;
	private JButton btnGoListPage;
	private Date onlineDate = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private int accountID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpeningPage window = new OpeningPage();
					window.frmAccountRegistrationPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OpeningPage() {
		super();
		getAccountID();
		initialize();

	}

	private void initialize() {
		createScreen();
		calendarMaturityStartDate.setDate(onlineDate);
		calendarMaturityStartDate.setMinSelectableDate(onlineDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(onlineDate);
		cal.add(Calendar.DATE, 1);

		calendarMaturityDeadline.setDate(cal.getTime());
		calendarMaturityDeadline.setMinSelectableDate(cal.getTime());

		calendarMaturityStartDate.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(calendarMaturityStartDate.getDate());
				cal1.add(Calendar.DATE, 1);

				calendarMaturityDeadline.setDate(cal1.getTime());
				calendarMaturityDeadline.setMinSelectableDate(cal1.getTime());
			}
		});

		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (controlPersonelID(txtPersonelID.getText().toString())) {

					boolean accountType = false;
					String fileNo = txtBranchNo.getText();
					String accountNo = "";
					boolean txtfieldControl = (!txtPersonelID.getText().equals("") && !txtName.getText().equals("")
							&& !txtLastName.getText().equals("") && !txtHomeTown.getText().equals("")
							&& !txtBranchNo.getText().equals("") && !txtInterestRate.getText().equals("")
							&& !txtLimit.getText().equals("") && !txtLimit.getText().equals("0")
							&& cmbxInterestPeriod.getSelectedIndex() != -1 && cmbxInterestPeriod.getSelectedIndex() != 0
							&& cmbxAccountType.getSelectedIndex() != -1 && cmbxAccountType.getSelectedIndex() != 0);

					boolean txtfieldLenghtControl = txtPersonelID.getText().length() == 11
							&& txtBranchNo.getText().length() == 4;

					boolean cmbxAccountLetterControl = true;

					if (txtfieldControl && txtfieldLenghtControl) {

						if (cmbxAccountType.getSelectedIndex() == 1) {

							accountType = false;
							for (int i = 0; i < 7; i++) {
								fileNo += (int) (Math.random() * 10);
							}
							accountNo = fileNo.substring(4);
							String control0 = accountNo.substring(0, 1);
							if (controlAccountNo(accountNo) != 0 || control0.equals("0")) {
								while (controlAccountNo(accountNo) != 0 || control0.equals("0")) {
									fileNo = txtBranchNo.getText();
									for (int i = 0; i < 7; i++) {
										fileNo += (int) (Math.random() * 10);
									}
									accountNo = fileNo.substring(4);
									control0 = accountNo.substring(0, 1);
								}
							}

						} else if (cmbxAccountType.getSelectedIndex() == 2) {
							accountType = true;
							if (cmbxAccountLetter.getSelectedIndex() != -1
									&& cmbxAccountLetter.getSelectedIndex() != 0) {
								cmbxAccountLetterControl = true;
								fileNo += cmbxAccountLetter.getSelectedItem().toString();
								for (int i = 0; i < 6; i++) {
									fileNo += (int) (Math.random() * 10);
								}
								accountNo = fileNo.substring(6);
								String control0 = accountNo.substring(0, 1);
								if (controlAccountNo(accountNo) != 0 || control0.equals("0")) {
									while (controlAccountNo(accountNo) != 0 || control0.equals("0")) {
										fileNo = txtBranchNo.getText() + cmbxAccountLetter.getSelectedItem().toString();
										for (int i = 0; i < 6; i++) {
											fileNo += (int) (Math.random() * 10);
										}
										accountNo = fileNo.substring(6);
										control0 = accountNo.substring(0, 1);
									}
								}

							} else {
								cmbxAccountLetterControl = false;
								JOptionPane.showMessageDialog(panel,
										"You must choice all of them and Limit cannot be equal to 0 "
												+ "\nor Personel ID must be 11 digits and Branch No must be 4 digits.");

							}

						}
						if (cmbxAccountLetterControl) {

							Customer newCustomer = new Customer(txtPersonelID.getText(), txtName.getText(),
									txtLastName.getText(), txtHomeTown.getText());

							String date1 = dateFormat.format(onlineDate).toString();
							Date o = calendarMaturityStartDate.getDate();
							String date2 = String.format("%1$tY-%1$tm-%1$td", o);
							Date o1 = calendarMaturityDeadline.getDate();
							String date3 = String.format("%1$tY-%1$tm-%1$td", o1);

							Date accountOpenDate = null, maturityStartDate = null, maturityDeadline = null,
									balanceUsageDate = null;
							try {

								accountOpenDate = dateFormat.parse(txtAccountOpenDate.getText());
								maturityStartDate = dateFormat.parse(date2);
								maturityDeadline = dateFormat.parse(date3);
								balanceUsageDate = dateFormat.parse(date1);
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							Account newAccount;
							accountID++;

							// accountType false ise BCH dir. True ise DVZ dir. BCH ise 0 dır. DVZ ise 1
							if (!accountType) {
								newAccount = new Account(accountID, newCustomer, txtBranchNo.getText(), accountNo, 0,
										accountOpenDate);
							} else {
								newAccount = new Account(accountID, newCustomer, txtBranchNo.getText(), accountNo,
										cmbxAccountLetter.getSelectedItem().toString(), 1, accountOpenDate);
							}

							AccountInfo newAccountInfo = new AccountInfo(fileNo, newAccount, maturityStartDate,
									maturityDeadline, Integer.valueOf(txtLimit.getText()),
									Double.valueOf(txtInterestRate.getText()),
									cmbxInterestPeriod.getSelectedItem().toString(), balanceUsageDate);

							DatabaseQueries newDatabaseQueries = new DatabaseQueries();

							newDatabaseQueries.insertData(newCustomer);
							newDatabaseQueries.insertData(newAccount);
							newDatabaseQueries.insertData(newAccountInfo);
							AccountListingPage.main(null);
							frmAccountRegistrationPage.setVisible(false);
						}
					} else {

						if (!txtfieldControl) {
							JOptionPane.showMessageDialog(panel,
									"You must choice all of them and Limit cannot be equal to 0. ");
						} else if (!txtfieldLenghtControl) {
							JOptionPane.showMessageDialog(panel,
									"Personel ID must be 11 digits and Branch No must be 4 digits.");
						}
					}
				} else
					JOptionPane.showMessageDialog(panel,
							"The person holding this PersonelID already has an account. A new account cannot be created.");

			}
		});

	}

	private boolean controlPersonelID(String personelID) {
		PreparedStatement ps;
		String query = "SELECT COUNT(*) AS 'PersonelIDNumber' FROM `customer` WHERE customer.PersonalID='" + personelID
				+ "';";
		int number = -1;
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				number = rs.getInt("PersonelIDNumber");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (number == 0)
			return true;
		else
			return false;
	}

	private int controlAccountNo(String accountNo) {
		// false BCH --> true DVZ
		PreparedStatement ps;
		String query = "SELECT COUNT(*) AS 'AccountNoNumber' FROM `account` WHERE AccountNo='" + accountNo + "';";
		int number = -1;
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				number = rs.getInt("AccountNoNumber");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return number;
	}

	private void getAccountID() {

		PreparedStatement ps;

		String query = "SELECT COUNT(AccountID) AS 'AccountIDNumber' FROM `account`";
		try {

			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				accountID = rs.getInt("AccountIDNumber");
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createScreen() {
		frmAccountRegistrationPage = new JFrame();
		frmAccountRegistrationPage.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("D:\\eclipse\\eclipse-workspace\\InternProject\\src\\Icon\\bank2.png"));
		frmAccountRegistrationPage.setTitle("Account Registration Page");
		frmAccountRegistrationPage.setBounds(100, 100, 706, 791);
		frmAccountRegistrationPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBackground(Color.CYAN);
		frmAccountRegistrationPage.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		// --------------------LABEL------------------------//

		JLabel labelTitle1 = new JLabel("Personal Information");
		labelTitle1.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle1.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelTitle1.setBounds(10, 50, 269, 19);
		panel.add(labelTitle1);

		JLabel labelTitle2 = new JLabel("Account Information");
		labelTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle2.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelTitle2.setBounds(312, 50, 368, 19);
		panel.add(labelTitle2);

		JLabel labelLine = new JLabel("");
		labelLine.setIcon(new ImageIcon("D:\\eclipse\\eclipse-workspace\\InternProject\\src\\Icon\\line.png"));
		labelLine.setBounds(289, 11, 13, 730);
		panel.add(labelLine);

		JLabel label1 = new JLabel("Personal ID:");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label1.setBounds(23, 188, 96, 20);
		panel.add(label1);

		JLabel label2 = new JLabel("Name:");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label2.setBounds(23, 219, 96, 20);
		panel.add(label2);

		JLabel label3 = new JLabel("Last Name:");
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label3.setBounds(23, 250, 96, 20);
		panel.add(label3);

		JLabel label4 = new JLabel("Hometown:");
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label4.setBounds(23, 281, 96, 20);
		panel.add(label4);

		JLabel label5 = new JLabel("Branch No:");
		label5.setHorizontalAlignment(SwingConstants.RIGHT);
		label5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label5.setBounds(341, 95, 96, 20);
		panel.add(label5);

		JLabel label6 = new JLabel("Account Open Date:");
		label6.setHorizontalAlignment(SwingConstants.RIGHT);
		label6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label6.setBounds(323, 126, 114, 20);
		panel.add(label6);

		JLabel label7 = new JLabel("Maturity Start Date:");
		label7.setHorizontalAlignment(SwingConstants.LEFT);
		label7.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label7.setBounds(323, 235, 114, 20);
		panel.add(label7);

		JLabel label8 = new JLabel("Maturity Deadline:");
		label8.setHorizontalAlignment(SwingConstants.RIGHT);
		label8.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label8.setBounds(323, 398, 114, 20);
		panel.add(label8);

		JLabel label9 = new JLabel("Interest Period:");
		label9.setHorizontalAlignment(SwingConstants.RIGHT);
		label9.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label9.setBounds(341, 485, 96, 20);
		panel.add(label9);

		JLabel label10 = new JLabel("Interest Rate:");
		label10.setHorizontalAlignment(SwingConstants.RIGHT);
		label10.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label10.setBounds(341, 517, 96, 20);
		panel.add(label10);

		JLabel label11 = new JLabel("Limit:");
		label11.setHorizontalAlignment(SwingConstants.RIGHT);
		label11.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label11.setBounds(341, 550, 96, 20);
		panel.add(label11);

		JLabel label12 = new JLabel("Account Type:");
		label12.setHorizontalAlignment(SwingConstants.RIGHT);
		label12.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label12.setBounds(341, 586, 96, 20);
		panel.add(label12);

		JLabel label13 = new JLabel("Account Letter:");
		label13.setHorizontalAlignment(SwingConstants.RIGHT);
		label13.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label13.setBounds(341, 619, 96, 20);
		panel.add(label13);
		label13.setVisible(false);

		// ----------------CUSTOMER----------------------//
		txtPersonelID = new JTextField();
		txtPersonelID.setBounds(130, 188, 130, 20);
		panel.add(txtPersonelID);
		txtPersonelID.setColumns(10);
		txtPersonelID.addKeyListener(new KeyListNumber());
		txtPersonelID.setDocument(new JTextFieldLimit(11));

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(130, 219, 130, 20);
		panel.add(txtName);

		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(130, 250, 130, 20);
		panel.add(txtLastName);

		txtHomeTown = new JTextField();
		txtHomeTown.setColumns(10);
		txtHomeTown.setBounds(130, 281, 130, 20);
		panel.add(txtHomeTown);

		// ----------------ACCOUNT----------------------//

		txtBranchNo = new JTextField();
		txtBranchNo.setColumns(10);
		txtBranchNo.setBounds(455, 95, 191, 20);
		panel.add(txtBranchNo);
		txtBranchNo.addKeyListener(new KeyListNumber());
		txtBranchNo.setDocument(new JTextFieldLimit(4));

		txtAccountOpenDate = new JTextField();
		txtAccountOpenDate.setEditable(false);
		txtAccountOpenDate.setColumns(10);
		txtAccountOpenDate.setBounds(455, 127, 191, 20);
		panel.add(txtAccountOpenDate);
		txtAccountOpenDate.setText(dateFormat.format(onlineDate).toString());
		Date maturityDeadline = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(maturityDeadline);
		c.add(Calendar.YEAR, 1);

		txtInterestRate = new JTextField();
		txtInterestRate.setColumns(10);
		txtInterestRate.setBounds(455, 518, 191, 20);
		panel.add(txtInterestRate);
		txtInterestRate.addKeyListener(new KeyListNumber());

		txtLimit = new JTextField();
		txtLimit.setColumns(10);
		txtLimit.setBounds(455, 551, 191, 20);
		panel.add(txtLimit);
		txtLimit.addKeyListener(new KeyListNumber());

		cmbxInterestPeriod = new JComboBox<String>();
		cmbxInterestPeriod.setBounds(455, 485, 191, 22);
		panel.add(cmbxInterestPeriod);
		String[] interestPeriods = { "", "1A", "2A", "3A", "H3" };
		for (String string : interestPeriods) {
			cmbxInterestPeriod.addItem(string);
		}

		cmbxAccountType = new JComboBox<String>();
		cmbxAccountType.setBounds(455, 586, 191, 22);
		panel.add(cmbxAccountType);
		String[] accountTypes = { "", "BCH", "DVZ" };
		for (String string : accountTypes) {
			cmbxAccountType.addItem(string);
		}

		cmbxAccountLetter = new JComboBox<String>();
		cmbxAccountLetter.setBounds(455, 619, 191, 22);
		panel.add(cmbxAccountLetter);
		cmbxAccountLetter.setVisible(false);
		String[] accountLetters = { "", "PF", "AU", "DA" };
		for (String string : accountLetters) {
			cmbxAccountLetter.addItem(string);
		}

		// BCH secildiginde AccountLetter gizlenecek. DVZ secildiginde gözükecek.
		cmbxAccountType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cmbxAccountType.getSelectedIndex() == 2) {
					cmbxAccountLetter.setVisible(true);
					label13.setVisible(true);
				} else {
					cmbxAccountLetter.setVisible(false);
					label13.setVisible(false);
					cmbxAccountLetter.setSelectedIndex(-1);
				}
			}
		});

		btnSave = new JButton("SAVE");
		btnSave.setBounds(422, 700, 89, 23);
		panel.add(btnSave);

		btnGoListPage = new JButton("List Account");
		btnGoListPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AccountListingPage.main(null);
				frmAccountRegistrationPage.dispose();
				;
			}
		});
		btnGoListPage.setBackground(Color.LIGHT_GRAY);
		btnGoListPage.setBounds(10, 11, 109, 23);
		panel.add(btnGoListPage);

		calendarMaturityStartDate = new JCalendar();
		calendarMaturityStartDate.setBounds(455, 157, 191, 153);
		panel.add(calendarMaturityStartDate);

		calendarMaturityDeadline = new JCalendar();
		calendarMaturityDeadline.setBounds(455, 321, 191, 153);
		panel.add(calendarMaturityDeadline);
	}
}
