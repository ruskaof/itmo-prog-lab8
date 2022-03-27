package com.ruskaof.client.util;

import com.ruskaof.common.data.*;
import com.ruskaof.common.util.InputManager;
import com.ruskaof.common.util.OutputManager;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Asks and receives user input data to make a StudyGroup object.
 */
public class StudyGroupMaker {
    private static final String ERROR_MESSAGE = "Your enter was not correct type. Try again";
    private final OutputManager outputManager;
    private final Asker asker;

    public StudyGroupMaker(InputManager inputManager, OutputManager outputManager) {
        this.outputManager = outputManager;
        this.asker = new Asker(inputManager, outputManager);
    }

    public StudyGroup makeStudyGroup() {
        return askForStudyGroup();
    }

    private StudyGroup askForStudyGroup() {
        outputManager.println("Enter studyGroup data");
        String name = asker.ask(arg -> (arg).length() > 0, "Enter name (String)",
                ERROR_MESSAGE, "The string must not be empty", x -> x, false);

        Integer studentsCount = asker.ask(arg -> (arg) > 0, "Enter studentsCount (int) (can be null)",
                ERROR_MESSAGE, "Your int must be >0. Try again", Integer::parseInt, true); // >0, null-able

        FormOfEducation formOfEducation = asker.ask(arg -> true, "Enter formOfEducation (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES)",
                ERROR_MESSAGE, ERROR_MESSAGE, FormOfEducation::valueOf, false); //not null

        Semester semesterEnum = asker.ask(arg -> true, "Enter semesterEnum (THIRD, FIFTH, SIXTH, SEVENTH) (can be null)",
                ERROR_MESSAGE, ERROR_MESSAGE, Semester::valueOf, true); // null-able

        Coordinates coordinates = askForCoordinates(); //not null
        Person groupAdmin = askForGroupAdmin(); //not null
        return new StudyGroup(name, coordinates, studentsCount,
                formOfEducation, semesterEnum, groupAdmin);
    }

    private Coordinates askForCoordinates() {
        outputManager.println("Enter coordinates data");
        final long xLimitation = -896;
        final double yLimitation = 135;
        long x = asker.ask(arg -> (arg) > xLimitation, "Enter x (long)",
                ERROR_MESSAGE, "The long must be >-896. Try again", Long::parseLong, false); //> -896
        Double y = asker.ask(arg -> (arg) <= yLimitation, "Enter y (Double)",
                ERROR_MESSAGE, "The double must be <= 135. Try again", Double::parseDouble, false); //<=135, not null
        return new Coordinates(x, y);
    }

    private Person askForGroupAdmin() {
        outputManager.println("Enter groupAdminData");

        String name = asker.ask(arg -> (arg).length() > 0, "Enter name (String)",
                ERROR_MESSAGE, "The string must not be empty. Try again", x -> x, false);

        Integer height = asker.ask(arg -> (arg) > 0, "Enter height (Integer)",
                ERROR_MESSAGE, "The integer must be >0. Try again", Integer::parseInt, false);

        Country nationality = asker.ask(arg -> true, "Enter country (RUSSIA, SPAIN, INDIA, THAILAND, NORTH_KOREA) (can be null)",
                ERROR_MESSAGE, ERROR_MESSAGE, Country::valueOf, true);

        Location location = askForLocation();

        return new Person(name, height, nationality, location);
    }

    private Location askForLocation() {
        outputManager.println("Enter location data");
        String name = asker.ask(arg -> (arg).length() > 0, "Enter name (String) (can be null)",
                ERROR_MESSAGE, "The string must not be empty. Try again", x -> x, true);
        float x = asker.ask(arg -> true, "Enter x (float)", ERROR_MESSAGE,
                ERROR_MESSAGE, Float::parseFloat, false);
        long y = asker.ask(arg -> true, "Enter y (long)", ERROR_MESSAGE,
                ERROR_MESSAGE, Long::parseLong, false);

        return new Location(x, y, name);
    }

    public static class Asker {

        private final InputManager inputManager;
        private final OutputManager outputManager;


        public Asker(InputManager inputManager, OutputManager outputManager) {
            this.inputManager = inputManager;
            this.outputManager = outputManager;
        }

        public <T> T ask(Predicate<T> predicate,
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
                    input = inputManager.nextLine();
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
