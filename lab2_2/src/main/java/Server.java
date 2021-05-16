
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    private ServerSocket server = null;
    private ClientSocket clientSocket = null;
    private PrintWriter out;
    private BufferedReader in;
    protected boolean isStopped = false;


    public void run(int port) throws IOException, SQLException, ClassNotFoundException {
        server = new ServerSocket(port);

        while (true) {

            Socket socket = null;
            try {
                socket = server.accept();
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                if (this.isStopped) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            String msg = null;
            try {
                msg = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Received message " + msg);
            processQuery(msg);
            System.out.println("Server Finished");
        }
    }

    private void processQuery(String str) throws SQLException, ClassNotFoundException {
        ArrayList<String> args = new ArrayList<>();
        String[] array = str.split(" ");
        args.add(array[0]);
        for (int i = 1; i < array.length; i++) {
            args.add(array[i]);
        }
        String msg="";
            switch (args.get(0)) {
                case "1":
                    msg=ArtistDAO.addArtist(Integer.parseInt(args.get(1)),args.get(2),args.get(3),Integer.parseInt(args.get(4)));
                    out.println(msg);
                    break;
                case "2":
                    msg=ArtistDAO.deleteArtist(Integer.parseInt(args.get(1)));
                    out.println(msg);
                    break;
                case "3":
                    msg=AlbumDAO.addAlbum(Integer.parseInt(args.get(1)),args.get(2),Integer.parseInt(args.get(3)),Integer.parseInt(args.get(4)),args.get(5),Integer.parseInt(args.get(6)));
                    out.println(msg);
                    break;
                case "4":
                    msg=AlbumDAO.deleteAlbum(Integer.parseInt(args.get(1)));
                    out.println(msg);
                    break;
                case "5":
                    msg=ArtistDAO.updateArtist(Integer.parseInt(args.get(1)),Integer.parseInt(args.get(2)));
                    out.println(msg);
                    break;
                case "6":
                    msg=AlbumDAO.getAlbumNumberByArtistID(Integer.parseInt(args.get(1)));
                    out.println(msg);
                    break;
                case "7":
                    msg=AlbumDAO.showAlbum();
                    out.println(msg);
                    break;
                case "8":
                    msg=AlbumDAO.getAlbumByArtistID(Integer.parseInt(args.get(1)));
                    out.println(msg);
                    break;
                case "9":
                    msg=ArtistDAO.showArtist();
                    out.println(msg);
                    break;
                case "10":
                    msg=AlbumDAO.updateAlbum(Integer.parseInt(args.get(1)),Integer.parseInt(args.get(2)));
                    out.println(msg);
                    break;
            }
            out.flush();
    }


    public static void main(String[] args)
    {
        try
        {
            Server srv = new Server();
            srv.run(8082);
        }
        catch(IOException | SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("Возникла ошибка");
        }
    }
}



