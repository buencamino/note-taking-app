import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class windowBank
{
	private ResultSet rset = null;
	JList<String> list_topic = new JList<>();
	JButton btn_add = new JButton("Add Note");
	JButton btn_update = new JButton("Change");
	JButton btn_delete = new JButton("Delete");
	JTextField text_addtopic; 
	JTextArea text_content;
	ListSelectionModel listselectionmodel;
	DefaultListModel<String> listModel = new DefaultListModel<String>();		
	
	public windowBank()
	{
		HandleControlButton control = new HandleControlButton();
		
	    dbconnect connect = new dbconnect();
		
		try
		{
			rset = connect.getList();
		}
		catch(Exception t)
		{
			System.out.println("resultset error");
		}
		
		btn_add.setBounds(630,60,150,40);
		btn_add.addActionListener(control);
		btn_update.setBounds(630,110,150,40);
		btn_delete.setBounds(630,160,150,40);
		btn_delete.addActionListener(control);
		text_addtopic = new JTextField();
		text_addtopic.setBounds(630,10,150,40);
		JPanel pnl_notes = new JPanel();
		pnl_notes.setLayout(null);
		list_topic.setBounds(10,10,200,500);
		listselectionmodel = list_topic.getSelectionModel();
		listselectionmodel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listselectionmodel.addListSelectionListener(new selectionHandler());
		list_topic.setSelectionModel(listselectionmodel);
		
		text_content = new JTextArea();
		text_content.setBounds(220,10,400,500);
		
		populateList(rset);
		
		pnl_notes.add(list_topic);
		pnl_notes.add(text_content);
		pnl_notes.add(text_addtopic);
		pnl_notes.add(btn_add);
		pnl_notes.add(btn_update);
		pnl_notes.add(btn_delete);
		
		JFrame mainWindow = new JFrame("main");
		mainWindow.setBounds(10,10,800,600);
		mainWindow.getContentPane().add(pnl_notes);
		
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
		connect.close();
	}
	
	public void populateList(ResultSet rset2)
	{		
		//DefaultListModel<String> listModel = new DefaultListModel<String>();		
		String topic = null;
		
		try		
		{	
			while (rset2.next())
			{
					topic = rset2.getString("notes_topic");
					listModel.addElement(topic);
			}
		}
		catch (Exception o)
		{
			System.out.println(o.getMessage());
			System.out.println("error populate list");
		}
		list_topic.setModel(listModel);
	}
/*
	public void refreshList()
	{
		listModel.clear();
		dbconnect connect4 = new dbconnect();
		
		try
		{
			rset = null;
			rset = connect4.getList();
		}
		catch (Exception n)
		{
			System.out.println(n.getMessage());
		}
		
		populateList(rset);
		connect4.close();
	}
*/
	class HandleControlButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			if (source == btn_add)
			{
				dbconnect connect2 = new dbconnect();
				try
				{
					connect2.addNote(text_addtopic.getText(), text_content.getText());
				}
				catch (Exception y)
				{
					System.out.println("error adding");
				}
				
				//attempt 2
				//refreshList();
				
			}
			else if (source == btn_delete)
			{
				dbconnect connect3 = new dbconnect();
				String topic, content;
				topic = null;
				content = null;
				
				topic = list_topic.getSelectedValue().toString();
				content = text_content.getText();
				System.out.println(topic + content);
				
				try
				{
					connect3.deleteNote(topic, content);
					connect3.close();
				}
				catch (Exception g)
				{
					System.out.println("error delete");
				}
				
				/*bugs!
				listModel.clear();
				try
				{
					rset = null;
					rset = connect3.getList();
				}
				catch(Exception t)
				{
					System.out.println("resultset error");
				}
					
				populateList(rset);
				*/
				
				connect3.close();
			}
		}
	}
	
	
	
	class selectionHandler implements ListSelectionListener
	{
		
		public void valueChanged(ListSelectionEvent e)
		{
			System.out.println("2");
			String topic = list_topic.getSelectedValue().toString();
			String content = null;
				
			dbconnect con = new dbconnect();
			try
			{
				content = con.getContent(topic);
				text_content.setText(content);
			}
			catch (Exception z)
			{
				System.out.println("content error");
			}
			con.close();
			
		}	
	}
}