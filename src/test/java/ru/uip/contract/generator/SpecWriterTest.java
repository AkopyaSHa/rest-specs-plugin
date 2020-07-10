package ru.uip.contract.generator;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.io.FileMatchers.aReadableFile;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
public class SpecWriterTest {

    @Test
    public void testWriteSpec() throws IOException {
        String outputDir = "build/testspec";
        SpecWriter writer = new SpecWriter(outputDir);

        Map<String, String> specs = new HashMap<>();
        specs.put("test", "Spec");
        writer.write(specs);

        final File file = new File(outputDir + "/" + "test.adoc");
        assertThat(file, aReadableFile());

        final String content = Files.readString(Path.of(file.getCanonicalPath()));
        assertThat(content, equalTo("Spec"));
    }

    @Test
    public void testWriteInvalidFile() throws IOException {
        String outputDir = "build/testspec";
        SpecWriter writer = new SpecWriter(outputDir);

        Map<String, String> specs = new HashMap<>();
        specs.put("test/", "Spec");
        assertThrows(RuntimeException.class, () -> writer.write(specs));
    }
}
