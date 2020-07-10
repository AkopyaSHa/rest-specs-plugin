package ru.uip.contract.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class SpecWriter {

    private final String outputDir;

    public SpecWriter(String outputDir) {
        this.outputDir = outputDir;
    }

    public void write(Map<String, String> contractSpecs) {
        for(Map.Entry<String, String> spec: contractSpecs.entrySet()) {
            try {
                new File(outputDir).mkdirs();
                Path path = Paths.get(outputDir, spec.getKey() + ".adoc");

                Files.deleteIfExists(path);
                Files.write(path, spec.getValue().getBytes(UTF_8), CREATE_NEW);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
