public class Albums {
    public int id;
    public String name;
    public int price;
    public int year;
    public String genre;
    public int artistId;

    public Albums(int id, String name, int price, int year, String genre, int artistId)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.year = year;
        this.genre = genre;
        this.artistId = artistId;
    }
    @Override
    public String toString(){
        return id + " " + name+ " (" + price + ", " + year + ", " + genre+ ")";
    }
}
