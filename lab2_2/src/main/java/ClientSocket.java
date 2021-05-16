import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 8082);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner s = new Scanner(System.in);

        System.out.println("1. Добавление нового исполнителя\n" +
                "2. Удаление исполнителя\n" +
                "3. Добавление нового альбома заданного исполнителя\n" +
                "4. Удаление альбома\n" +
                "5. Редактирование данных об исполнителе\n" +
                "6. Подсчет количества альбомов исполнителя\n" +
                "7. Выдача полного списка альбомов с указанием исполнителя\n" +
                "8. Выдача списка альбомов заданного исполнителя\n" +
                "9. Выдача полного списка исполнителей" +
                "10. Редактирование данных альбома\n" );
        int var = s.nextInt();
        switch (var) {
            case 1 -> {
                System.out.println("Add Artist:");
                int artistID = s.nextInt();
                String name = s.next();
                String realName = s.next();
                int age = s.nextInt();
                sendMessage("1 " + artistID + " " + name + " " + realName + " " + age);

                break;
            }
            case 2 -> {
                System.out.println("Delete artist:");
                int id = s.nextInt();
                sendMessage("2 " + id);

                break;
            }
            case 3 -> {
                System.out.println("Add Album:");
                int albumID = s.nextInt();
                String name = s.next();
                int price = s.nextInt();
                int year = s.nextInt();
                String gener = s.next();
                int artistID = s.nextInt();
                sendMessage("3 " + albumID+" "+name+" "+price+" "+year+" "+gener+" "+artistID);
                break;
            }
            case 4 -> {
                System.out.println("Delete Album:");
                int albumID = s.nextInt();
                sendMessage("4 " + albumID);
                break;
            }

            case 5 -> {
                System.out.println("Update Artist:");
                int artistID = s.nextInt();
                int age = s.nextInt();
                sendMessage("5 " + artistID+" "+age);
                break;
            }
            case 6 -> {
                System.out.println("Album count:");
                int id = s.nextInt();
                sendMessage("6 " + id);
                break;
            }
            case 7 -> {
                System.out.println("Show Album:");
                sendMessage("7 ");
                break;
            }
            case 8 -> {
                System.out.println("Show Album by Artist:");
                int artistid = s.nextInt();
                sendMessage("8 "+ artistid);
                break;
            }
            case 9 -> {
                System.out.println("Show Artist:");
                sendMessage("9 ");
                break;
            }
            case 10 -> {
                System.out.println("Update Album:");
                int albumID = s.nextInt();
                int price = s.nextInt();
                sendMessage("10 " + albumID+" "+price);
                break;
            }
            default -> {
                return;
            }
        }
        try {
            System.out.println("Close");
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sendMessage(String msg) throws IOException {
        out.println(msg);
        StringBuilder res = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}
