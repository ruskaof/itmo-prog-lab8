package com.ruskaof.client.util;

import com.ruskaof.common.data.Coordinates;
import com.ruskaof.common.data.Country;
import com.ruskaof.common.data.FormOfEducation;
import com.ruskaof.common.data.Location;
import com.ruskaof.common.data.Person;
import com.ruskaof.common.data.Semester;
import com.ruskaof.common.data.StudyGroup;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Asks and receives user input data to make a StudyGroup/username&password object.
 */
public class DataObjectsMaker {
    private static final int MAX_STRING_LENGTH = 100;
    private static final String ERROR_MESSAGE = "Your enter was not correct type. Try again";
    private final LinkedList<String> linkedList;
    private final Asker asker;
    private final String authorName;

    public DataObjectsMaker(LinkedList<String> data, String authorName) {
        this.linkedList = data;
        this.asker = new Asker(data);
        this.authorName = authorName;
    }


    public StudyGroup makeStudyGroup() throws IOException {
        String name = asker.ask(arg -> (arg).length() > 0 && arg.length() < MAX_STRING_LENGTH, "Enter name (String)",
                ERROR_MESSAGE, "The string must not be empty and shorter than 100 chars", x -> x, false);

        Integer studentsCount = asker.ask(arg -> (arg) > 0, "Enter studentsCount (int) (can be null)",
                ERROR_MESSAGE, "Your int must be >0. Try again", Integer::parseInt, true); // >0, null-able

        FormOfEducation formOfEducation = asker.ask(arg -> true, "Enter formOfEducation (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES)",
                ERROR_MESSAGE, ERROR_MESSAGE, FormOfEducation::valueOf, false); //not null

        Semester semesterEnum = asker.ask(arg -> true, "Enter semesterEnum (THIRD, FIFTH, SIXTH, SEVENTH) (can be null)",
                ERROR_MESSAGE, ERROR_MESSAGE, Semester::valueOf, true); // null-able

        Coordinates coordinates = askForCoordinates(); //not null
        Person groupAdmin = askForGroupAdmin(); //not null
        return new StudyGroup(name, coordinates, studentsCount,
                formOfEducation, semesterEnum, groupAdmin, LocalDate.now(), authorName);
    }

    private Coordinates askForCoordinates() throws IOException {
        final long xLimitation = -896;
        final double yLimitation = 135;
        long x = asker.ask(arg -> (arg) > xLimitation, "Enter x (long)",
                ERROR_MESSAGE, "The long must be >-896. Try again", Long::parseLong, false); //> -896
        Double y = asker.ask(arg -> (arg) <= yLimitation, "Enter y (Double)",
                ERROR_MESSAGE, "The double must be <= 135. Try again", Double::parseDouble, false); //<=135, not null
        return new Coordinates(x, y);
    }

    private Person askForGroupAdmin() throws IOException {

        String name = asker.ask(arg -> (arg).length() > 0 && arg.length() < MAX_STRING_LENGTH, "Enter name (String)",
                ERROR_MESSAGE, "The string must not be empty and shorter than 100 chars. Try again", x -> x, false);

        Integer height = asker.ask(arg -> (arg) > 0, "Enter height (Integer)",
                ERROR_MESSAGE, "The integer must be >0. Try again", Integer::parseInt, false);

        Country nationality = asker.ask(arg -> true, "Enter country (RUSSIA, SPAIN, INDIA, THAILAND, NORTH_KOREA) (can be null)",
                ERROR_MESSAGE, ERROR_MESSAGE, Country::valueOf, true);

        Location location = askForLocation();

        return new Person(name, height, nationality, location);
    }

    private Location askForLocation() throws IOException {
        String name = asker.ask(arg -> (arg).length() > 0 && arg.length() < MAX_STRING_LENGTH, "Enter name (String) (can be null)",
                ERROR_MESSAGE, "The string must not be empty and shorter than 100 chars. Try again", x -> x, true);
        float x = asker.ask(arg -> true, "Enter x (float)", ERROR_MESSAGE,
                ERROR_MESSAGE, Float::parseFloat, false);
        long y = asker.ask(arg -> true, "Enter y (long)", ERROR_MESSAGE,
                ERROR_MESSAGE, Long::parseLong, false);

        return new Location(x, y, name);
    }

    public static class Asker {

        private final LinkedList<String> data;

        public Asker(LinkedList<String> data) {
            this.data = data;
        }

        public <T> T ask(Predicate<T> predicate,
                         String askMessage,
                         String errorMessage,
                         String wrongValueMessage,
                         Function<String, T> converter,
                         boolean nullable) throws IOException {
            String input;
            T value;
            do {
                try {
                    System.out.println(askMessage);
                    input = data.getFirst().trim();
                    data.removeFirst();
                    if ("".equals(input) && nullable) {
                        return null;
                    }

                    value = converter.apply(input);

                } catch (IllegalArgumentException e) {
                    System.out.println(errorMessage);
                    continue;
                }
                if (predicate.test(value)) {
                    return value;
                } else {
                    System.out.println(wrongValueMessage);
                }
            } while (true);
        }
    }
}
