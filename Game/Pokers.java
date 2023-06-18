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