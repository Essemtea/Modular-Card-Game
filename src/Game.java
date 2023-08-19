/**
 * An abstract class representing a card game. <br><br>
 * Handles the functions that aren't specific to a game, such as its child class {@code BlackjackGame}.
 */
public abstract class Game {
    private Deck gameDeck;
    private String name;

    protected Game() {
        resetDeck();
    }
    
    /** 
     * @return Deck
     */
    public Deck getDeck() {
        return gameDeck;
    }
    
    /** 
     * @param gameDeck
     */
    public void setDeck(Deck gameDeck) {
        this.gameDeck = gameDeck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract GameScreen getScreen();

    public void resetDeck() {
        if (gameDeck != null) {
            gameDeck.empty();
        }

        gameDeck = new Deck();

        try {
            for (Card.Signs sign : Card.Signs.values()) {
                for (int i = 1; i <= 13; i++) {
                    gameDeck.giveCards(new Card(i, sign));
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to populate game deck.");
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a random {@code Card}, if any are present, from the {@code Deck} of this {@code Game}.
     * The {@code Card} is removed from this {@code Game}'s {@code Deck}.
     * @return A random {@code Card} not already picked up.
     */
    public Card pickUpRandomCard() {
        return gameDeck.takeRandomCard();
    }

    /**
     * Retrieves a number of random {@code Card}s, if enough are present, from the {@code Deck} of this {@code Game}.
     * The {@code Card} are removed from this {@code Game}'s {@code Deck}.
     * @param number
     * @return A new {@code Deck} containing the {@code Card}s retrieved.
     */
    public Deck pickUpRandomCards(int number) {
        Deck newDeck = new Deck();
        for (int i = 0; i < number; i++) {
            newDeck.giveCards(gameDeck.takeRandomCard());
        }
        return newDeck;
    }

    public Deck pickUpCards(Card... cards) {
        return gameDeck.takeCards(cards);
    }

    public void putDownCards(Card... cards) {
        gameDeck.giveCards(cards);
    }
} 
