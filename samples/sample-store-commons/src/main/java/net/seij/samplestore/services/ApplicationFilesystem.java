package net.seij.samplestore.services;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class ApplicationFilesystem {
    private static FileSystem fileSystem = FileSystems.getDefault();

    public static FileSystem getFileSystem() {
        return fileSystem;
    }

    public static void setFileSystem(FileSystem fileSystem) {
        ApplicationFilesystem.fileSystem = fileSystem;
    }
}
