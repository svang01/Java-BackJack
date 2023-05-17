import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<Card>();
    }

    public void createFullDeck() {
        for (Suits cardSuit : Suits.values()) {
            for (Values cardValue : Values.values()) {
                this.deck.add(new Card(cardSuit, cardValue));
            }
        }
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public String toString() {
        String cardList = "";
        for (Card card : this.deck) {
            cardList += "\n-" + card.toString();
        }
        return cardList;
    }

    public Card getCard(int i){
        return this.deck.get(i);
    }

    public void removeCard(int i){
        this.deck.remove(i);
    }

    public void addCard(Card addCard) {
        this.deck.add(addCard);
    }

    public int deckSize() {
        return this.deck.size();
    }

    public void draw(Deck from) {
        this.deck.add(from.getCard(0));
        from.removeCard(0);
    }

    public void moveAllToDeck(Deck moveTo) {
        int thisDeckSize = this.deck.size();

        for (int i = 0; i < thisDeckSize; i++) {
            moveTo.addCard(this.getCard(i));
        }

        for (int i = 0; i < thisDeckSize; i++) {
            this.removeCard(0);
        }
    }

    public int cardsValue() {
        int totalAmount = 0;
        int aces = 0;

        for (Card card : this.deck) {
            switch (card.getValue()) {
                case TWO: totalAmount = totalAmount + 2;
                    break;
                case THREE: totalAmount = totalAmount + 3;
                    break;
                case FOUR: totalAmount = totalAmount + 4;
                    break;
                case FIVE: totalAmount = totalAmount + 5;
                    break;
                case SIX: totalAmount = totalAmount + 6;
                    break;
                case SEVEN: totalAmount = totalAmount + 7;
                    break;
                case EIGHT: totalAmount = totalAmount + 8;
                    break;
                case NINE: totalAmount = totalAmount + 9;
                    break;
                case TEN: totalAmount = totalAmount + 10;
                    break;
                case JACK: totalAmount = totalAmount + 10;
                    break;
                case QUEEN: totalAmount = totalAmount + 10;
                    break;
                case KING: totalAmount = totalAmount + 10;
                    break;
                case ACE: aces += 1;
                    break;
            }
        }

        for (int i = 0; i < aces; i++) {
            if (totalAmount > 10) {
                totalAmount += 1;
            } else {
                totalAmount += 11;
            }
        }
        return totalAmount;
    }
}