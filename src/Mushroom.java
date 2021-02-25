public class Mushroom extends Sprite {

    private final int INITIAL_X = 400;

    public Mushroom(int x, int y) {
        super(x, y);

        initMushroom();
    }

    private void initMushroom() {
        loadImage("images/mushroom.png");
        getImageDimensions();
    }
}