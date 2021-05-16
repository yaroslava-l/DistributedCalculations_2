import java.sql.*;

public class ArtistDAO {
    static Connection connection = null;
    static Statement stmt = null;

    public static String addArtist(int id, String name, String realName, int age) throws SQLException, ClassNotFoundException {
        String msg="";
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        try{
        PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO artist (artistid, name, realname, age)"+
                        "VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(4, age);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, realName);
       preparedStatement.executeUpdate();

       msg+="Artist "+name+ " успешно добавлена!";


        } catch (SQLException e)
        {
            msg+="ОШИБКА! Artist "+name+" не добавлена!";
            msg+=" >> "+e.getMessage();

        }
        return msg;
    }

    public static String deleteArtist(int id) throws SQLException
    {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String sql1 = "DELETE FROM album WHERE artistid = "+id;
        String sql2 = "DELETE FROM artist WHERE artistid = "+id;
        String msg="";
        try
        {
            int c = stmt.executeUpdate(sql1);
            if (c>0)
            {
                msg+="Album с идентификатором "
                        + id +" успешно удален!\n";
            }
            else
            {
                msg+="Album с идентификатором "
                        + id +" не найден!\n";
            }
            c = stmt.executeUpdate(sql2);
            if (c>0)
            {
                msg+="Artist с идентификатором "
                        + id +" успешно удалена!";
            }
            else
            {
                msg+="Artist с идентификатором "
                        + id +" не найдена!";
            }
        } catch (SQLException e)
        {
            msg+="ОШИБКА при удалении Artist с идентификатором "+id;
            msg+=" >> "+e.getMessage();
        }
        return msg;
    }
    public static String updateArtist(int id, int age) throws SQLException
    {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String sql = "UPDATE  artist SET age = "+age+"WHERE artistid ="+ id;
        String msg="";
        try
        {
            int c = stmt.executeUpdate(sql);
            if (c>0)
            {
                msg+=" с идентификатором "
                        + id +" успешно update!";
            }
            else
            {
                msg+="Artist с идентификатором "
                        + id +" не найдена!";
            }
        } catch (SQLException e)
        {
            msg+=" >> "+e.getMessage();
        }
        return msg;
    }
    public static String showArtist() throws SQLException {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String sql = "SELECT artistid, NAME FROM artist";
        String msg="";
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            msg+="СПИСОК Artist:";
            while (rs.next())
            {
                int id = rs.getInt("artistid");
                String name = rs.getString("name");
                msg+=" >> "+ id + " - " + name+"\n";
            }
            rs.close();
        } catch (SQLException e)
        {
            msg+="ОШИБКА при получении списка Artist";
            System.out.println(" >> "+e.getMessage());
        }
        return msg;
    }
    public static void getArtistByName(String name) throws SQLException {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String sql = "SELECT *  FROM artist WHERE name = " + "'" + name + "'";
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Artist: ");
            while (rs.next())
            {
                int id = rs.getInt("artistid");
                String arname = rs.getString("name");
                System.out.println(" >> "+ id + " - " + arname);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
