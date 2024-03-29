package meditracker.command;

import meditracker.medication.Medication;
import meditracker.medication.MedicationManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest {

    @Test
    void execute_listCommand_expect() {
        MedicationManager medicationManager = new MedicationManager();

        String medicationName = "Medication_B";

        Medication medication = new Medication(
                medicationName,
                30.0,
                1000.0,
                null,
                null,
                null,
                "01/08/25",
                "night",
                "for_flu_or_allergy",
                "");

        medicationManager.addMedication(medication);

        assert medicationManager.getTotalMedications() > 0 : "Total medications in medication must be greater " +
                "than 0 after adding in" + medicationName;

        Medication addedMedication = medicationManager.getMedication(1);
        assertEquals(addedMedication.getName(), medicationName);
    }
}