package control;

import model.Die;

import java.util.ArrayList;
import java.util.HashMap;

public class DiceController {
    // Dice are stored by firing character id
    private static HashMap<Integer, ArrayList<Die>> DICE = new HashMap<>();

    private static HashMap<Integer, Integer> SUCCESS = new HashMap<>();

    private static DiceController dc = new DiceController();
    private DiceController() {

    }

    public static void addDice(int id, int numDice, int successValue) {
        ArrayList<Die> newDice = new ArrayList<>();
        for(int i = 0; i < numDice; i++) {
            newDice.add(new Die());
        }
        if(DICE.containsKey(id)) DICE.get(id).addAll(newDice);
        else DICE.put(id, newDice);
        SUCCESS.put(id, successValue);
    }

    public static void finishRolling() {
        // TODO resolve effects
        DICE.clear();
        SUCCESS.clear();
    }

    public static ArrayList<Integer> getSuccesses() {
        // For now, just check the dice against successes
        ArrayList<Integer> successes = new ArrayList<>();
        for(int id : DICE.keySet()) {
            int target = SUCCESS.get(id);
            for(Die die : DICE.get(id)) {
                if(die.getValue() >= target)
                    successes.add(id);
            }
        }
        return successes;
    }
}
