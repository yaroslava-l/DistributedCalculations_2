import java.sql.*;
import java.util.ArrayList;

public class AlbumDAO {
    static Connection connection = null;
    static Statement stmt = null;

    public static String addAlbum(int id, String name, int price, int year, String gener, int arid) throws SQLException, ClassNotFoundException {
        String msg = "";
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO album (AlbumID, Name, Price , Year, Gener, ArtistID)"+
                            "VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, year);
            preparedStatement.setInt(6, arid);
            preparedStatement.setString(2, name);
            preparedStatement.setString(5, gener);
            preparedStatement.executeUpdate();

            msg+="Album "+name+ " успешно добавлена!";


        } catch (SQLException e)
        {
            msg+="ОШИБКА! Album "+name+" не добавлена!";
            msg+=" >> "+e.getMessage();

        }
        return msg;
    }

    public static String deleteAlbum(int id) throws SQLException
    {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String msg = "";
        String sql = "DELETE FROM album WHERE Albumid = "+id;
        try
        {
            int c = stmt.executeUpdate(sql);
            if (c>0)
            {
                msg+="Album с идентификатором "
                        + id +" успешно удалена!";
            }
            else
            {
                msg+="Album с идентификатором "
                        + id +" не найдена!";
            }
        } catch (SQLException e)
        {
            msg+="ОШИБКА при удалении Album с идентификатором "+id;
            msg+=" >> "+e.getMessage();
        }
        return msg;
    }
    public static String updateAlbum(int id, int price) throws SQLException
    {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String msg = "";
        String sql = "UPDATE  album SET price = "+price+"WHERE Albumid ="+ id;
        try
        {
            int c = stmt.executeUpdate(sql);

            if (c>0)
            {
               msg+="Album с идентификатором " + id +" успешно update!";
            }
            else
            {
                msg+="Album с идентификатором " + id +" не найдена!";
            }
        } catch (SQLException e)
        {
            System.out.println(" >> "+e.getMessage());
        }
        return msg;
    }
    public static String showAlbum() throws SQLException {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String msg = "";
        String sql = "SELECT Albumid, Name, artistid FROM album";
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            msg+="СПИСОК Album:";
            while (rs.next())
            {
                int id = rs.getInt("Albumid");
                String name = rs.getString("name");
                int arid = rs.getInt("artistid");
                msg+=" >> "+ id + " - " + name + " - " + arid+"\n";
            }
            rs.close();
        } catch (SQLException e)
        {
            msg+="ОШИБКА при получении списка Album";
            msg+=" >> "+e.getMessage();
        }
        return msg;
    }

    public static String getAlbumByName(String name) throws SQLException {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String msg = "";
        String sql = "SELECT *  FROM Album WHERE name = " + "'" + name + "'";
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            msg+="Album: ";
            while (rs.next())
            {
                int id = rs.getInt("Albumid");
                String arname = rs.getString("name");
                msg+=" >> "+ id + " - " + arname+"\n";
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
    }
    public static String getAlbumByArtistID(int arid) throws SQLException {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String sql = "SELECT *  FROM album WHERE artistid = " + arid;
        String msg = "";
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
           msg+="Album: ";
            while (rs.next())
            {

                Integer id = rs.getInt("albumid");
                String arname = rs.getString("name");
                msg+=" >> "+ id + " - " + arname+"\n";
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getAlbumNumberByArtistID(int arid) throws SQLException {
        connection =  DBConnection.getConnection();
        stmt = connection.createStatement();
        String sql = "SELECT COUNT(*) AS total FROM album WHERE artistid = " +  arid ;
        String msg = "";
        Integer count=0;
        try
        {
            ResultSet rs = stmt.executeQuery(sql);
            msg+="Count: ";
            while (rs.next())
            {
                count = rs.getInt("total");
                msg+=" >> "+ count.toString();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
