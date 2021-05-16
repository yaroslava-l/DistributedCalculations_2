import java.util.ArrayList;

public class Artists {
    public int id;
    public String nickname;
    public String realName;
    public int age;
    public ArrayList <Albums> albums;
    public Artists(int id, String nickname, String realName, int age)
    {
        this.id=id;
        this.nickname=nickname;
        this.realName=realName;
        this.age=age;
        this.albums = new ArrayList<>();
    }
    public ArrayList<Albums> getAlbums() {
        if (albums == null) {
            albums = new ArrayList<Albums>();
        }
        return this.albums;
    }
    @Override
    public String toString(){
        return id + " " + nickname+  " " + realName + " " + age;
    }
}
