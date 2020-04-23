package com.rtbytez.server.util;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.*;
import com.rtbytez.server.file.Line;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Functions {

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private Functions() {

    }

    /**
     * Return a random set of 8 characters in a row
     *
     * @return The random set of 8 characters
     */
    public static String s4() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 7);
    }

    /**
     * Have a logger that is annoying and needs disabling?
     * Well then run this method to get you out of your logger problems. Don't wait, run today!
     */
    public static void disableLoggerFor(Class clazz) {
        SimpleLogger logger = (SimpleLogger) LoggerFactory.getLogger(clazz);
        try {
            Field field = SimpleLogger.class.getDeclaredField("currentLogLevel");
            field.setAccessible(true);
            field.set(logger, 25);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resolve a line conflict where edits were made very closely.
     *
     * @param currentLine The current line that the server has in memory
     * @param first       The first message that came in by server timestamp
     * @param closeSecond The second line that came in by server timestamp
     * @return The final line
     */
    public static Line resolveConflict(Line currentLine, Line first, Line closeSecond) {
        String currentLineText = currentLine.getText();
        String closeSecondText = closeSecond.getText();
        String firstText = first.getText();
        String finalLine = currentLineText;
        try {
            finalLine = resolve(currentLineText, firstText, closeSecondText);
        } catch (DiffException e) {
            e.printStackTrace();
        }
        currentLine.setText(finalLine);
        currentLine.flag(true);
        return currentLine;
    }

    /**
     * Resolve conflict algorithm. Internal use only
     */
    private static String resolve(String original, String first, String second) throws DiffException {
        String result = "";
        List<String> originalList = Arrays.asList(original.split(""));
        List<String> secondList = Arrays.asList(second.split(""));
        Patch<String> patch = DiffUtils.diff(originalList, secondList);
        for (Object delta : patch.getDeltas()) {
            AbstractDelta<?> abstractDelta = (AbstractDelta<?>) delta;
            int position = abstractDelta.getSource().getPosition();
            int deltaLength = abstractDelta.getTarget().getLines().size();
            if (abstractDelta instanceof DeleteDelta) {
                result = first.substring(0, position) + first.substring(position + deltaLength);
            }
            if (abstractDelta instanceof ChangeDelta) {
                result = first.substring(0, position) + second.substring(position, position + deltaLength) + first.substring(position + deltaLength);
            }
            if (abstractDelta instanceof InsertDelta) {
                result = first.substring(0, position) + second.substring(position, position + deltaLength) + first.substring(position);
            }
        }
        return result;
    }
}
