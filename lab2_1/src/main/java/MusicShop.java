import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicShop {
    private static ArrayList<Artists> allArtists;
    private static ArrayList<Albums> allAlbums;

    public MusicShop (){
        allArtists = new ArrayList<>();
        allAlbums = new ArrayList<>();
    }

    public ArrayList<Albums> getAllAlbums() {
        return allAlbums;
    }

    public static ArrayList<String> getAllAlbumsName() {
        ArrayList<String> albumName = new ArrayList<>();
        for (Albums album : allAlbums) {
            albumName.add(album.name);
        }
        return albumName;
    }
    public static ArrayList<String> getAllArtistsName() {
        ArrayList<String> artistsName = new ArrayList<>();
        for (Artists artist : allArtists) {
            artistsName.add(artist.nickname);
        }
        return artistsName;
    }
    public ArrayList<Artists> getAllArtists() {
        return allArtists;
    }

    static class SimpleErrorHandler implements ErrorHandler {

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("Line " + exception.getLineNumber() + ":");
            System.out.println(exception.getMessage());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            System.out.println("Line " + exception.getLineNumber() + ":");
            System.out.println(exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println("Line " + exception.getLineNumber() + ":");
            System.out.println(exception.getMessage());
        }
    }

    public static void saveToFile(String filename){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement("shop");
            doc.appendChild(root);
            for (Artists artist : allArtists) {
                Element art = doc.createElement("artist");
                art.setAttribute("id", String.valueOf(artist.id));
                art.setAttribute("nickname", artist.nickname);
                art.setAttribute("realName",artist.realName);
                art.setAttribute("age", String.valueOf(artist.age));

                root.appendChild(art);
                for (Albums album : artist.albums) {
                    Element alb = doc.createElement("album");
                    alb.setAttribute("id", String.valueOf(album.id));
                    alb.setAttribute("name", album.name);
                    alb.setAttribute("genre", album.genre);
                    alb.setAttribute("price", String.valueOf(album.price));
                    alb.setAttribute("year", String.valueOf(album.year));
                    alb.setAttribute("artistId", String.valueOf(album.artistId));
                    art.appendChild(alb);
                }
            }

            Source domSource = new DOMSource(doc);
            Result fileRes = new StreamResult(new File(filename));
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(domSource, fileRes);
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void loadFile(String filename){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(new SimpleErrorHandler());
            Document doc = db.parse(new File(filename));
            Element root = doc.getDocumentElement();
            if (root.getTagName().equals("shop")) {
                NodeList artists = root.getElementsByTagName("artist");
                for (int i = 0; i < artists.getLength(); i++) {
                    Element artist = (Element) artists.item(i);
                    Integer artistId = Integer.parseInt(artist.getAttribute("id"));
                    String artistName = artist.getAttribute("nickname");
                    String artistRealName =artist.getAttribute("realName");
                    Integer artistAge = Integer.parseInt(artist.getAttribute("age"));
                    Artists newArtist = new Artists(artistId, artistName, artistRealName, artistAge);
                    allArtists.add(newArtist);
                    System.out.println( artistId + " " + artistName + " " + artistRealName + " " + artistAge);

                    NodeList albums = artist.getElementsByTagName("album");
                    for (int j = 0; j < albums.getLength(); j++) {
                        Element album = (Element) albums.item(j);
                        String albumName = album.getAttribute("name");
                        Albums newAlbum = new Albums(
                                Integer.parseInt(album.getAttribute("id")),
                                album.getAttribute("name"),
                                Integer.parseInt(album.getAttribute("price")),
                                Integer.parseInt(album.getAttribute("year")),
                                album.getAttribute("genre"),
                                Integer.parseInt(album.getAttribute("artistId"))
                        );
                        allAlbums.add(newAlbum);
                        Artists art = getArtistById(Integer.parseInt(album.getAttribute("artistId")));
                        art.albums.add(newAlbum);

                        System.out.println("       "+ albumName);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAlbum(int id, String name, int price, int year, String genre, int artistId) throws Exception {
        if (getAlbumById(id) != null) {
            throw new Exception("Can find Album");
        }
            allAlbums.add(new Albums(id, name, price, year, genre, artistId));
            Artists artist = getArtistById(artistId);
            artist.albums.add(new Albums(id, name, price, year, genre, artistId));
    }
    public static void addArtist(int id, String nickname, String realName, int age) throws Exception {
        if (getAlbumById(id) != null) {
            throw new Exception("Can find Album");
        }
            allArtists.add(new Artists(id, nickname, realName, age));
    }

    public Albums getAlbumByName(String name){
        for (Albums album : allAlbums) {
            if (album.name.equals(name)) {
                return album;
            }
        }
        return null;
    }

    public static Albums getAlbumById(int id){
        for (Albums album : allAlbums) {
            if (album.id == id) {
                return album;
            }
        }
        return null;
    }

    public static Artists getArtistById(int id){
        for (Artists artist : allArtists) {
            if (artist.id == id) {
                return artist;
            }
        }
        return null;
    }

    public static Artists getArtistByName(String name){
        for (Artists artist : allArtists) {
            if (artist.nickname.equals(name)) {
                return artist;
            }
        }
        return null;
    }

    public int countArtists(){
        return allArtists.size();
    }

    public  int countAlbums(String nickname){
        Artists artist = getArtistByName(nickname);
        return artist.albums.size();
    }

    public static void deleteAlbum(int id) throws Exception {
        Albums album = getAlbumById(id);
        if (album == null) {
            throw new Exception("Can't find Album");
        }
        Artists artist =getArtistById(album.artistId);

        allAlbums.remove(album);
        artist.albums.remove(album);
    }

    public static void deleteArtist(int id) throws Exception {
        Artists artist = getArtistById(id);
        if (artist == null) {
            throw new Exception("Can't find Artist");
        }
        for (int i = 0; i < allAlbums.size(); i++) {
            Albums album = allAlbums.get(i);
        if (album.artistId == id)
                allAlbums.remove(album);
        }
        allArtists.remove(artist);
    }
    public static void changeArtist(int id, Artists artist) throws Exception {
        Artists changedArtist = getArtistById(id);
        if (changedArtist != null) {
            changedArtist.nickname = artist.nickname;
        }
    }

    public static void changeAlbum(int id, Albums album) throws Exception {
        Albums changedAlbum = getAlbumById(id);
        if (album.name != null) {
            changedAlbum.name = album.name;
        }
        if (album.year!=0) {
            changedAlbum.year = album.year;
        }
        if (album.price!=0) {
            changedAlbum.price = album.price;
        }
    }
}

