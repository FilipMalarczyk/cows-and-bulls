import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//I DONE SOMETHING!!!

public class userInterface extends JFrame{

    // stuff for inputs.
    private static JTextField[] digitFields;
    public void Main() {
        int numberOfDigits = 4;
        digitFields = new JTextField[numberOfDigits];
        int xPosition = 250;
        int yPosition = 500;

        for (int i = 0; i < numberOfDigits; i++) {
            JTextField field = new JTextField(1);
            field.setBounds(xPosition + (i * 50), yPosition, 40, 40);
            field.setHorizontalAlignment(JTextField.CENTER);
            field.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char input = e.getKeyChar();
                    if ((input < '0' || input > '9') && input != '\b') {
                        e.consume();
                    }
                }

                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        submitGuess();
                    }
                }
            });
            this.add(field);
            digitFields[i] = field;
        }
    }

    private static void submitGuess() {
        StringBuilder guessBuilder = new StringBuilder();
        for (JTextField field : digitFields) {
            guessBuilder.append(field.getText());
        }
        System.out.println(guessBuilder);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Main::new);

        final int[] cows = {0};
        final int[] bulls = {0};

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        int cowBoundX = width/32;
        int bullBoundX = width/4 * 3;
        int cowBoundY = height/2 - height/8;
        int bullBoundY = height/2 - height/8;



        JFrame frame = new JFrame("Bulls and Cows");
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        Button addCow = new Button("Add cow");
        Button removeCow = new Button("Remove cow");
        Button addBull = new Button("Add bull");
        Button removeBull = new Button("Remove bull");
        Button startBasic = new Button("Start Basic game");

        startBasic.setBounds(100,140,100,20);
        addCow.setBounds(100,20,100,20);
        removeCow.setBounds(100,50,100,20);
        addBull.setBounds(100,80,100,20);
        removeBull.setBounds(100,110,100,20);

        frame.add(startBasic);
        frame.add(addCow);
        frame.add(removeCow);
        frame.add(addBull);
        frame.add(removeBull);
        JPanel panel = new MyPanel(cows, bulls, cowBoundX, cowBoundY, bullBoundX, bullBoundY);

        addCow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cows[0] += 1;
                panel.repaint();
            }
        });
        removeCow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cows[0] != 0 ) {
                    cows[0] -= 1;
                    panel.repaint();
                }
            }
        });
        addBull.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bulls[0] += 1;
                panel.repaint();
            }
        });
        removeBull.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bulls[0] != 0) {
                    bulls[0] -= 1;
                    panel.repaint();
                }
            }
        });



        frame.add(panel);
        frame.setVisible(true);
    }



}


class MyPanel extends JPanel {
    private BufferedImage cowImage, bullImage;
    private boolean drawCow = true;
    int[] cows, bulls;
    int cowBoundX, cowBoundY, bullBoundX, bullBoundY;

    public MyPanel(int[] cows, int[] bulls, int cowBoundX, int cowBoundY, int bullBoundX, int bullBoundY) {
        this.cows = cows;
        this.bulls = bulls;
        this.cowBoundX = cowBoundX;
        this.cowBoundY = cowBoundY;
        this.bullBoundX = bullBoundX;
        this.bullBoundY = bullBoundY;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        setBounds(0, 0, width, height);

        try {
            cowImage = ImageIO.read(new File("C:\\Users\\harri\\Documents\\bulls and cows\\src\\cow.png"));
            bullImage = ImageIO.read(new File("C:\\Users\\harri\\Documents\\bulls and cows\\src\\bull.png"));
        } catch (IOException e) {
            System.out.println("Failed to load images");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawAnimals(g2d, cowImage, cows[0], cowBoundX, cowBoundY);
        drawAnimals(g2d, bullImage, bulls[0], bullBoundX, bullBoundY);
    }

    private void drawAnimals(Graphics2D g2d, BufferedImage image, int count, int boundX, int boundY) {
        int gridSize = (int) Math.ceil(Math.sqrt(count));
        int imageWidth = (gridSize > 0) ? 400 / gridSize : 0;
        int imageHeight = (gridSize > 0) ? 400 / gridSize : 0;

        for (int i = 0; i < count; i++) {
            int row = i / gridSize;
            int col = i % gridSize;
            int x = boundX + col * imageWidth;
            int y = boundY + row * imageHeight;
            g2d.drawImage(image, x, y, imageWidth, imageHeight, this);
        }
    }



}





