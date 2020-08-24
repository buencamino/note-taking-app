import java.sql.*;

public class dbconnect
{
	private Connection conn = null;
	ResultSet rset = null;
	Statement statement = null;
	
	public void getConnection() throws Exception
	{
		try 
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:m.db");
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public String getContent(String topic) throws Exception
	{
		rset = null;
		String text = null;
		
		try
		{
			getConnection();
			statement = conn.createStatement();
			rset = statement.executeQuery("select * from tbl_notes where notes_topic = '" + topic + "'");
			text = rset.getString("notes_content");
		}
		catch (Exception y)
		{
			System.out.println("error content fill");
		}
		
		return text;
	}
	
	public void deleteNote(String topic, String content) throws Exception
	{
		try
		{
			getConnection();
			PreparedStatement statement = conn.prepareStatement("delete from tbl_notes where notes_topic = ? and notes_content = ?");
			statement.setString(1, topic);
			statement.setString(2, content);
			statement.executeUpdate();
		}
		catch (Exception l)
		{
			System.out.println("error delete");
		}
	}
	
	public ResultSet getList() throws Exception
	{
		rset = null;
		
		try
		{
			getConnection();
			statement = conn.createStatement();
			rset = statement.executeQuery("select * from tbl_notes");
		}
		catch (Exception h)
		{
			System.out.println("get list error");
		}
		
		return rset;
	}
	
	public void addNote(String topicadd, String contentadd) throws Exception
	{	
		try
		{
			getConnection();
			PreparedStatement statement = conn.prepareStatement("insert into tbl_notes(notes_topic, notes_content) values (?, ?)");
			statement.setString(1, topicadd);
			statement.setString(2, contentadd);
			statement.executeUpdate();
		}
		catch (Exception m)
		{
			System.out.println("error insert topic");
			System.out.println(m.getMessage());
		}
	}
	
	public void close()
	{
		rset = null;

                try
                {
                        if (rset != null)
                        {
                                rset.close();
                        }

                        if (statement != null)
                        {
								statement.close();
                        }

                        if (conn != null)
                        {
                                conn.close();
                        }
                } catch (Exception e){
					System.out.println("error close");
				}      
	}
}