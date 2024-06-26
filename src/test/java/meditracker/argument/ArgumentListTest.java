package meditracker.argument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

import meditracker.exception.ArgumentException;
import meditracker.exception.HelpInvokedException;

public class ArgumentListTest {
    private final ArgumentList testArgumentList = new ArgumentList(
            new NameArgument(false),
            new DosageMorningArgument(false),
            new QuantityArgument(false)
    );

    @Test
    void argumentList_flagCollision_assertionError() {
        Argument testArgument1 = new Argument(
                ArgumentName.LIST_TYPE,
                "-l",
                "",
                true,
                true) {};
        Argument testArgument2 = new Argument(
                ArgumentName.LIST_INDEX,
                "-l",
                "",
                true,
                true) {};
        assertThrows(AssertionError.class, () -> new ArgumentList(testArgument1, testArgument2));
    }

    @Test
    void parse_properArgumentString_argumentValuesObtained() {
        String name = "Medication";
        String dosage = "100";
        String quantity = "2000";
        String testArgumentString = String.format("-n %s -dM %s -q %s", name, dosage, quantity);

        Map<ArgumentName, String> parsedArgs;
        try {
            parsedArgs = testArgumentList.parse(testArgumentString);
        } catch (HelpInvokedException | ArgumentException e) {
            throw new RuntimeException(e);
        }

        assertEquals(parsedArgs.get(ArgumentName.NAME), name);
        assertEquals(parsedArgs.get(ArgumentName.DOSAGE_MORNING), dosage);
        assertEquals(parsedArgs.get(ArgumentName.QUANTITY), quantity);
    }

    @Test
    void parse_missingArgumentSpecified_argumentException() {
        String name = "Medication";
        String dosage = "100";
        String testArgumentString = String.format("-n %s -dM %s", name, dosage);

        assertThrows(ArgumentException.class, () -> testArgumentList.parse(testArgumentString));
    }
}
