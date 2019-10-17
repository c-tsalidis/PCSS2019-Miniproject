import java.util.ArrayList;
import java.util.Arrays;

public class Deck {

    public static void main(String[] args) {

        //Creating the deck
        ArrayList<Card> list = new ArrayList<Card>();


        for (int i = 0; i < 10; i++) {
            list.add(new Card(Integer.toString(i), "blue"));
            list.add(new Card(Integer.toString(i), "red"));
            list.add(new Card(Integer.toString(i), "green"));
            list.add(new Card(Integer.toString(i), "yellow"));
        }

        for (int i = 1; i < 10; i++) {
            list.add(new Card(Integer.toString(i), "blue"));
            list.add(new Card(Integer.toString(i), "red"));
            list.add(new Card(Integer.toString(i), "green"));
            list.add(new Card(Integer.toString(i), "yellow"));
        }
        for (int i = 0; i< 2; i++){
            list.add(new Card("Skip","blue"));
            list.add(new Card("Skip","red"));
            list.add(new Card("Skip","green"));
            list.add(new Card("Skip","yellow"));
            list.add(new Card("Reverse","blue"));
            list.add(new Card("Reverse","red"));
            list.add(new Card("Reverse","green"));
            list.add(new Card("Reverse","yellow"));
            list.add(new Card("+2","blue"));
            list.add(new Card("+2","red"));
            list.add(new Card("+2","green"));
            list.add(new Card("+2","yellow"));
        }
        for (int i = 0; i< 4; i++){
            list.add(new Card("Choose colour", "Uncolored"));
            list.add(new Card("+4", "Uncolored"));
        }

        for (int i = 0; i < 76; i++) {
            System.out.println("index number " + i);
            System.out.println(list.get(i).getName());
            System.out.println(list.get(i).getColour());
            System.out.println();
        }
        for (int i = 76; i < list.size(); i++) {
            System.out.println("index number " + i);
            System.out.println(list.get(i).getType());
            System.out.println(list.get(i).getColour());
            System.out.println();
        }

    }

}
