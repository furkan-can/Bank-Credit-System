package com.furkancantavukcu.bank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccountListingPage extends DatabaseConnect {

	public JFrame frmAccountListingPage;
	private JTextField txtPersonelID;
	private JTextField txtFileNo;
	private JTable table;
	private JButton btnList;
	private JButton btnGoOpenPage;
	private JButton btnUpdateBound;
	private JPanel panel;
	private JButton btnListPayment;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountListingPage window = new AccountListingPage();
					window.frmAccountListingPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AccountListingPage() {
		super();
		createScreen();
		control();
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		btnGoOpenPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OpeningPage.main(null);
				frmAccountListingPage.dispose();

			}
		});

		btnList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (!txtPersonelID.getText().equals("") || !txtFileNo.getText().equals("")) {

					if (!txtPersonelID.getText().equals("")) {
						getDataFromPersonelID(txtPersonelID.getText());
					} else if (!txtFileNo.getText().equals("")) {
						getDataFromFileNo(txtFileNo.getText());
					}

				} else {
					getData();
				}
			}
		});

		btnUpdateBound.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {

					if (controlMaturity(table.getSelectedRow())) {

						String value = table.getModel().getValueAt(table.getSelectedRow(), 2).toString();
						AddBalancePage.main(null);
						AddBalancePage.setFileNo(value);
						frmAccountListingPage.dispose();
					} else {
						JOptionPane.showMessageDialog(panel,
								"The selected account has maturity date expired. No action can be taken.");
					}

				} else {
					JOptionPane.showMessageDialog(panel,
							"The account to be added to the balance has not been selected.");
					getData();
				}
			}
		});

		btnListPayment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {

					if (controlMaturity(table.getSelectedRow())) {

						String value = table.getModel().getValueAt(table.getSelectedRow(), 2).toString();
						PaymentListingPage.main(null);
						PaymentListingPage.setFileNo(value);
						frmAccountListingPage.dispose();
					} else {
						JOptionPane.showMessageDialog(panel,
								"The selected account has maturity date expired. No action can be taken.");
					}
				} else {
					JOptionPane.showMessageDialog(panel,
							"The account whose payment information will be listed is not selected.");
					getData();
				}
			}
		});
	}

	private void control() {
		txtPersonelID.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				if (!txtPersonelID.getText().equals(""))
					txtFileNo.setEnabled(false);
				else
					txtFileNo.setEnabled(true);
			}

			public void removeUpdate(DocumentEvent e) {
				if (!txtPersonelID.getText().equals(""))
					txtFileNo.setEnabled(false);
				else
					txtFileNo.setEnabled(true);
			}

			public void insertUpdate(DocumentEvent e) {
				if (!txtPersonelID.getText().equals(""))
					txtFileNo.setEnabled(false);
				else
					txtFileNo.setEnabled(true);
			}
		});

		txtFileNo.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				if (!txtFileNo.getText().equals(""))
					txtPersonelID.setEnabled(false);
				else
					txtPersonelID.setEnabled(true);
			}

			public void removeUpdate(DocumentEvent e) {
				if (!txtFileNo.getText().equals(""))
					txtPersonelID.setEnabled(false);
				else
					txtPersonelID.setEnabled(true);
			}

			public void insertUpdate(DocumentEvent e) {
				if (!txtFileNo.getText().equals(""))
					txtPersonelID.setEnabled(false);
				else
					txtPersonelID.setEnabled(true);
			}
		});
	}

	private void createScreen() {
		frmAccountListingPage = new JFrame();
		frmAccountListingPage.setTitle("Account Listing Page");
		frmAccountListingPage.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("D:\\eclipse\\eclipse-workspace\\InternProject\\src\\Icon\\bank2.png"));
		frmAccountListingPage.setBounds(100, 100, 1275, 543);
		frmAccountListingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBackground(Color.CYAN);
		frmAccountListingPage.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel label1 = new JLabel("Personal ID:");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label1.setBounds(280, 40, 96, 20);
		panel.add(label1);

		JLabel label2 = new JLabel("File No:");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label2.setBounds(688, 40, 96, 20);
		panel.add(label2);

		JLabel label3 = new JLabel("OR");
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));
		label3.setBounds(607, 40, 55, 16);
		panel.add(label3);

		txtPersonelID = new JTextField();
		txtPersonelID.setBounds(386, 40, 130, 20);
		panel.add(txtPersonelID);
		txtPersonelID.setColumns(10);
		txtPersonelID.addKeyListener(new KeyListNumber());
		txtPersonelID.setDocument(new JTextFieldLimit(11));

		txtFileNo = new JTextField();
		txtFileNo.setColumns(10);
		txtFileNo.setBounds(794, 40, 130, 20);
		panel.add(txtFileNo);
		txtFileNo.setDocument(new JTextFieldLimit(12));

		btnList = new JButton("LIST");
		btnList.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnList.setBounds(590, 82, 89, 23);
		panel.add(btnList);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);
		model.setColumnIdentifiers(new String[] { "Personel ID", "Account Opening Date", "File No", "Limit", "Balance",
				"Maturity Start Date", "Maturity Deadline", "Interest Period", "Balance Usage Date" });
		table.setForeground(Color.BLACK);
		table.setBackground(Color.CYAN);
		table.setBounds(10, 143, 714, 276);
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumnModel tcm = table.getColumnModel();
		TableColumn tm = tcm.getColumn(6);
		tm.setCellRenderer(new JTableColor());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 127, 1239, 366);
		panel.add(scrollPane);
		scrollPane.setViewportView(table);

		btnGoOpenPage = new JButton("Registering Account");
		btnGoOpenPage.setBackground(Color.LIGHT_GRAY);
		btnGoOpenPage.setBounds(10, 11, 164, 23);
		panel.add(btnGoOpenPage);

		btnUpdateBound = new JButton("Add Balance");
		btnUpdateBound.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdateBound.setBounds(1137, 83, 112, 23);
		panel.add(btnUpdateBound);

		btnListPayment = new JButton("List Payment");
		btnListPayment.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListPayment.setBounds(10, 83, 140, 23);
		panel.add(btnListPayment);

	}

	private boolean controlMaturity(int indis) {

		String maturityDeadline = table.getModel().getValueAt(indis, 6).toString();
		Date onlineDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(onlineDate);
		boolean result = true;
		try {
			if (new SimpleDateFormat("yyyy-MM-dd").parse(maturityDeadline)
					.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(date)) < 0) {
				result = false;
			} else {
				result = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	private void getDataFromFileNo(String txtFileNo) {

		ArrayList<List> list = new ArrayList<>();
		String query = "SELECT customer.PersonalID , customer.Name , customer.LastName , customer.Hometown , account.BranchNo , account.AccountNo , account.AccountLetter, account.AccountType , account.AccountOpenDate , accountinfo.FileNo , accountinfo.MaturityStartDate , accountinfo.MaturityDeadline , accountinfo.BalanceUsed , accountinfo.MaxLimit , accountinfo.InterestRate , accountinfo.BalanceUsageDate , accountinfo.UsageInterest , accountinfo.Tax , accountinfo.InterestPeriod FROM `accountinfo` INNER JOIN `account` ON account.AccountID=accountinfo.AccountID INNER JOIN `customer` ON customer.PersonalID=account.PersonelID WHERE accountinfo.FileNo='"
				+ txtFileNo + "';";

		try {

			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			List newlist;

			while (rs.next()) {

				newlist = new List(rs.getString("PersonalID"), rs.getString("Name"), rs.getString("LastName"),
						rs.getString("Hometown"), String.valueOf(rs.getInt("BranchNo")),
						String.valueOf(rs.getInt("AccountNo")), rs.getString("AccountLetter"), rs.getInt("AccountType"),
						rs.getDate("AccountOpenDate"), rs.getString("FileNo"), rs.getDate("MaturityStartDate"),
						rs.getDate("MaturityDeadline"), rs.getInt("BalanceUsed"), rs.getInt("MaxLimit"),
						rs.getDouble("InterestRate"), rs.getDate("BalanceUsageDate"), rs.getDouble("UsageInterest"),
						rs.getDouble("Tax"), rs.getString("InterestPeriod"));

				list.add(newlist);

			}

		} catch (Exception e) {
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setNumRows(0);

		Object[] row = new Object[9];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getPersonalID();
			row[1] = list.get(i).getAccountOpenDate();
			row[2] = list.get(i).getFileNo();
			row[3] = list.get(i).getLimit();
			row[4] = list.get(i).getBalanceUsed();
			row[5] = list.get(i).getMaturityStartDate();
			row[6] = list.get(i).getMaturityDeadline();
			row[7] = list.get(i).getInterestPeriod();
			row[8] = list.get(i).getBalanceUsageDate();
			model.addRow(row);

		}
	}

	private void getDataFromPersonelID(String txtPersonelID) {

		ArrayList<List> list = new ArrayList<>();
		String query = "SELECT customer.PersonalID , customer.Name , customer.LastName , customer.Hometown , account.BranchNo , account.AccountNo , account.AccountLetter, account.AccountType , account.AccountOpenDate , accountinfo.FileNo , accountinfo.MaturityStartDate , accountinfo.MaturityDeadline , accountinfo.BalanceUsed , accountinfo.MaxLimit , accountinfo.InterestRate , accountinfo.BalanceUsageDate , accountinfo.UsageInterest , accountinfo.Tax , accountinfo.InterestPeriod FROM `accountinfo` INNER JOIN `account` ON account.AccountID=accountinfo.AccountID INNER JOIN `customer` ON customer.PersonalID=account.PersonelID WHERE customer.PersonalID="
				+ txtPersonelID + ";";

		try {

			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			List newlist;

			while (rs.next()) {

				newlist = new List(rs.getString("PersonalID"), rs.getString("Name"), rs.getString("LastName"),
						rs.getString("Hometown"), String.valueOf(rs.getInt("BranchNo")),
						String.valueOf(rs.getInt("AccountNo")), rs.getString("AccountLetter"), rs.getInt("AccountType"),
						rs.getDate("AccountOpenDate"), rs.getString("FileNo"), rs.getDate("MaturityStartDate"),
						rs.getDate("MaturityDeadline"), rs.getInt("BalanceUsed"), rs.getInt("MaxLimit"),
						rs.getDouble("InterestRate"), rs.getDate("BalanceUsageDate"), rs.getDouble("UsageInterest"),
						rs.getDouble("Tax"), rs.getString("InterestPeriod"));

				list.add(newlist);

			}

		} catch (Exception e) {
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setNumRows(0);

		Object[] row = new Object[9];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getPersonalID();
			row[1] = list.get(i).getAccountOpenDate();
			row[2] = list.get(i).getFileNo();
			row[3] = list.get(i).getLimit();
			row[4] = list.get(i).getBalanceUsed();
			row[5] = list.get(i).getMaturityStartDate();
			row[6] = list.get(i).getMaturityDeadline();
			row[7] = list.get(i).getInterestPeriod();
			row[8] = list.get(i).getBalanceUsageDate();
			model.addRow(row);

		}
	}

	private void getData() {
		ArrayList<List> list = new ArrayList<>();
		String query = "SELECT customer.PersonalID , customer.Name , customer.LastName , customer.Hometown , account.BranchNo , account.AccountNo , account.AccountLetter, account.AccountType , account.AccountOpenDate , accountinfo.FileNo , accountinfo.MaturityStartDate , accountinfo.MaturityDeadline , accountinfo.BalanceUsed , accountinfo.MaxLimit , accountinfo.InterestRate , accountinfo.BalanceUsageDate , accountinfo.UsageInterest , accountinfo.Tax , accountinfo.InterestPeriod FROM `accountinfo` INNER JOIN `account` ON account.AccountID=accountinfo.AccountID INNER JOIN `customer` ON customer.PersonalID=account.PersonelID;";

		try {

			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			List newlist;

			while (rs.next()) {

				newlist = new List(rs.getString("PersonalID"), rs.getString("Name"), rs.getString("LastName"),
						rs.getString("Hometown"), String.valueOf(rs.getInt("BranchNo")),
						String.valueOf(rs.getInt("AccountNo")), rs.getString("AccountLetter"), rs.getInt("AccountType"),
						rs.getDate("AccountOpenDate"), rs.getString("FileNo"), rs.getDate("MaturityStartDate"),
						rs.getDate("MaturityDeadline"), rs.getInt("BalanceUsed"), rs.getInt("MaxLimit"),
						rs.getDouble("InterestRate"), rs.getDate("BalanceUsageDate"), rs.getDouble("UsageInterest"),
						rs.getDouble("Tax"), rs.getString("InterestPeriod"));

				list.add(newlist);

			}

		} catch (Exception e) {
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setNumRows(0);

		Object[] row = new Object[9];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getPersonalID();
			row[1] = list.get(i).getAccountOpenDate();
			row[2] = list.get(i).getFileNo();
			row[3] = list.get(i).getLimit();
			row[4] = list.get(i).getBalanceUsed();
			row[5] = list.get(i).getMaturityStartDate();
			row[6] = list.get(i).getMaturityDeadline();
			row[7] = list.get(i).getInterestPeriod();
			row[8] = list.get(i).getBalanceUsageDate();
			model.addRow(row);
		}

	}
}
