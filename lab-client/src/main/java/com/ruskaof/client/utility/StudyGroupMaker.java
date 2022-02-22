package com.ruskaof.client.utility;

import com.ruskaof.client.data.Coordinates;
import com.ruskaof.client.data.Country;
import com.ruskaof.client.data.FormOfEducation;
import com.ruskaof.client.data.Location;
import com.ruskaof.client.data.Person;
import com.ruskaof.client.data.Semester;
import com.ruskaof.client.data.StudyGroup;

import java.util.function.Predicate;

/**
 * Asks and receives user input data to make a StudyGroup object.
 */
public class StudyGroupMaker {
    private final OutputManager outputManager;
    private final Asker asker;
    private final CollectionManager collectionManager;

    public static class Asker {
        private final UserInputManager userInputManager;
        private final OutputManager outputManager;

        public Asker(UserInputManager userInputManager, OutputManager outputManager) {
            this.userInputManager = userInputManager;
            this.outputManager = outputManager;
        }

        @SuppressWarnings("unchecked")
        public <T> T ask(Predicate<Object> predicate,
                         Class<?> type, String askMessage,
                         String errorMessage,
                         String wrongValueMessage,
                         boolean nullable) {
            outputManager.println(askMessage);
            String input;

            Object value;
            do {
                try {
                    input = userInputManager.nextLine();
                    if (input.equals("") && nullable) {
                        return null;
                    }
                    if (type == Integer.class) {
                        value = Integer.valueOf(input);
                    } else if (type == Long.class) {
                        value = Long.valueOf(input);
                    } else if (type == Double.class) {
                        value = Double.valueOf(input);
                    } else if (type == Float.class) {
                        value = Float.valueOf(input);
                    } else if (type == String.class) {
                        value = input;
                    } else if (type == Country.class) {
                        value = Country.valueOf(input);
                    } else if (type == FormOfEducation.class) {
                        value = FormOfEducation.valueOf(input);
                    } else if (type == Semester.class) {
                        value = Semester.valueOf(input);
                    } else {
                        throw new RuntimeException("bad type was written");
                    }
                } catch (IllegalArgumentException e) {
                    outputManager.println(errorMessage);
                    continue;
                }
                if (predicate.test(value)) {
                    return (T) value;
                } else {
                    outputManager.println(wrongValueMessage);
                }
            } while (true);
        }
    }

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
        System.out.println("Enter studyGroup data");
        String name = asker.ask(arg -> ((String) arg).length() > 0, String.class, "Enter name (String)",
                errMessage, "The string must not be empty", false);

        Integer studentsCount = asker.ask(arg -> ((Integer) arg) > 0, Integer.class, "Enter studentsCount (int)",
                errMessage, "Your int must be >0. Try again", true); // >0, null-able

        FormOfEducation formOfEducation = asker.ask(arg -> true, FormOfEducation.class,
                "Enter formOfEducation (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES)",
                errMessage, errMessage, false); //not null

        Semester semesterEnum = asker.ask(arg -> true, Semester.class,
                "Enter semesterEnum (THIRD, FIFTH, SIXTH, SEVENTH)", errMessage, errMessage, true); // null-able

        Coordinates coordinates = askForCoordinates(); //not null
        Person groupAdmin = askForGroupAdmin(); //not null
        return new StudyGroup(name, coordinates, studentsCount,
                formOfEducation, semesterEnum, groupAdmin, collectionManager);
    }

    private Coordinates askForCoordinates() {
        System.out.println("Enter coordinates data");
        long x = asker.ask(arg -> ((Long) arg) > -896, Long.class, "Enter x (long)",
                errMessage, "The long must be >-896. Try again", false);//> -896
        Double y = asker.ask(arg -> ((Double) arg) <= 135, Double.class, "Enter y (Double)",
                errMessage, "The double must be <= 135. Try again", false); //<=135, not null
        return new Coordinates(x, y);
    }

    private Person askForGroupAdmin() {
        outputManager.println("Enter groupAdminData");

        String name = asker.ask(arg -> ((String) arg).length() > 0, String.class, "Enter name (String)",
                errMessage, "The string must not be empty. Try again", false);

        Integer height = asker.ask(arg -> ((Integer) arg) > 0, Integer.class, "Enter height (Integer)",
                errMessage, "The integer must be >0. Try again", false);

        Country nationality = asker.ask(arg -> true, Country.class, "Enter country (RUSSIA, SPAIN, INDIA, THAILAND, NORTH_KOREA)",
                errMessage, errMessage, false);

        Location location = askForLocation();

        return new Person(name, height, nationality, location);
    }

    private Location askForLocation() {
        System.out.println("Enter location data");
        String name = asker.ask(arg -> ((String) arg).length() > 0, String.class, "Enter name (String)",
                errMessage, "The string must not be empty. Try again", false);
        float x = asker.ask(arg -> true, Float.class, "Enter x (float)", errMessage,
                errMessage, false);
        long y = asker.ask(arg -> true, Long.class, "Enter y (long)", errMessage,
                errMessage, false);

        return new Location(x, y, name);
    }
}
