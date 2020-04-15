package com.rtbytez.server.util;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.*;

import java.util.Arrays;
import java.util.List;

public class ConflictAlgorithm {
    public String resolve(String original, String first, String second) throws DiffException, PatchFailedException {
        String result = "";
        List<String> originalList = Arrays.asList(original.split(""));
        List<String> secondList = Arrays.asList(second.split(""));
        Patch patch = DiffUtils.diff(originalList, secondList);
        for (Object d : patch.getDeltas()) {
            AbstractDelta abstractDelta = (AbstractDelta) d;
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

