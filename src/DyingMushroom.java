public class DyingMushroom extends Sprite {

    private final int INITIAL_X = 400;

    public DyingMushroom(int x, int y) {
        super(x, y);

        initMushroom();
    }

    private void initMushroom() {
        loadImage("images/dyingmushroom.png");
        getImageDimensions();
    }
}