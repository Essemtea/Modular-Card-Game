import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
    }

    public Deck(Card ...cards) {
        this.cards = new ArrayList<Card>(Arrays.asList(cards));
    }

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    
    /**
     * Adds the specified {@code Card}s to this {@code Deck}.
     * @param sourceCards
     */
    public void giveCards(Card... sourceCards) {
        for (Card card : sourceCards) {
            cards.add(card);
        }
    }

    /**
     * Adds the specified {@code ArrayList} of {@code Card}s to this {@code Deck}.
     * @param sourceCards
     */
    public void giveCards(ArrayList<Card> sourceCards) {
        Card[] arr = new Card[sourceCards.size()];
        sourceCards.toArray(arr);
        giveCards(arr);
        sourceCards.clear();
    }

    /**
     * Adds the contents of another {@code Deck} to this {@code Deck}.
     * {@code Card}s are subsequently removed from the {@code Deck} that was passed.
     * @param sourceDeck
     */
    public void giveCards(Deck sourceDeck) {
        giveCards(sourceDeck.getCards());
        sourceDeck.empty();
    }

    /**
     * Retrieves the specified {@code Card}s, if present, from this {@code Deck}.
     * @param cardsToTake
     * @return A new {@code Deck} containing the {@code Card}s removed from this one.
     */
    public Deck takeCards(Card... cardsToTake) {
        Deck newDeck = new Deck();
        for (Card cardToTake : cardsToTake) {
            for (Card card : cards) {
                if (card.equals(cardToTake)) {
                    newDeck.giveCards(cardToTake);
                    cards.remove(card);
                }
            }
        }
        return newDeck;
    }

    /**
     * Retrieves elements of the {@code ArrayList} of {@code Card}s, if present, from this {@code Deck}.
     * @param cardsToTake
     * @return A new {@code Deck} containing the {@code Card}s removed from this one.
     */
    public Deck takeCards(ArrayList<Card> cardsToTake) {
        Card[] arr = new Card[cardsToTake.size()];
        cardsToTake.toArray(arr);
        Deck newDeck = takeCards(arr);
        cardsToTake.clear();
        return newDeck;
    }

    /**
     * Retrieves the contents of another {@code Deck}, if present, from this {@code Deck}.
     * {@code Card}s are subsequently removed from the {@code Deck} that was passed.
     * @param deckToTake A {@code Deck} specifying which cards to remove.
     * @return a new {@code Deck} containing the {@code Card}s removed from this one.
     */
    public Deck takeCards(Deck deckToTake) {
        Deck newDeck = takeCards(deckToTake.getCards());
        deckToTake.empty();
        return newDeck;
    }

    /**
     * Retrieves a random {@code Card}, if any are present, from this {@code Deck}.
     * The {@code Card} is removed from this {@code Deck}.
     * @return A random {@code Card} taken from this deck.
     * @throws IndexOutOfBoundsException
     */
    public Card takeRandomCard() throws IndexOutOfBoundsException {
        if (cards.isEmpty()) {
            throw new IndexOutOfBoundsException("Cannot take card from empty deck.");
        }

        int randIndex = (int) (Math.random() * cards.size());
        Card randomCard = cards.get(randIndex);
        cards.remove(randIndex);

        return randomCard;
    }

    public void empty() {
        cards.clear();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void print() {
        CardPrinter.printCards(this);
    }

    @Override
    public String toString() {
        return CardPrinter.getDeckString(this);
    }

    
}
