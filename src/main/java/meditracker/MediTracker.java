package meditracker;

import meditracker.command.Command;
import meditracker.command.CommandName;
import meditracker.command.CommandParser;
import meditracker.dailymedication.DailyMedicationManager;
import meditracker.exception.ArgumentNotFoundException;
import meditracker.exception.CommandNotFoundException;
import meditracker.exception.DuplicateArgumentFoundException;
import meditracker.exception.HelpInvokedException;
import meditracker.logging.MediLogger;
import meditracker.storage.FileReaderWriter;
import meditracker.ui.Ui;

import java.util.List;

/**
 * The main class for the MediTracker application.
 * It initializes the user interface and runs the application loop.
 */
public class MediTracker {
    /**
     * Constructs a new MediTracker object and initializes both medicationManager and
     * dailyMedicationManager.
     */
    public MediTracker() {
        DailyMedicationManager.createDailyMedicationManager();
    }

    /**
     * Constructs a new MediTracker object with data from save file for DailyMedicationManager
     *
     * @param dailyMedicationList Daily medication
     */
    public MediTracker(List<String> dailyMedicationList) {
        DailyMedicationManager.importDailyMedicationManager(dailyMedicationList);
    }

    /**
     * Runs the MediTracker application.
     * This method displays a welcome message, reads user commands, and processes them until the user exits the
     * application.
     */
    public void run() {
        //@@author nickczh-reused
        //Reused from https://github.com/nickczh/ip
        //with minor modifications
        FileReaderWriter.loadMediTrackerData();
        Ui.showWelcomeMessage();
        boolean isExit = false;
        while (!isExit) {
            Ui.showLine();
            String fullCommand = Ui.readCommand();

            CommandParser commandParser;
            try {
                commandParser = new CommandParser(fullCommand);
            } catch (CommandNotFoundException e) {
                // Just pressing enter into console, skip processing
                continue;
            }
            CommandName commandName = commandParser.getCommandName();

            Command command;
            try {
                command = commandParser.getCommand();
            } catch (ArgumentNotFoundException | DuplicateArgumentFoundException | CommandNotFoundException e) {
                Ui.showErrorMessage(e);
                continue;
            } catch (HelpInvokedException e) {
                Ui.showHelpMessage(commandName);
                continue;
            }

            command.execute();
            isExit = command.isExit();
        }
    }

    /**
     * Starts the MediTracker application.
     * It creates a new MediTracker object and calls its run() method.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        MediLogger.initialiseLogger();

        List<String> dailyMedicationList = FileReaderWriter.loadDailyMedicationData();
        if (dailyMedicationList == null) {
            new MediTracker().run();
        } else {
            new MediTracker(dailyMedicationList).run();
        }
    }
}
