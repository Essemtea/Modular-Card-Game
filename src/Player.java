import java.util.ArrayList;

/**
 * The {@code Player} class represents participants of a {@code Game}.
 * Presently, it acts as a Singleton to produce only one instance of the class,
 * but subclasses like {@code Dealer} are free to follow their own model.
 */
public class Player {
    private static Player instance;

    private String name;
    private int balance;
    private Deck hand;

    protected Player() {}

    private Player(String name) {
        setName(name);
        setHand(new Deck());
        this.balance = 100;
    }

    
    /** 
     * @param name
     * @return Player
     */
    public static Player getInstance(String name) {
        synchronized (Player.class) {
            if (instance == null) {
                return new Player(name);
            } else {
                return instance;
            }
        }
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public String getBalanceString() {
        return name + "\'s balance: " +  balance + " coins";
    }

    public void printBalance() {
        System.out.println(getBalanceString());
    }

    private void setBalance(int balance) throws Exception {
        if (balance < 0) {
            throw new Exception("Unable to have negative balance.");
        }
        this.balance = balance;
    }

    public void giveCoins(int coins) throws Exception {
        setBalance(balance + coins);
    }

    public void takeCoins(int coins) throws Exception {
        setBalance(balance - coins);
    }

    public Deck getHand() {
        return hand;
    }

    public void setHand(Deck hand) {
        this.hand = hand;
    }

    public void giveCards(Card... cards) {
        hand.giveCards(cards);
    }

    public void giveCards(ArrayList<Card> cards) {
        hand.giveCards(cards);
    }

    public void giveCards(Deck deck) {
        hand.giveCards(deck);
    }

    public Deck takeCards(Card... cards) {
        return hand.takeCards(cards);
    }

    public Deck takeCards(ArrayList<Card> cards) {
        return hand.takeCards(cards);
    }

    public Deck takeCards(Deck deck) {
        return hand.takeCards(deck);
    }
    
    public void printHand() {
        Arcade.printSection(name + "\'s Hand");
        hand.print();
    }
}
