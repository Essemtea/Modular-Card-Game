/**
 * An abstract class used for displaying and collecting information to and from 
 * a {@code Player} for the duration of a card game.
 */
public abstract class GameScreen {

    protected GameScreen() {}

    public abstract Game getGame();

    public abstract void printTitle();

    public abstract void play() throws Exception;
}
