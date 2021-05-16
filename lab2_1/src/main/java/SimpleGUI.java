import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimpleGUI extends JFrame{
    private JComboBox allArtistsName;
    private JLabel artistId;
    private JLabel artistNickname;
    private JLabel artistRealName;
    private JLabel artistAge;
    private JComboBox albumsName;
    private JLabel albumId;
    private JLabel albumName;
    private JLabel albumYear;
    private JLabel albumPrice;
    private JLabel albumGener;

    private JButton AddArtist;
    private JButton AddAlbum;
    private JButton DeleteArtist;
    private JButton DeleteAlbum;
    private JButton ChangeArtist;
    private JButton ChangeAlbum;
    private JButton Save;
    private JButton Load;

    private JTextField artistIdText;
    private JTextField artistNicknameText;
    private JTextField artistRealNameText;
    private JTextField artistAgeText;
    private JTextField albumIdText;
    private JTextField albumNameText;
    private JTextField albumYearText;
    private JTextField albumPriceText;
    private JTextField albumGenerText;

public SimpleGUI(MusicShop shop){
    createGUI(shop);
}
    public void createGUI(MusicShop shop) {
        JFrame frame = new JFrame("Shop");
        // Определяем размеры
        frame.setSize(900, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)dimension.getWidth()/4, (int)dimension.getHeight()/4);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());

        allArtistsName = new JComboBox(MusicShop.getAllArtistsName().toArray());
        artistId = new JLabel("Artist ID");
        artistNickname = new JLabel("Artist Name");
        artistRealName = new JLabel("Artist RealName");
        artistAge = new JLabel("Artist Age");
        albumsName = new JComboBox(MusicShop.getAllAlbumsName().toArray(new String[0]));
        albumId = new JLabel("Album ID");
        albumName = new JLabel("Album Name");
        albumYear = new JLabel("Album Year");
        albumPrice = new JLabel("Album Price");
        albumGener = new JLabel("Album Gener");
        AddArtist = new JButton("Add Artist");
        AddAlbum = new JButton("Add Album");
        DeleteArtist = new JButton("Delete Artist");
        DeleteAlbum = new JButton("Delete Album");
        ChangeArtist = new JButton("Change Artist");
        ChangeAlbum = new JButton(" Change Album");
        Save = new JButton("Save");
        Load = new JButton("Load");

        artistIdText = new JTextField(null, null, 4);
        artistNicknameText = new JTextField(null, null, 12);
        artistRealNameText = new JTextField(null, null, 12);
        artistAgeText = new JTextField(null, null, 4);
        albumIdText = new JTextField(null, null, 4);
        albumNameText = new JTextField(null, null, 12);
        albumYearText = new JTextField(null, null, 4);
        albumPriceText = new JTextField(null, null, 4);
        albumGenerText = new JTextField(null, null, 8);

        panel.add(allArtistsName);
        panel.add(artistId);
        panel.add(artistIdText);
        panel.add(artistNickname);
        panel.add(artistNicknameText);
        panel.add(artistRealName);
        panel.add(artistRealNameText);
        panel.add(artistAge);
        panel.add(artistAgeText);

        panel2.add(albumsName);
        panel2.add(albumId);
        panel2.add(albumIdText);
        panel2.add(albumName);
        panel2.add(albumNameText);
        panel2.add(albumGener);
        panel2.add(albumGenerText);
        panel2.add(albumPrice);
        panel2.add(albumPriceText);
        panel2.add(albumYear);
        panel2.add(albumYearText);

        panel3.add(AddArtist);
        panel3.add(AddAlbum);
        panel3.add(DeleteAlbum);
        panel3.add(DeleteArtist);
        panel3.add(ChangeAlbum);
        panel3.add(ChangeArtist);
        panel3.add(Load);
        panel3.add(Save);

        ActionListener actionListener = new TestActionListener();
        albumsName.addActionListener(actionListener);
        allArtistsName.addActionListener(actionListener);
        AddAlbum.addActionListener(e -> {
                    try {
                        MusicShop.addAlbum(Integer.parseInt(albumIdText.getText()), albumNameText.getText(), Integer.parseInt(albumPriceText.getText()), Integer.parseInt(albumYearText.getText()), albumGenerText.getText(), Integer.parseInt(artistIdText.getText()));
                        System.out.println(shop.getAllAlbums().toString());
                        albumsName.addItem(albumNameText.getText());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
        AddArtist.addActionListener(e -> {
                    try {
                        MusicShop.addArtist(Integer.parseInt(artistIdText.getText()), artistNicknameText.getText(), artistRealNameText.getText(), Integer.parseInt(artistAgeText.getText()));
                        System.out.println(shop.getAllArtists().toString());
                        allArtistsName.addItem(artistNicknameText.getText());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
        DeleteAlbum.addActionListener(e -> {
                    try {
                        ArrayList<Albums> albums = shop.getAllAlbums();
                        int id = 0;
                        id = Integer.parseInt(albumIdText.getText()) % 10 - 1;
                        shop.deleteAlbum(Integer.parseInt(albumIdText.getText()));
                        System.out.println(shop.getAllAlbums().toString());

                        albumsName.removeItemAt(id);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
        DeleteArtist.addActionListener(e -> {
                    try {
                        Artists deleteArtist = shop.getArtistById(Integer.parseInt(artistIdText.getText()));
                        if(!deleteArtist.albums.isEmpty()) {
                            for (Albums alb : deleteArtist.albums) {
                                albumsName.removeItem(alb.name);
                            }
                        }
                        shop.deleteArtist(deleteArtist.id);
                        allArtistsName.removeItem(deleteArtist.nickname);
                        System.out.println(shop.getAllArtists().toString());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
        ChangeAlbum.addActionListener(e -> {
                    try {
                        shop.changeAlbum(Integer.parseInt(albumIdText.getText()), new Albums(Integer.parseInt(albumIdText.getText()), albumNameText.getText(), Integer.parseInt(albumPriceText.getText()), 0, null, Integer.parseInt(artistIdText.getText())));
                        System.out.println(shop.getAllAlbums());
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(shop.getAllAlbumsName().toArray(new String[0]));
                        albumsName.setModel(model);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
        ChangeArtist.addActionListener(e -> {
                    try {
                        shop.changeArtist(Integer.parseInt(artistIdText.getText()), new Artists(Integer.parseInt(artistIdText.getText()), artistNicknameText.getText(), null, 0));
                        System.out.println(shop.getAllArtists());
                        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
                        for (Artists artist2: shop.getAllArtists()) {
                            model2.addElement(artist2.nickname);
                        }
                        allArtistsName.setModel(model2);

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
        Save.addActionListener(e -> {
                    try {
                        shop.saveToFile("shop1.xml");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );
        Load.addActionListener(e -> {
                    try {
                        shop.loadFile("shop.xml");
                        SimpleGUI frame2 = new SimpleGUI(shop);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        );

        allArtistsName.addActionListener(e -> {
            try {
                Artists selectedArtist = shop.getArtistByName(allArtistsName.getSelectedItem().toString());
                artistIdText.setText(String.valueOf(selectedArtist.id));
                artistNicknameText.setText(selectedArtist.nickname);
                artistRealNameText.setText(selectedArtist.realName);
                artistAgeText.setText(String.valueOf(selectedArtist.age));
                DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
                for (Albums artistAlbum: selectedArtist.albums) {
                    model2.addElement(artistAlbum.name);
                }
                albumsName.setModel(model2);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        albumsName.addActionListener(e -> {
            try {
                Albums selectedAlbum = shop.getAlbumByName(albumsName.getSelectedItem().toString());
                albumIdText.setText(String.valueOf(selectedAlbum.id));
                albumNameText.setText(selectedAlbum.name);
                albumYearText.setText(String.valueOf(selectedAlbum.year));
                albumPriceText.setText(String.valueOf(selectedAlbum.price));
                albumGenerText.setText(selectedAlbum.genre);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        frame.add(panel3,BorderLayout.PAGE_END );
        frame.add(panel2, BorderLayout.PAGE_START);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);

    }
    public void setAlbumsName(ArrayList<String> albumsName) {
        this.albumsName = new JComboBox(albumsName.toArray());
    }
    public class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }
    }

    public static void main(String[] args) {
        MusicShop shop = new MusicShop();
        //shop.loadFile("shop.xml");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new SimpleGUI(shop);
            }
        });
    }


}