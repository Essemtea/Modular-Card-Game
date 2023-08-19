public abstract class GameScreen {

    protected GameScreen() {}

    public abstract Game getGame();

    public abstract void printTitle();

    public abstract void play() throws Exception;
}
