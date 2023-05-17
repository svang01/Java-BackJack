import java.util.Scanner;

public class Blackjack {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = input.nextLine();
        System.out.println("Welcome to My Java Blackjack! " + playerName + "!");

        // Create the playing deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffleDeck();

        // Create hands for the player and the dealer - hands are created from methods that are made in the deck class
        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();

        // Game loops
        int playerMoney = 1000;

        while(playerMoney > 0) {
            System.out.print(playerName + ", you have $" + playerMoney + ", how much would you like to bet? ");
            int playerBet = input.nextInt();
            if (playerBet % 5 != 0) {
                System.out.println("Sorry - you are only allowed to bet in $5 increments.");
                continue;
            }

            if (playerBet > playerMoney) {
                System.out.println("You cannot bet more than you have. Please leave.");
                break;
            }

            boolean endRound = false;

            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);

            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);

            while (true) {
                System.out.println("Your hand: ");
                System.out.println(playerHand.toString());
                System.out.println("Your hand is valued at: " + playerHand.cardsValue());
                System.out.println("Dealer Hand: " + dealerHand.getCard(0).toString() + " and [Hidden]");
                System.out.println("Do you want to Double Down?");
                String answer = input.next().toLowerCase();

                boolean doubledown = false;
                String choice = "";

                do {
                    if (!answer.equals("yes") && !answer.equals("y")) {
                        System.out.print("Would you like to hit(h) or stand(s)? ");
                        choice = input.next();
                        if (choice.charAt(0) == 'h' || choice.charAt(0) == 'H') {
                            playerHand.draw(playingDeck);
                            System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                            System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
                            if (playerHand.cardsValue() > 21) {
                                System.out.println("Bust. Currently valued at: " + playerHand.cardsValue());
                                playerMoney -= playerBet;
                                endRound = true;
                                break;
                            }
                        }
                        if (choice.charAt(0) == 's' || choice.charAt(0) == 'S') {
                            break;
                        }
                    }

                    if (answer.equals("yes") || answer.equals("y")) {
                        if (playerBet * 2 > playerMoney) {
                            System.out.println("Not enough money to double down. \n Please, continue to play your hand.");
                            System.out.print("Would you like to hit(H) or stand(S)? ");
                            choice = input.next();

                            if (choice.charAt(0) == 'h' || choice.charAt(0) == 'H') {
                                playerHand.draw(playingDeck);
                                System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                                System.out.println("Your hand is now valued at: " + playerHand.cardsValue());

                                if (playerHand.cardsValue() > 21) {
                                    System.out.println("Bust. Currently valued at: " + playerHand.cardsValue());
                                    playerMoney -= playerBet;
                                    endRound = true;
                                    break;
                                }
                            }

                            if (choice.charAt(0) == 's' || choice.charAt(0) == 'S') {
                                break;
                            }

                        } else {
                            playerBet = playerBet * 2;
                            doubledown = true;

                            System.out.println("Your bet is now " + playerBet);
                            playerHand.draw(playingDeck);

                            System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                            System.out.println("Your hand is: " + playerHand.cardsValue());

                            if (playerHand.cardsValue() > 21) {
                                System.out.println("Bust. Currently at: " + playerHand.cardsValue());
                                playerMoney -= playerBet;
                                endRound = true;
                                break;
                            }
                        }
                    }
                } while (!doubledown);
                break;
            }

            System.out.println("Dealer Cards: " + dealerHand.toString());

            if ((dealerHand.cardsValue() >= 17) && (dealerHand.cardsValue() > playerHand.cardsValue()) && !endRound) {
                System.out.println("Dealer wins!");
                playerMoney -= playerBet;
                endRound = true;
            }
            while ((dealerHand.cardsValue() < 17) && !endRound) {
                dealerHand.draw(playingDeck);
                System.out.println("Dealer Draws: " + dealerHand.getCard(dealerHand.deckSize()-1).toString());
            }

            System.out.println("Dealer's Hand value: " + dealerHand.cardsValue() + "\n Player's Hand value: "
                    + playerHand.cardsValue());

            if ((dealerHand.cardsValue() > 21) && !endRound) {
                System.out.println("Dealer busts! You win.");
                playerMoney += playerBet;
                endRound = true;
            }

            if ((playerHand.cardsValue() == dealerHand.cardsValue()) && !endRound) {
                System.out.println("Tie no one wins.");
                endRound = true;
            }

            if ((playerHand.cardsValue() > dealerHand.cardsValue()) && !endRound) {
                System.out.println("You win hand");
                playerMoney += playerBet;
            } else if (!endRound) {
                System.out.println("You lose hand");
                playerMoney -= playerBet;
            }

            playerHand.moveAllToDeck(playingDeck);
            dealerHand.moveAllToDeck(playingDeck);
            System.out.println("End of hand");

        }
        System.out.println("Game Over, you are out of money.");
        input.close();
    }
}

