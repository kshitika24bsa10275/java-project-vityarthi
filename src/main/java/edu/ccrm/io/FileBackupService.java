package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Backup utilities using NIO.2
 */
public final class FileBackupService {
    private FileBackupService() { }

    public static Path backupData(Path sourceDir) throws IOException {
        if (!Files.isDirectory(sourceDir)) throw new IllegalArgumentException("sourceDir must be a directory");
        String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path target = sourceDir.resolveSibling(sourceDir.getFileName().toString() + "_backup_" + stamp);
        Files.createDirectories(target);

        Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relative = sourceDir.relativize(dir);
                Path targetDir = target.resolve(relative);
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relative = sourceDir.relativize(file);
                Path targetFile = target.resolve(relative);
                Files.copy(file, targetFile, new CopyOption[0]);
                return FileVisitResult.CONTINUE;
            }
        });
        return target;
    }

    public static long calculateDirectorySize(Path dir) throws IOException {
        if (!Files.exists(dir)) return 0L;
        AtomicLong size = new AtomicLong(0);
        Files.walkFileTree(dir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                size.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }
        });
        return size.get();
    }
}
