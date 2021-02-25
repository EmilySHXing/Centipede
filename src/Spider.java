import java.util.concurrent.ThreadLocalRandom;

public class Spider extends Sprite {
    public Spider() {
        super(0, 600);
        initSpider();
    }

    private void initSpider() {
        loadImage("images/spider.png");
        getImageDimensions();
    }

    public void move(int op) {
        switch (op) {
            case 0: {
                if (x > 10) {
                    x -= 10;
                }
                else {
                    x += 10;
                }
                break;
            }
            case 1: {
                if (x < 490) {
                    x += 10;
                }
                else {
                    x -= 10;
                }
                break;
            }
            case 2: {
                if (y > 10) {
                    y -= 10;
                }
                else {
                    y += 10;
                }
                break;
            }
            case 3: {
                if (y < 670) {
                    y += 10;
                }
                else {
                    y -= 10;
                }
                break;
            }
        }
    }
}