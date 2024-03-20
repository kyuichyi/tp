package meditracker.library;

import meditracker.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the searching of medications in the medication library.
 */
public class LibraryManager {

    private static final String FILE_PATH = "./data/medicationLibrary.txt";
    private final List<SearchResult> searchResults = new ArrayList<>();

    /**
     * Searches for medications in the library based on a keyword.
     *
     * @param keyword The keyword to search for in the medication library.
     * @throws FileNotFoundException If the medication library file is not found.
     */
    public void searchMedication(String keyword) throws FileNotFoundException {
        File file = new File(FILE_PATH);
        Scanner scanner = new Scanner(file);
        searchResults.clear();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(keyword)) {
                String[] medicationDetails = line.split("\\|");
                searchResults.add(new SearchResult(medicationDetails[0], medicationDetails[1], medicationDetails[2]));
            }
        }
    }

    /**
     * Prints the search results to the user interface.
     *
     * @param ui The user interface to print the search results to.
     */
    public void printSearchResults(Ui ui) {
        if (searchResults.isEmpty()) {
            ui.showNoSearchResultsMessage();
        } else {
            ui.showSearchResults(searchResults);
        }
    }
}