package Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Pokers {
    // initialization of variables
    public static final String[] SUITS = { "c", "h", "d", "s" }; // suit of the card
    public static final String[] RANK = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K" }; // rank of
                                                                                                             // the card
    public static List<List<Card>> hand;
    public static List<Card> pokerList;
    public static List<Card> center;
    public static String[] playerRank;
    public static int trick = 1;
    public static int active;
    public static int playersPlayed = 0;

    public static Scanner scan = new Scanner(System.in);

    // a new deck
    public static List<Card> createpokers() {
        List<Card> pokerList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                pokerList.add(new Card(SUITS[i], RANK[j]));
            }
        }
        return pokerList;
    }

    // shuffle deck
    public static void shuffle(List<Card> pokerList) {
        Random random = new Random();
        for (int i = pokerList.size() - 1; i > 0; i--) {
            int index = random.nextInt(i);
            swap(pokerList, i, index);
        }
    }

    // swap
    public static void swap(List<Card> pokerList, int i, int index) {
        Card tmp = pokerList.get(i);
        pokerList.set(i, pokerList.get(index));
        pokerList.set(index, tmp);
    }

    public static void start() {
        // print out initial unshuffled deck
        List<Card> pokerList = createpokers();
        System.out.println("-----------Creating Deck---------------");
        System.out.println("New Deck: " + pokerList);
        System.out.println("\n-----------Shuffling the Deck---------------");
        shuffle(pokerList);
        System.out.println("Shuffled Deck: " + pokerList);

        // print out shuffled desk
        System.out.println("\n--------------------------------------------");
        System.out.println("----------------Game Start---------------");
        System.out.println("--------------------------------------------");
        System.out.println("Commands");
        System.out.println("--------");
        System.out.println("1. Draw     - Draw a card.");
        System.out.println("2. Reset    - Reset and start a new game.");
        System.out.println("3. Save     - Save the game.");
        System.out.println("4. Load     - Load previous game.");
        System.out.println("5. Exit     - Exit from the game.");

        // initialize players hands
        List<Card> hand1 = new ArrayList<>();
        List<Card> hand2 = new ArrayList<>();
        List<Card> hand3 = new ArrayList<>();
        List<Card> hand4 = new ArrayList<>();

        List<List<Card>> hand = new ArrayList<>();
        hand.add(hand1);
        hand.add(hand2);
        hand.add(hand3);
        hand.add(hand4);

        // center
        List<Card> center = new ArrayList<>();
        Card ctr = pokerList.remove(0);
        center.add(ctr);

        // deal to player
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                // deal card
                List<Card> givencard = hand.get(j);
                givencard.add(pokerList.remove(0));
            }
        }

        // // Empty pokerlist and empty hand test
        // if (pokerList.size() > 1) {
        // // Keep only the first card and remove the rest
        // pokerList.subList(1, pokerList.size()).clear();
        // }
        // if (hand1.size() > 1) {
        // hand1.subList(1, hand1.size()).clear();
        // }
        // if (hand2.size() > 1) {
        // hand2.subList(1, hand2.size()).clear();
        // }
        // if (hand3.size() > 1) {
        // hand3.subList(1, hand3.size()).clear();
        // }
        // if (hand4.size() > 1) {
        // hand4.subList(1, hand4.size()).clear();
        // }

        // determine first player
        String strFirstCenter = center.toString();
        char charFirstCenter = strFirstCenter.charAt(2);

        if (charFirstCenter == 'A' || charFirstCenter == '5' || charFirstCenter == '9' || charFirstCenter == 'K') {
            active = 1;
        }

        else if (charFirstCenter == '2' || charFirstCenter == '6' || charFirstCenter == 'X') {
            active = 2;
        }

        else if (charFirstCenter == '3' || charFirstCenter == '7' || charFirstCenter == 'J') {
            active = 3;
        }

        else if (charFirstCenter == '4' || charFirstCenter == '8' || charFirstCenter == 'Q') {
            active = 4;
        }

        // for checking of winner
        String[] playerRank = { " ", " ", " ", " " };

        playGame(hand, pokerList, center, active, playerRank, trick, playersPlayed);
    }
    
    public static boolean noValidCards(List<List<Card>> hands, char charCenterSuit, char charCenterRank) {
        for (List<Card> hand : hands) {
            for (Card card : hand) {
                String s = card.getSuit();
                String r = card.getRank();

                if (s.equals(String.valueOf(charCenterSuit)) || r.equals(String.valueOf(charCenterRank))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void con() {
        System.out.println();
        System.out.println("Do you want to play again? [Y/N] ");
        System.out.print("> ");
        String conti = scan.nextLine();
        if (conti.equalsIgnoreCase("Y")) {
            System.out.println("A new game will start soon...");
            System.out.println();
            start();
        } else if (conti.equalsIgnoreCase("N")) {
            System.out.println("Thanks for Playing Go Boom");
            System.exit(0);
        } else {
            System.out.println("Please enter Y or N");
        }
    }

    public static void countScore(List<List<Card>> hands) {
        int minScore = Integer.MAX_VALUE;
        int winningPlayer = -1;

        for (int i = 0; i < hands.size(); i++) {
            List<Card> playerHand = hands.get(i);
            int score = 0;

            for (Card card : playerHand) {
                String cardRank = card.getRank();

                if (cardRank.equals("A")) {
                    score += 1;
                } else if (cardRank.matches("[2-9]")) {
                    score += Integer.parseInt(cardRank);
                } else if (cardRank.matches("X") || cardRank.matches("J") || cardRank.matches("Q")
                        || cardRank.matches("K")) {
                    score += 10;
                }
            }

            System.out.println("Player " + (i + 1) + "'s score: " + score);

            if (score < minScore) {
                minScore = score;
                winningPlayer = i + 1; // Player numbers start from 1
            }
        }
        System.out.println("### Player " + winningPlayer + " wins the game with score of " + minScore + " ###");

        con();
    }

    public static void saveGame(List<List<Card>> hand, List<Card> pokerList, List<Card> center, int active,
            String[] playerRank, int trick, int playersPlayed) {
        try {
            // Prompt the user for a file name
            System.out.print("Enter the file name to save the game (without .txt): ");
            String fileName = scan.nextLine();

            // Create a FileWriter object to write to the file
            FileWriter writer = new FileWriter(fileName + ".txt");

            // Write the game state to the file
            writer.write(trick + "\n");

            for (List<Card> playerHand : hand) {
                writer.write(playerHand.stream()
                        .map(card -> card.getSuit() + "," + card.getRank())
                        .collect(Collectors.joining(", ")) + "\n");
            }

            writer.write(pokerList.stream()
                    .map(card -> card.getSuit() + "," + card.getRank())
                    .collect(Collectors.joining(", ")) + "\n");

            writer.write(center.stream()
                    .map(card -> card.getSuit() + "," + card.getRank())
                    .collect(Collectors.joining(", ")) + "\n");

            writer.write(active + "\n");
            writer.write(Arrays.toString(playerRank) + "\n");
            writer.write(playersPlayed + "\n");

            // Close the FileWriter
            writer.close();

            System.out.println("Game saved successfully to the file: " + fileName);
            System.out.println("Do you want to continue play? [Y/N] ");
            System.out.print("> ");
            String ex = scan.nextLine();

            if (ex.equalsIgnoreCase("Y")) {
            }
            else if (ex.equalsIgnoreCase("N")) {
                System.exit(0);
            }
            else {
                System.out.println("Please enter Y or N");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while saving the game.");
            e.printStackTrace();
        }
    }

    public static void loadGame() {
        System.out.print("Enter the file name of the saved game (without .txt): ");
        String fileName = scan.nextLine();

        try {
            // Create a BufferedReader to read from the file
            BufferedReader reader = new BufferedReader(new FileReader(fileName + ".txt"));

            // Read the game state from the file
            trick = Integer.parseInt(reader.readLine());

            hand = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                List<Card> handCardList = Arrays.stream(reader.readLine().split(", "))
                        .map(cardStr -> {
                            String[] parts = cardStr.split(",");
                            return new Card(parts[0], parts[1]);
                        })
                        .collect(Collectors.toList());
                hand.add(handCardList);
            }

            pokerList = Arrays.stream(reader.readLine().split(", "))
                    .map(cardStr -> {
                        String[] parts = cardStr.split(",");
                        return new Card(parts[0], parts[1]);
                    })
                    .collect(Collectors.toList());

            center = Arrays.stream(reader.readLine().split(", "))
                    .map(cardStr -> {
                        String[] parts = cardStr.split(",");
                        return new Card(parts[0], parts[1]);
                    })
                    .collect(Collectors.toList());

            active = Integer.parseInt(reader.readLine());

            String playerRankStr = reader.readLine();
            playerRankStr = playerRankStr.substring(1, playerRankStr.length() - 1);
            playerRank = playerRankStr.split(", ");

            playersPlayed = Integer.parseInt(reader.readLine());

            // Close the BufferedReader
            reader.close();

            System.out.println("Game loaded successfully from the file: " + fileName);

            // After loading, call playGame() to resume game
            playGame(hand, pokerList, center, active, playerRank, trick, playersPlayed);

        } catch (IOException e) {
            System.out.println("An error occurred while loading the game.");
            e.printStackTrace();
        }
    }