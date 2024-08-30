import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFilesVisitor extends SimpleFileVisitor<Path> {
    private final Path from;
    private final Path target;

    public MyFilesVisitor(Path from, Path target){
        this.from = from;
        this.target = target;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path relativePath = from.relativize(file);
        Path destinationPath = target.resolve(relativePath);
        Files.copy(file,destinationPath,StandardCopyOption.REPLACE_EXISTING);
        return FileVisitResult.CONTINUE;
    }
}
