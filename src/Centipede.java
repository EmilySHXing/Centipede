public class Centipede extends Sprite {

    private final int BOARD_WIDTH = 500;
    private final int SPEED = 20;
    private int f;
    private int life;

    public Centipede(int x, int y) {
        super(x, y);
        
        initCentipede();
    }

    public void update(Board b) {
        if (life == 0) {
            b.score += 5;
            setVisible(false);
        }
        if (life == 1) {
            b.score += 2;
            life--;
            loadImage("images/centipede2.png");
            getImageDimensions();
        }
    }
    
    private void initCentipede() {
        f = 1;
        life = 1;
        loadImage("images/centipede.png");  
        getImageDimensions();
    }

    public void move(Board b) {
        x -= SPEED * f;
        if (x < 0 || x >= BOARD_WIDTH) {
            if (y < 600) {
                y += SPEED;
                f *= -1;
                x -= SPEED * f;
            }
            else {
                f *= -1;
                x -= SPEED * f;
            }
        }
        else if (!b.checkMush(x, y)) {
            y += SPEED;
            f *= -1;
            x -= SPEED * f;
        }
    }
}