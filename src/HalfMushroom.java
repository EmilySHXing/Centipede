public class HalfMushroom extends Sprite {

    private final int INITIAL_X = 400;

    public HalfMushroom(int x, int y) {
        super(x, y);

        initMushroom();
    }

    private void initMushroom() {
        loadImage("images/halfmushroom.png");
        getImageDimensions();
    }
}