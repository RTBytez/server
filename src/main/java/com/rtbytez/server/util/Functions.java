package com.rtbytez.server.util;

import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.PatchFailedException;
import com.rtbytez.server.file.Line;

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
    public static Line resolveConflict(Line currentLine, Line first, Line closeSecond) throws PatchFailedException, DiffException {
        ConflictAlgorithm conflictAlgorithm = new ConflictAlgorithm();
        int numChanges = 0;
        String currentLineText = currentLine.getText();
        String closeSecondText = closeSecond.getText();
        String firstText = first.getText();
        String finalLine = conflictAlgorithm.resolve(currentLineText, firstText, closeSecondText);
        return new Line(currentLine.getId(), currentLine.getLineNumber(), finalLine);
    }
}
