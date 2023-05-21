package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
 
public class Pokers {
    //initialization of variables
    public static final String[] SUITS = {"c","h","d","s"}; //suit of the card
    public static final String[] RANK = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"}; //rank of the card
    public static int active; //current active player
    public static int trick = 1; //display current trick
    public static Scanner scan  = new Scanner(System.in);

    //a new deck
    public static List<Card> createpokers() {
        List<Card> pokerList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                pokerList.add(new Card(SUITS[i],RANK[j]));
            }
        }
        return pokerList;
    }
 
    //shuffle deck
    public static void shuffle(List<Card> pokerList) {
        Random random = new Random();
        for (int i = pokerList.size()-1; i > 0; i--) {
            int index = random.nextInt(i);
            swap(pokerList, i, index);
        }
    }
    
    //swap
    public static void swap (List<Card> pokerList, int i, int index) {
        Card tmp = pokerList.get(i);
        pokerList.set(i, pokerList.get(index));
        pokerList.set(index, tmp);
    }

    public static void start()
    {
        //print out initial unshuffled deck
        List<Card> pokerList = createpokers();
        System.out.println("-----------Creating Deck---------------");
        System.out.println("New Deck: " + pokerList);
        System.out.println("\n-----------Shuffling the Deck---------------");
        shuffle(pokerList);
        System.out.println("Shuffled Deck: " + pokerList);

        //print out shuffled desk
        System.out.println("\n--------------------------------------------");
        System.out.println("----------------Game Start---------------");
        System.out.println("--------------------------------------------");

        //initialize players hands
        List<Card> hand1 = new ArrayList<>();
        List<Card> hand2 = new ArrayList<>();
        List<Card> hand3 = new ArrayList<>();
        List<Card> hand4 = new ArrayList<>();
 
        List<List<Card>> hand = new ArrayList<>();
        hand.add(hand1);
        hand.add(hand2);
        hand.add(hand3);
        hand.add(hand4);

        //center
        List<Card> center = new ArrayList<>();
        Card ctr = pokerList.remove(0);
        center.add(ctr);
        
        //deal to player
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                //deal card
                List<Card> givencard = hand.get(j);
                givencard.add(pokerList.remove(0));
            }
        }
        
        //determine first player
        String strFirstCenter = center.toString();
        char charFirstCenter = strFirstCenter.charAt(2);

        if(charFirstCenter == 'A' || charFirstCenter == '5' || charFirstCenter == '9' || charFirstCenter == 'K')
        {
            active = 1;
        }

        else if(charFirstCenter == '2' || charFirstCenter == '6' || charFirstCenter == 'X')
        {
            active = 2;
        }

        else if(charFirstCenter == '3' || charFirstCenter == '7' || charFirstCenter == 'J')
        {
            active = 3;
        }

        else if(charFirstCenter == '4' || charFirstCenter == '8' || charFirstCenter == 'Q')
        {
            active = 4;
        }

        //for checking of winner
        String[] playerRank = {" ", " ", " "," "};
        
        for(int m = 1; m <= 4; m++){
            while(true){
                //print current trick
                System.out.print("Trick ");     System.out.println(trick);

                //print hand card
                for (int i = 0; i < hand.size(); i++) {
                    System.out.println("Player " +(i+1)+ " : "+hand.get(i));
                }

                //print deck and center cards
                System.out.println("Deck : " + pokerList);
                System.out.println("Center : " + center);

                //card input from user
                System.out.println("Turn: Player " + active);
                System.out.print("> ");
                String cardChoice  = scan.nextLine();
            
                //checking center card suit and rank
                String strCenter = center.toString();
                char charCenterSuit = strCenter.charAt(1);
                char charCenterRank = strCenter.charAt(2);
                
                if (active == 1){

                    String strHand = hand1.toString();

                    //check if user input exist in player hand and check input length, else throw error
                    if(strHand.contains(cardChoice) && cardChoice.length() == 2){

                        //check whether suit or rank is same with center card suit and rank, else throw error
                        if(charCenterSuit == cardChoice.charAt(0) || charCenterRank == cardChoice.charAt(1)){

                            //remove played card from users hand and put played card to center
                            int index = strHand.indexOf(cardChoice);
                            Card dealt = hand1.remove((index - 1) / 4);
                            center.add(dealt);

                            //change active plater
                            active = 2;
                            
                            //for winner checking
                            playerRank[0] = cardChoice.substring(0,0) + cardChoice.substring(1);

                            //clear screen
                            System.out.print("\033[H\033[2J");      System.out.flush();
                            break;
                        }
                        else{
                            System.out.println("This card is unplayable.");
                        }
                    }
                    else{
                        System.out.println("This card does not exist.");
                    }
                }
                
                else if (active == 2){

                    String strHand = hand2.toString();

                    if(strHand.contains(cardChoice) && cardChoice.length() == 2){

                        if(charCenterSuit == cardChoice.charAt(0) || charCenterRank == cardChoice.charAt(1)){

                            int index = strHand.indexOf(cardChoice);
                            Card dealt = hand2.remove((index - 1) / 4);
                            center.add(dealt);

                            active = 3;
                            
                            playerRank[1] = cardChoice.substring(0,0) + cardChoice.substring(1);

                            System.out.print("\033[H\033[2J");  System.out.flush();
                            break;
                        }
                        else{
                            System.out.println("This card is unplayable.");
                        }
                    }
                    else{
                        System.out.println("This card does not exist.");
                    }
                }
                
                else if (active == 3){

                    String strHand = hand3.toString();

                    if(strHand.contains(cardChoice) && cardChoice.length() == 2){

                        if(charCenterSuit == cardChoice.charAt(0) || charCenterRank == cardChoice.charAt(1)){

                            int index = strHand.indexOf(cardChoice);
                            Card dealt = hand3.remove((index - 1) / 4);
                            center.add(dealt);

                            active = 4;
                            
                            playerRank[2] = cardChoice.substring(0,0) + cardChoice.substring(1);

                            System.out.print("\033[H\033[2J");      System.out.flush();
                            break;
                        }
                        else{
                            System.out.println("This card is unplayable.");
                        }
                    }
                    else{
                        System.out.println("This card does not exist.");
                    }
                }

                else{

                    String strHand = hand4.toString();

                    if(strHand.contains(cardChoice) && cardChoice.length() == 2){

                        if(charCenterSuit == cardChoice.charAt(0) || charCenterRank == cardChoice.charAt(1)){

                            int index = strHand.indexOf(cardChoice);
                            Card dealt = hand4.remove((index - 1) / 4);
                            center.add(dealt);

                            active = 1;
                            
                            playerRank[3] = cardChoice.substring(0,0) + cardChoice.substring(1);

                            System.out.print("\033[H\033[2J");      System.out.flush();
                            break;
                        }
                        else{
                            System.out.println("This card is unplayable.");
                        }
                    }
                    else{
                        System.out.println("This card does not exist.");
                    }
                }
            }
            //initialize winner checking variables
            int winningRank = 0;
            int winner = -1;
    
            //winner checking
            for(int i = 0; i <= 3; i++){
                for (int j = 0; j <= 12; j++){
                    if(playerRank[i].equals(RANK[j]) && j > winningRank){
                        winningRank = j;
                        winner = i+1;
                    }
                }
            }
    
            //!TBD
            if(m == 4){
                System.out.print("### Winner is player "); System.out.println(winner+"###"+"\n");
                trick++;
                active = winner + 1;
                center.clear();
                System.out.print("Trick ");     System.out.println(trick);
                System.out.print("Player 1: ");     System.out.println(hand1);
                System.out.print("Player 2: ");     System.out.println(hand2);
                System.out.print("Player 3: ");     System.out.println(hand3);
                System.out.print("Player 4: ");     System.out.println(hand4);
                System.out.print("Center: ");       System.out.println(center);
                System.out.print("Deck: ");     System.out.println(pokerList);
                System.out.println("Turn: Player " + winner);
                System.out.print("> ");
                String cardChoice  = scan.nextLine();
                String strHand = hand.get(winner-1).toString();
                int index = strHand.indexOf(cardChoice);
                Card dealt = hand2.remove((index - 1) / 4);
                center.add(dealt);
                m = 1;
            }
        }
    }

    public static void main(String[] args) {
        start();
    }
}