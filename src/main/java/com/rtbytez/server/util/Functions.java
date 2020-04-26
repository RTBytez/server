package com.rtbytez.server.util;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.*;
import com.rtbytez.server.file.Line;

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
