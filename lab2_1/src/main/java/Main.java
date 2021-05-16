

public class Main {
    public static void main(String[] args) throws Exception {
        MusicShop shop = new MusicShop();
        shop.loadFile("shop.xml");

        shop.addArtist(5, "Beyonce", "Giselle", 39);
        shop.addAlbum(51, "Halo", 200, 2010, "Pop", 3);
        shop.addArtist(4, "RiseAgainst", "Nick", 45);
        shop.addAlbum(41, "NowhereGeneration", 100, 2020, "Rock", 4);
        System.out.println(shop.getAllAlbums().toString());
        System.out.println(shop.getAllArtists().toString());

        shop.saveToFile("shop2.xml");
        System.out.println(shop.getAlbumByName("NowhereGeneration"));
        shop.changeAlbum(41, new Albums(0, null, 120, 0, null,2));
        System.out.println(shop.getAlbumById(41));
        System.out.println(shop.countAlbums("RiseAgainst"));
        shop.deleteAlbum(41);
        System.out.println(shop.countAlbums("RiseAgainst"));
        shop.deleteArtist(5);
        System.out.println(shop.getArtistByName("Beyonce"));
        System.out.println(shop.getArtistById(4));
    }
}
