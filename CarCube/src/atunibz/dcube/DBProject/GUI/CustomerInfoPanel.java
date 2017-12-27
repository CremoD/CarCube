package atunibz.dcube.DBProject.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.*;

import atunibz.dcube.DBProject.configuration.AppResources;

public class CustomerInfoPanel extends BackgroundedPanel {
	
	private String customerPkey;
	private IconLabel nameLbl, surnameLbl, taxLbl, phoneLbl, mailLbl, faxLbl;
	private int numberOfPhones, numberOfMails, numberOfFaxes;
	private JLabel nameTF, surnameTF, taxTF, phoneTF, mailTF, faxTF;
	private JButton backBtn, statsBtn, deleteBtn, modifyBtn;
	private String[] results = {"Default", "Default", "CRMDVD96E15A952H", "Default", "Default", "Default"};
	
	public CustomerInfoPanel(String customerPkey) {
		this.customerPkey = customerPkey;
		initComponents();
		configLayout();
		getQueryResults();
		getNumberOfContacts();
		getPhones();
		getMails();
		getFaxes();
		
	}
	//select count(*) from phone_contact where owner_customer = customerPkey;
	private void getNumberOfContacts() {
		Connection conn = DatabaseConnection.getDBConnection().getConnection();
		try {
			Statement stmnt = conn.createStatement();
			String queryPhone, queryMail, queryFax;
			queryPhone = "select count(*) from phone_contact where owner_customer = " + "'" + customerPkey + "'";
			queryMail = "select count(*) from mail_contact where owner_customer = " + "'" + customerPkey + "'";
			queryFax = "select count(*) from fax_contact where owner_customer = " + "'" + customerPkey + "'";
			ResultSet rsPhone = stmnt.executeQuery(queryPhone);
			while(rsPhone.next()) {
				numberOfPhones = rsPhone.getInt("count");
			}
			ResultSet rsMail = stmnt.executeQuery(queryMail);
			while(rsMail.next()) {
				numberOfMails = rsMail.getInt("count");
			}
			ResultSet rsFax = stmnt.executeQuery(queryFax);
			while(rsFax.next()) {
				numberOfFaxes = rsFax.getInt("count");
			}
			System.out.println("The customer has " + numberOfPhones + " phone numbers, " + numberOfMails + " mail addresses and " + numberOfFaxes + " fax numbers.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String[] getPhones() {
		Connection conn = DatabaseConnection.getDBConnection().getConnection();
		String[] phones = new String[numberOfPhones];
		int index = 0;
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select phone_number from phone_contact where owner_customer = '" + customerPkey + "'");
			while(rs.next()) {
				phones[index] = rs.getString(1);
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(Arrays.toString(phones));
		return phones;
	}
	
	private String[] getMails() {
		Connection conn = DatabaseConnection.getDBConnection().getConnection();
		String[] mails = new String[numberOfMails];
		int index = 0;
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select mail from mail_contact where owner_customer = '" + customerPkey + "'");
			while(rs.next()) {
				mails[index] = rs.getString(1);
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(Arrays.toString(mails));
		return mails;
	}
	
	private String[] getFaxes() {
		Connection conn = DatabaseConnection.getDBConnection().getConnection();
		String[] faxes = new String[numberOfFaxes];
		int index = 0;
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select fax from fax_contact where owner_customer = '" + customerPkey + "'");
			while(rs.next()) {
				faxes[index] = rs.getString(1);
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(faxes.length == 0)
			System.out.println("[No fax]");
		System.out.println(Arrays.toString(faxes));
		return faxes;
	}
	
	
	
	private void initComponents() {
		//name
		nameLbl = new IconLabel("icons/contacts/tax.png","Name:", AppResources.DEFAULT_FONT, false);
		nameTF = new JLabel();
		//surname
		surnameLbl = new IconLabel("icons/contacts/tax.png", "Surname:", AppResources.DEFAULT_FONT, false);
		surnameTF = new JLabel();
		//taxcode
		taxLbl = new IconLabel("icons/contacts/tax.png","Taxcode:", AppResources.DEFAULT_FONT, false);
		taxTF = new JLabel();
		//phone
		phoneLbl = new IconLabel("icons/contacts/phone.png","Phone:", AppResources.DEFAULT_FONT, false);
		phoneTF = new JLabel();
		//mail
		mailLbl = new IconLabel("icons/contacts/mail.png","Mail:", AppResources.DEFAULT_FONT, false);
		mailTF = new JLabel();
		//fax
		faxLbl = new IconLabel("icons/contacts/fax.png","Fax:", AppResources.DEFAULT_FONT, false);
		faxTF = new JLabel();
		//go back
		backBtn = new JButton("Back");
		statsBtn = new JButton("Stats");
		deleteBtn = new JButton("Delete");
		modifyBtn = new JButton("Modify");
	}
	
	
	private void configLayout() {
		
		this.setLayout(new BorderLayout());
		this.add(AppResources.carCubePanel(), BorderLayout.NORTH);
		JPanel infoPanel = new JPanel();
		
		GridBagLayout l = new GridBagLayout();
		infoPanel.setLayout(l);
		GridBagConstraints c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		//set constraints and add name
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, -48, 0, 0);
		infoPanel.add(nameLbl, c);
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.LINE_START;
		infoPanel.add(nameTF, c);
		
		//set constraints and add surname
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(surnameLbl, c);
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(surnameTF, c);
		
		//set constraints and add taxcode
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(taxLbl, c);
		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(taxTF, c);
		
		//set constraints and add phone number
		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(phoneLbl, c);
		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(phoneTF, c);
		
		//set constraints and add phone number
		c.gridx = 0;
		c.gridy = 8;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(phoneLbl, c);
		c.gridx = 1;
		c.gridy = 8;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(phoneTF, c);
		
		//set constraints and add mail
		c.gridx = 0;
		c.gridy = 10;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(mailLbl, c);
		c.gridx = 1;
		c.gridy = 10;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(mailTF, c);
		
		//set constraints and add fax
		c.gridx = 0;
		c.gridy = 12;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(faxLbl, c);
		c.gridx = 1;
		c.gridy = 12;
		c.insets = new Insets(0, 0, 0, 0);
		infoPanel.add(faxTF, c);
		this.add(infoPanel, BorderLayout.CENTER);
		
		//set up buttons
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(backBtn);
		backBtn.addActionListener(new BackListener());
		btnPanel.add(statsBtn);
		btnPanel.add(modifyBtn);
		btnPanel.add(deleteBtn);
		deleteBtn.addActionListener(new DeleteListener());
		
		this.add(btnPanel, BorderLayout.SOUTH);
		
		
	}
	
	private void getQueryResults() {
		nameTF.setText(results[0]);
		surnameTF.setText(results[1]);
		taxTF.setText(results[2]);
		phoneTF.setText(results[3]);
		mailTF.setText(results[4]);
		faxTF.setText(results[5]);
	}
	
	
	private class BackListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			MainPanel.getMainPanel().swapPanel(new StakeholdersPanel());
			
		}
	}
	
	
	private class DeleteListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String pkey = taxTF.getText();
			String query = "DELETE from customer WHERE tax_code = ?";
			PreparedStatement stmt;
			try {
				stmt = DatabaseConnection.getDBConnection().getConnection().prepareStatement(query);
				stmt.setString(1, pkey);
				stmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//MainPanel.getMainPanel().swapPanel(new StakeholdersPanel());
			
		}
		
	}
	

}