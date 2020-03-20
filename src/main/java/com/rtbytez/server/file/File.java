package com.rtbytez.server.file;

import com.rtbytez.server.util.Functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class File {

    HashMap<String, Line> lines = new HashMap<>();
    List<Line> linesInOrder = new ArrayList<>();

    public File() {

    }

    public Line newLine(int lineNumber) {
        if (lineNumber == 0) {
            lineNumber = 1;
        }
        Line line = new Line(Functions.s4(), lineNumber, "");
        lines.put(line.getId(), line);
        linesInOrder.add(lineNumber - 1, line);

        // Loop all lines that come after this one and add 1 to the line number
        for (int i = lineNumber; i < linesInOrder.size(); i++) {
            Line editingLine = linesInOrder.get(i);
            editingLine.setLineNumber(i + 1);
        }

        return line;
    }

    public void deleteLineById(String id) {
        Line line = lines.get(id);
        lines.remove(id);
        linesInOrder.remove(line);

        // Loop all lines that come after this one and subtract 1 to the line number
        for (int i = line.getLineNumber(); i < linesInOrder.size(); i++) {
            Line editingLine = linesInOrder.get(i);
            editingLine.setLineNumber(i - 1);
        }

    }

    public void deleteLineByLineNumber(int lineNumber) {
        Line line = linesInOrder.get(lineNumber);
        lines.remove(line.getId());
        linesInOrder.remove(lineNumber);

        // Loop all lines that come after this one and subtract 1 to the line number
        for (int i = line.getLineNumber(); i < linesInOrder.size(); i++) {
            Line editingLine = linesInOrder.get(i);
            editingLine.setLineNumber(i - 1);
        }

    }

    public Line getLineById(String id) {
        return lines.get(id);
    }

    public Line getLineByLineNumber(int lineNumber) {
        return linesInOrder.get(lineNumber - 1);
    }

    public String buildFile() {
        if (!linesInOrder.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            linesInOrder.forEach(line -> stringBuilder.append(line.toString()).append("\n"));
            String str = stringBuilder.toString();
            return str.substring(0, str.length() - 1);
        }
        return "";
    }

}
