import java.awt.Color;
import java.awt.Dimension;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private Player player;
    private List<Centipede> centipedes;
    private Spider spider;
    private DyingSpider dyingspider;
    private List<Mushroom> mushrooms;
    private List<HalfMushroom> halfmushrooms;
    private List<DyingMushroom> dyingmushrooms;
    private boolean ingame;
    public int score;
    private int life;
    private final int ICRAFT_X = 250;
    private final int ICRAFT_Y = 680;
    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 700;
    private final int DELAY = 15;
    private int count;
    private ArrayList<Coord> pos;

    /*
    private final int[][] pos = {
        {400, 30}, {320, 70}, {200, 90},
        {200, 110}, {100, 150}, {280, 250},
        {120, 270}, {360, 50}, {240, 150},
        {220, 210}, {20, 50}, {60, 70},
        {440, 150}, {340, 90}, {60, 230},
        {240, 50}, {440, 70}, {220, 170},
        {120, 250}, {420, 50}, {340, 90},
        {20, 210}, {120, 10}, {60, 190},
        {40, 130}, {480, 170}, {220, 30}
    };
    */

    public Board() {
        initBoard();
    }

    private void initBoard() {

        addMouseListener(new MAdapter());
        addMouseMotionListener(new MMAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;
        count = 0;
        score = 0;
        life = 3;

        setSize(new Dimension(B_WIDTH, B_HEIGHT));
        //setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        player = new Player(ICRAFT_X, ICRAFT_Y);

        initCoord(0.1);
        initMushrooms();
        initHalfMushrooms();
        initDyingMushrooms();
        initSpider();
        initDyingSpider();
        initCentipedes();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initCoord(double n)
    {
        Random r = new Random();
        pos = new ArrayList<>();
        for (int i = 0; i < 500; i += 20)
        {
            for (int j = 30; j < 600; j += 20)
            {
                double rr = r.nextDouble();

                if (rr <= n && !pos.contains(new Coord(i - 20, j - 20)) && !pos.contains(new Coord(i + 20, j - 20))) {
                    pos.add(new Coord(i, j));
                }
            }
        }
    }

    public void initCentipedes()
    {
        centipedes = new ArrayList<>();
        centipedes.add(new Centipede(B_WIDTH, 10));
    }

    public void initDyingSpider()
    {
        dyingspider = new DyingSpider(-1, -1);
        dyingspider.setVisible(false);
    }

    public void initSpider()
    {
        spider = new Spider();
    }

    public void initHalfMushrooms() {
        halfmushrooms = new ArrayList<>();
    }

    public void initDyingMushrooms() {
        dyingmushrooms = new ArrayList<>();
    }

    public void initMushrooms() {
        
        mushrooms = new ArrayList<>();

        for (Coord p : pos) {
            mushrooms.add(new Mushroom(p.x, p.y));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {

            drawObjects(g);

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(),
                    this);
        }

        List<Missile> ms = player.getMissiles();

        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(), 
                        missile.getY(), this);
            }
        }

        for (Mushroom mushroom : mushrooms) {
            if (mushroom.isVisible()) {
                g.drawImage(mushroom.getImage(), mushroom.getX(), mushroom.getY(), this);
            }
        }

        for (HalfMushroom m : halfmushrooms) {
            if (m.isVisible()) {
                g.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
        }

        for (DyingMushroom d : dyingmushrooms) {
            if (d.isVisible()) {
                g.drawImage(d.getImage(), d.getX(), d.getY(), this);
            }
        }
        
        if (spider.isVisible())
        {
            g.drawImage(spider.getImage(), spider.getX(), spider.getY(), this);
        }

        if (dyingspider.isVisible())
        {
            g.drawImage(dyingspider.getImage(), dyingspider.getX(), dyingspider.getY(), this);
        }

        for (Centipede centipede: centipedes)
        {
            if (centipede.isVisible())
            {
                g.drawImage(centipede.getImage(), centipede.getX(), centipede.getY(), this);
            }
        }
        

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 5, 15);
        g.drawString("Life: " + life, 5, 30);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        count %= 102;
        inGame();

        if (player.count > 0) {

        updatePlayer();
        int op = ThreadLocalRandom.current().nextInt(0, 4);
        if (count % 102 == 0)
        {
            op = ThreadLocalRandom.current().nextInt(0, 4);
        }
        updateSpider(op);
        for (int i = 0; i < 6; i++)
        {
            updateDyingSpider();
        }
        updateMissiles();
        updateMushrooms();
        updateHalfMushrooms();
        updateDyingMushrooms();
        int f = 0;
        for (Centipede c : centipedes) {
            if (c.isVisible()) {
                f = 1;
                break;
            }
        }
        if (f == 0) {
            initCentipedes();
            score += 600;
        }
        else updateCentipede();

        checkCollisions();

        repaint();
        }
    }

    private void inGame() {
        if (!ingame) {
            timer.stop();
        }
    }

    private void updateCentipede() {
        for (Centipede centipede : centipedes)
        {
            if (centipede.isVisible() && count % 3 == 0) {
                centipede.move(this);
            }
        }
        if (centipedes.size() < 40)
        {
            centipedes.add(new Centipede(380, 10));
        }
    }

    private void updateSpider(int op) {
        if (spider.isVisible()) {
            spider.move(op);
        }
    }

    private void updateDyingSpider() {
        if (dyingspider.isVisible()) {
            dyingspider.move();
        }
    }


    private void updatePlayer() {

        if (player.isVisible()) {       
            player.move();
        }
    }

    private void updateMissiles() {

        List<Missile> ms = player.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    private void updateMushrooms() {

        if (mushrooms.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < mushrooms.size(); i++) {

            Mushroom a = mushrooms.get(i);
            
            if (a.isVisible()) {
            } else {
                int xx = a.getX();
                int yy = a.getY();
                mushrooms.remove(i);
                halfmushrooms.add(new HalfMushroom(xx, yy));
            }
        }
    }

    private void updateHalfMushrooms() {

        for (int i = 0; i < halfmushrooms.size(); i++) {

            HalfMushroom a = halfmushrooms.get(i);
            
            if (a.isVisible()) {
            } else {
                int xx = a.getX();
                int yy = a.getY();
                halfmushrooms.remove(i);
                dyingmushrooms.add(new DyingMushroom(xx, yy));
            }
        }
    }

    private void updateDyingMushrooms() {

        for (int i = 0; i < dyingmushrooms.size(); i++) {

            DyingMushroom a = dyingmushrooms.get(i);
            
            if (a.isVisible()) {
                
            } else {
                dyingmushrooms.remove(i);   
            }
        }
    }

    public void restoreMushrooms() {
        int s = halfmushrooms.size();
        for (int i = 0; i < s; i++)
        {
            HalfMushroom half = halfmushrooms.get(0);
            int xx = half.getX();
            int yy = half.getY();
            halfmushrooms.remove(0);
            mushrooms.add(new Mushroom(xx, yy));
            score += 10;
        }
        s = dyingmushrooms.size();
        for (int i = 0; i < s; i++)
        {
            DyingMushroom d = dyingmushrooms.get(0);
            int xx = d.getX();
            int yy = d.getY();
            dyingmushrooms.remove(0);
            mushrooms.add(new Mushroom(xx, yy));
            score += 10;
        }
    }

    public void checkCollisions() {

        Rectangle r3 = player.getBounds();

        if (spider.isVisible())
        {
            Rectangle r2 = spider.getBounds();
            if (r3.intersects(r2)) {
                if (life > 1) {
                    life -= 1;
                    restoreMushrooms();
                    dyingspider.setVisible(false);
                    initSpider();
                    player = new Player(ICRAFT_X, ICRAFT_Y);
                    return;
                }
                else {
                    ingame = false;
                    return;
                }
            }
        }

        for (Centipede c : centipedes)
        {
            if (c.isVisible()) {
                Rectangle r5 = c.getBounds();
                if (r5.intersects(r3)) {
                    if (life > 1) {
                        life -= 1;
                        dyingspider.setVisible(false);
                        initSpider();
                        restoreMushrooms();
                        player = new Player(ICRAFT_X, ICRAFT_Y);
                        return;
                    }
                    else {
                        ingame = false;
                        return;
                    }
                }
            }
        }

        if (dyingspider.isVisible())
        {
            Rectangle r2 = dyingspider.getBounds();
            if (r3.intersects(r2)) {
                if (life > 1) {
                    life -= 1;
                    restoreMushrooms();
                    dyingspider.setVisible(false);
                    initSpider();
                    player = new Player(ICRAFT_X, ICRAFT_Y);
                    return;
                }
                else {
                    ingame = false;
                    return;
                }
            }
        }

        List<Missile> ms = player.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();
            if (dyingspider.isVisible())
            {
                Rectangle r4 = dyingspider.getBounds();

                if (r1.intersects(r4)) {
                    dyingspider.setVisible(false);
                    m.setVisible(false);
                    score += 600;
                    continue;
                }
            }

            if (spider.isVisible())
            {
                Rectangle r4 = spider.getBounds();

                if (r1.intersects(r4)) {
                    spider.setVisible(false);
                    m.setVisible(false);
                    score += 100;
                    int xx = spider.getX();
                    int yy = spider.getY();
                    dyingspider = new DyingSpider(xx, yy);
                    continue;
                }
            }
            

            for (Mushroom mushroom : mushrooms) {

                Rectangle r2 = mushroom.getBounds();

                if (r1.intersects(r2)) {
                    mushroom.setVisible(false);
                    m.setVisible(false);
                    score += 1;
                    continue;
                }
            }

            for (HalfMushroom half : halfmushrooms) {

                Rectangle r2 = half.getBounds();

                if (r1.intersects(r2)) {
                    
                    half.setVisible(false);
                    m.setVisible(false);
                    score += 1;
                    continue;
                }
            }

            for (DyingMushroom d : dyingmushrooms) {

                Rectangle r2 = d.getBounds();

                if (r1.intersects(r2)) {
                    score += 5;
                    d.setVisible(false);
                    m.setVisible(false);
                    continue;
                }
            }

            for (Centipede c : centipedes) {
                if (c.isVisible()) {
                    Rectangle r2 = c.getBounds();
                    if (r1.intersects(r2)) {
                        c.update(this);
                        m.setVisible(false);
                        continue;
                    }
                }
            }
        }
    }

    public boolean checkMush(int x, int y)
    {
        for (Mushroom m : mushrooms)
        {
            if (m.x == x && m.y == y) return false;
        }
        for (HalfMushroom m : halfmushrooms)
        {
            if (m.x == x && m.y == y) return false;
        }
        for (DyingMushroom m : dyingmushrooms)
        {
            if (m.x == x && m.y == y) return false;
        }
        return true;
    }

    /*

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }

    */

    private class MAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            player.mousePressed(e);
        }
/*
        @Override
        public void mouseDragged(MouseEvent e) {
            player.mouseDragged(e);
        }
        */
    }

    private class MMAdapter extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            player.mouseMoved(e);
        }
    }
}


