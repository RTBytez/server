package com.rtbytez.server.file;

import java.util.HashMap;

public class FileManager {

    private HashMap<String, File> files = new HashMap<>();

    public FileManager() {

    }

    public File createFile(String path) {
        File file = new File();
        files.put(path, file);
        return file;
    }

    public File getFile(String path) {
        return files.get(path);
    }

    public void deleteFile(String path) {
        files.remove(path);
    }

}
