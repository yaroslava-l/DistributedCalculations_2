import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    private final static String url = "jdbc:postgresql://localhost:5432/musicShop";
    private final static String user = "postgres";
    private final static String password = "1234";

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, password);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
      /*  ArtistDAO.addArtist(9, "k", "i", 49);
        ArtistDAO.addArtist(55, "o", "i", 40);
        ArtistDAO.deleteArtist(9);
        ArtistDAO.updateArtist(55, 7);
        ArtistDAO.showArtist();
        ArtistDAO.getArtistByName("k");
       */
        AlbumDAO.addAlbum(9, "k", 900,2009,"i", 55);
        AlbumDAO.addAlbum(55, "o", 800,2008, "i", 55);
        AlbumDAO.deleteAlbum(9);
        AlbumDAO.updateAlbum(55, 7);
        AlbumDAO.showAlbum();
        AlbumDAO.getAlbumByName("o");
        AlbumDAO.getAlbumNumberByArtistID(55);
        AlbumDAO.getAlbumByArtistID(55);
    }

}