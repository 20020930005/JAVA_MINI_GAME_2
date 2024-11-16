import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Whacky {
    int boardwidth = 600;
    int boardheight = 650;

    JFrame frame = new JFrame("Whacky the Xmas: click only on your cake");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];

    ImageIcon santa;
    ImageIcon thiefdeer;
    ImageIcon gift;

    JButton currentsanta = null;
    JButton currentdeer = null;
    JButton currentgift = null;
    Random random = new Random();
    Timer setSantaTimer;
    Timer setDeerTimer;
    Timer setTimerGift;
    int score;

    Whacky() {
        frame.setSize(boardwidth, boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel);

        // Load images
        Image thiefDeerImg = new ImageIcon(getClass().getResource("/reindeer.png")).getImage();
        thiefdeer = new ImageIcon(thiefDeerImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        Image santaImg = new ImageIcon(getClass().getResource("/santa-claus.png")).getImage();
        santa = new ImageIcon(santaImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        Image giftImg = new ImageIcon(getClass().getResource("/cake.png")).getImage();
        gift = new ImageIcon(giftImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        score = 0;

        // Initialize buttons
        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);

            //tile.setBackground(Color.LIGHT_GRAY); // Change to your desired background color
            //tile.setForeground(Color.RED);

            tile.setFocusable(false);
            tile.addActionListener(e -> {
                JButton clickedTile = (JButton) e.getSource();
                if (clickedTile == currentgift) {
                    score += 10;
                    textLabel.setText("Cake-Score: " + score);
                } else if (clickedTile == currentdeer || clickedTile == currentsanta) {
                    textLabel.setText("cake only-Game Over: " + score);
                    setSantaTimer.stop();
                    setDeerTimer.stop();
                    setTimerGift.stop();
                    for (JButton b : board) {
                        b.setEnabled(false);
                    }
                }
            });
        }

        // Timer for Santa
        setSantaTimer = new Timer(1000, e -> {
            if (currentsanta != null) {
                currentsanta.setIcon(null);
            }
            int num = random.nextInt(9);
            currentsanta = board[num];
            currentsanta.setIcon(santa);

        });

        // Timer for Deer
        setDeerTimer = new Timer(1500, e -> {
            if (currentdeer != null) {
                currentdeer.setIcon(null);
            }
            int num = random.nextInt(9);
            currentdeer = board[num];
            currentdeer.setIcon(thiefdeer);

        });

        // Timer for Gift
        setTimerGift = new Timer(2200, e -> {
            if (currentgift != null) {
                currentgift.setIcon(null);
            }
            int num = random.nextInt(9);
            currentgift = board[num];
            currentgift.setIcon(gift);
        });

        setSantaTimer.start();
        setDeerTimer.start();
        setTimerGift.start();

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Whacky();
    }
}
