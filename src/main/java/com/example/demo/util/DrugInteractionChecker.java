package com.example.demo.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrugInteractionChecker {

    /**
     * Returns true if there is at least one common ingredient
     * between drugA and drugB
     */
    public static boolean hasInteraction(List<String> drugA, List<String> drugB) {

        if (drugA == null || drugB == null) {
            return false;
        }

        for (String a : drugA) {
            for (String b : drugB) {
                if (a != null && a.equalsIgnoreCase(b)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns all common ingredients between two drugs
     */
    public static Set<String> getInteractingIngredients(
            List<String> drugA,
            List<String> drugB) {

        Set<String> result = new HashSet<>();

        if (drugA == null || drugB == null) {
            return result;
        }

        for (String a : drugA) {
            for (String b : drugB) {
                if (a != null && a.equalsIgnoreCase(b)) {
                    result.add(a);
                }
            }
        }
        return result;
    }
}
