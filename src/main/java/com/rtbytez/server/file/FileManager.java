package com.rtbytez.server.file;

import java.util.HashMap;

public class FileManager {

    private HashMap<String, File> files = new HashMap<>();

    public FileManager() {

    }

    /**
     * Create a file located at a path
     *
     * @param path The path at where the file is stored
     * @return The file that is created in memory
     */
    public File createFile(String path) {
        File file = new File();
        files.put(path, file);
        return file;
    }

    /**
     * Retrieve a file by it's path
     *
     * @param path The path at where the file is stored
     * @return The file at that path
     */
    public File getFile(String path) {
        return files.get(path);
    }

    /**
     * Delete a file in memory at a path
     *
     * @param path The path at where the file to delete is located
     */
    public void deleteFile(String path) {
        files.remove(path);
    }

}
