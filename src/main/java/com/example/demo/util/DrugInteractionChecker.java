package com.example.demo.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrugInteractionChecker {

    /**
     * Checks whether two drug ingredient lists have any interaction
     * (i.e., common ingredient).
     */
    public static boolean hasInteraction(List<String> drugA, List<String> drugB) {
        if (drugA == null || drugB == null) {
            return false;
        }

        Set<String> set = new HashSet<>(drugA);
        for (String ingredient : drugB) {
            if (set.contains(ingredient)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of interacting ingredients.
     */
    public static Set<String> getInteractingIngredients(
            List<String> drugA,
            List<String> drugB) {

        Set<String> interactions = new HashSet<>();

        if (drugA == null || drugB == null) {
            return interactions;
        }

        Set<String> set = new HashSet<>(drugA);
        for (String ingredient : drugB) {
            if (set.contains(ingredient)) {
                interactions.add(ingredient);
            }
        }
        return interactions;
    }
}
