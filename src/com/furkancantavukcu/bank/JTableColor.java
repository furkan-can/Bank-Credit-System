package com.furkancantavukcu.bank;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
class JTableColor extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {
		setEnabled(table == null || table.isEnabled()); // see question above

		Date onlineDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(onlineDate);

		try {
			if (new SimpleDateFormat("yyyy-MM-dd").parse(table.getModel().getValueAt(row, 6).toString())
					.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(date)) < 0) {
				setBackground(Color.RED);
			} else {
				setBackground(Color.CYAN);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.getTableCellRendererComponent(table, value, selected, focused, row, column);

		return this;
	}
}