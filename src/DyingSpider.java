import java.util.concurrent.ThreadLocalRandom;

public class DyingSpider extends Sprite {
    public DyingSpider(int x, int y) {
        super(x, y);
        initDyingSpider();
    }

    private void initDyingSpider() {
        loadImage("images/dyingspider.png");
        getImageDimensions();
    }

    public void move() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
        int d = ThreadLocalRandom.current().nextInt(1, 6);
        switch (randomNum) {
            case 0: {
                if (x > d) {
                    x -= d;
                }
                else {
                    x += d;
                }
                break;
            }
            case 1: {
                if (x < 500 - d) {
                    x += d;
                }
                else {
                    x -= d;
                }
                break;
            }
            case 2: {
                if (y > d) {
                    y -= d;
                }
                else {
                    y += d;
                }
                break;
            }
            case 3: {
                if (y < 500 - d) {
                    y += d;
                }
                else {
                    y -= d;
                }
                break;
            }
        }
    }
}