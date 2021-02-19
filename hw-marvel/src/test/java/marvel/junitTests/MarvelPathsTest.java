package marvel.junitTests;

import marvel.MarvelModel;
import marvel.MarvelPaths;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MarvelPathsTest {

    private Map<String, List<String>> trackedBooks;

    public MarvelPathsTest() {
        trackedBooks = new HashMap<>();
        trackedBooks.put("added", new ArrayList<>());
    }

    // The following tests test the addCharacter helper method
    // in MarvelPaths
    @Test
    public void addHeroWithExistingBook() {
        MarvelModel existingBook = new MarvelModel();
        existingBook.setBook("added");
        existingBook.setHero("firstAdded");
        MarvelPaths.addCharacter(trackedBooks, existingBook);
        assertEquals("firstAdded", trackedBooks.get("added").get(0));
    }

    @Test
    public void addHeroWithoutExistingBook() {
        MarvelModel nonExistingBook = new MarvelModel();
        nonExistingBook.setBook("notadded");
        nonExistingBook.setHero("firstNotAdded");
        MarvelPaths.addCharacter(trackedBooks, nonExistingBook);
        assertEquals("firstNotAdded", trackedBooks.get("notadded").get(0));
    }

}
