import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.*;
import javax.sound.sampled.*;

public class Player extends Sprite {

    private int dx;
    public int count;
    private int dy;
    private List<Missile> missiles;
    private InputStream stream;
    private SimpleSoundPlayer sound;
    private AudioFormat format;
    //File file = new File("biu.wav");
    //AudioInputStream stream = AudioSystem.getAudioInputStream(file);
    //AudioFormat format = stream.getFormat( );
    //byte[] samples = getSamples(stream);
/*
    private byte[] getSamples(AudioInputStream audioStream) {
        // get the number of bytes to read
        int length = (int)(audioStream.getFrameLength() * format.getFrameSize());
        // read the entire stream
        byte[] samples = new byte[length];
        DataInputStream is = new DataInputStream(audioStream);
        try {
        is.readFully(samples);
        }
        catch (IOException ex) {
        ex.printStackTrace();
        }
        // return the samples
        return samples;
    }
    */

    public Player(int x, int y) {
        super(x, y);
        initPlayer();
    }

    private void initPlayer() {
        
        missiles = new ArrayList<>();
        dx = 0;
        dy = 0;
        count = 0;
        loadImage("images/player.png");

        getImageDimensions();
    }

    public void move() {

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (x > 480) {
            x = 480;
        }

        if (y < 1) {
            y = 1;
        }

        if (y > 660) {
            y = 660;
        }

        dx = 0;
        dy = 0;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void mousePressed(MouseEvent e) {
        
        if (count > 0) fire();
        count++;
    }

    public void mouseMoved(MouseEvent e) {
        if (count > 0) {
            dx = e.getX() - x;
            dy = e.getY() - y;
        }
        
        /*
        dx = e.getX() - x;
        dy = e.getY() - y;
        */
        //x += dx;
        //y += dy;
      }

/*
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }
    */

    public void fire() {
        sound = new SimpleSoundPlayer("biu.wav");
        /*
        try {
            AudioInputStream s =
                AudioSystem.getAudioInputStream(
                new File("biu.wav"));
            format = s.getFormat();
            stream = new ByteArrayInputStream(sound.getSamples());
        }
        catch (Exception e) {

        } */
        sound.start();
        missiles.add(new Missile(x + width/3, y - height / 2));
    }

    /*
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
*/
}