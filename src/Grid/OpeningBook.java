package Grid;

import java.util.HashMap;
import java.util.Map;

public class OpeningBook {
    // AI GENERATED
    private final Map<String, Integer> openingBook;

    public OpeningBook() {
        openingBook = new HashMap<>() {{
            // Standard opening center-first
            put("3", 3);
            put("3,3", 2);
            put("3,3,2", 3);
            put("3,3,2,3", 4);
            put("3,3,2,3,4", 2);
            put("3,3,2,3,4,2", 5);
            put("3,3,2,3,4,2,5", 1);
    
            // Alternative opponent response
            put("3,2", 3);
            put("3,2,3", 4);
            put("3,2,3,4", 2);
            put("3,2,3,4,2", 5);
            put("3,2,3,4,2,5", 1);
    
            // If opponent goes 3, 2, 4
            put("3,2,4", 3);
            put("3,2,4,3", 2);
            put("3,2,4,3,2", 5);
            put("3,2,4,3,2,5", 1);
    
            // Center to left clamp
            put("3,4", 3);
            put("3,4,3", 2);
            put("3,4,3,2", 4);
            put("3,4,3,2,4", 1);
    
            // Center to right clamp
            put("3,5", 3);
            put("3,5,3", 2);
            put("3,5,3,2", 4);
            put("3,5,3,2,4", 5);
    
            // Left-first (non-center opening)
            put("2", 3);
            put("2,3", 2);
            put("2,3,2", 3);
            put("2,3,2,3", 4);
    
            // Right-first
            put("4", 3);
            put("4,3", 4);
            put("4,3,4", 3);
            put("4,3,4,3", 2);
    
            // Edge play punishment
            put("0", 3);
            put("6", 3);
            put("0,3", 2);
            put("6,3", 4);
        }};    
    }

    public Map<String, Integer> getOpeningBook() {
        return openingBook;
    }
}
