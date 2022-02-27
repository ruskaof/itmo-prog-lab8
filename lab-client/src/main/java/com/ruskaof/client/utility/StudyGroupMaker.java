package com.ruskaof.client.utility;

import com.ruskaof.client.data.Coordinates;
import com.ruskaof.client.data.Country;
import com.ruskaof.client.data.FormOfEducation;
import com.ruskaof.client.data.Location;
import com.ruskaof.client.data.Person;
import com.ruskaof.client.data.Semester;
import com.ruskaof.client.data.StudyGroup;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Asks and receives user input data to make a StudyGroup object.
 */
public class StudyGroupMaker {
    private final OutputManager outputManager;
    private final Asker asker;
    private final CollectionManager collectionManager;
    private final String errMessage = "Your enter was not correct type. Try again";

    public StudyGroupMaker(UserInputManager userInputManager, OutputManager outputManager, CollectionManager collectionManager) {
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
        this.asker = new Asker(userInputManager, outputManager);
    }

    public StudyGroup makeStudyGroup() {
        return askForStudyGroup();
    }

    private StudyGroup askForStudyGroup() {
        outputManager.println("Enter studyGroup data");
        String name = asker.ask(arg -> ((String) arg).length() > 0, "Enter name (String)",
                errMessage, "The string must not be empty", x -> x, false);

        Integer studentsCount = asker.ask(arg -> ((Integer) arg) > 0, "Enter studentsCount (int) (can be null)",
                errMessage, "Your int must be >0. Try again", Integer::parseInt, true); // >0, null-able

        FormOfEducation formOfEducation = asker.ask(arg -> true, "Enter formOfEducation (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES)",
                errMessage, errMessage, FormOfEducation::valueOf, false); //not null

        Semester semesterEnum = asker.ask(arg -> true, "Enter semesterEnum (THIRD, FIFTH, SIXTH, SEVENTH) (can be null)",
                errMessage, errMessage, Semester::valueOf, true); // null-able

        Coordinates coordinates = askForCoordinates(); //not null
        Person groupAdmin = askForGroupAdmin(); //not null
        return new StudyGroup(name, coordinates, studentsCount,
                formOfEducation, semesterEnum, groupAdmin, collectionManager);
    }

    private Coordinates askForCoordinates() {
        outputManager.println("Enter coordinates data");
        final long xLimitation = -896;
        final double yLimitation = 135;
        long x = asker.ask(arg -> ((Long) arg) > xLimitation, "Enter x (long)",
                errMessage, "The long must be >-896. Try again", Long::parseLong, false); //> -896
        Double y = asker.ask(arg -> ((Double) arg) <= yLimitation, "Enter y (Double)",
                errMessage, "The double must be <= 135. Try again", Double::parseDouble, false); //<=135, not null
        return new Coordinates(x, y);
    }

    private Person askForGroupAdmin() {
        outputManager.println("Enter groupAdminData");

        String name = asker.ask(arg -> ((String) arg).length() > 0, "Enter name (String)",
                errMessage, "The string must not be empty. Try again", x -> x, false);

        Integer height = asker.ask(arg -> ((Integer) arg) > 0, "Enter height (Integer)",
                errMessage, "The integer must be >0. Try again", Integer::parseInt, false);

        Country nationality = asker.ask(arg -> true, "Enter country (RUSSIA, SPAIN, INDIA, THAILAND, NORTH_KOREA) (can be null)",
                errMessage, errMessage, Country::valueOf, true);

        Location location = askForLocation();

        return new Person(name, height, nationality, location);
    }

    private Location askForLocation() {
        System.out.println("Enter location data");
        String name = asker.ask(arg -> ((String) arg).length() > 0, "Enter name (String) (can be null)",
                errMessage, "The string must not be empty. Try again", x -> x, true);
        float x = asker.ask(arg -> true, "Enter x (float)", errMessage,
                errMessage, Float::parseFloat, false);
        long y = asker.ask(arg -> true, "Enter y (long)", errMessage,
                errMessage, Long::parseLong, false);

        return new Location(x, y, name);
    }

    public static class Asker {

        private final UserInputManager userInputManager;
        private final OutputManager outputManager;


        public Asker(UserInputManager userInputManager, OutputManager outputManager) {
            this.userInputManager = userInputManager;
            this.outputManager = outputManager;
        }

        public <T> T ask(Predicate<Object> predicate,
                         String askMessage,
                         String errorMessage,
                         String wrongValueMessage,
                         Function<String, T> converter,
                         boolean nullable) {
            outputManager.println(askMessage);
            String input;
            T value;
            do {
                try {
                    input = userInputManager.nextLine();
                    if ("".equals(input) && nullable) {
                        return null;
                    }

                    value = converter.apply(input);

                } catch (IllegalArgumentException e) {
                    outputManager.println(errorMessage);
                    continue;
                }
                if (predicate.test(value)) {
                    return value;
                } else {
                    outputManager.println(wrongValueMessage);
                }
            } while (true);
        }
    }
}
