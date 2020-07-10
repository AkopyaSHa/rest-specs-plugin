package ru.uip.contract.generator;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.uip.contract.parser.ContractDescription;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
public class SpecGeneratorTest {

    @Test
    public void testGenerateSpecWithParams() {
        final SpecGenerator generator = new SpecGenerator("src/test/api/snippets");

        Map<String, Set<ContractDescription>> operationContracts = new HashMap<>();
        ContractDescription description = new ContractDescription("Its name", "Its desc");
        Set<ContractDescription> contractDescriptions = new HashSet<>();
        contractDescriptions.add(description);
        operationContracts.put("Test", contractDescriptions);

        final Map<String, String> spec = generator.generateSpecs(operationContracts);
        final String separator = System.getProperty("line.separator");
        final String expected = spec.get("Test").replaceAll(separator, " ");

        assertThat(expected, equalTo(" ===== Its name  Its desc  Запрос:  include::src/test/api/snippets/validate_its_name/http-request.adoc[]  Ответ:  include::src/test/api/snippets/validate_its_name/http-response.adoc[]  "));
    }

    @Test
    public void testGenerateSpecWithDefaults() {
        final SpecGenerator generator = new SpecGenerator("snippets");

        Map<String, Set<ContractDescription>> operationContracts = new HashMap<>();
        ContractDescription description = new ContractDescription("Its name", "Its desc");
        Set<ContractDescription> contractDescriptions = new HashSet<>();
        contractDescriptions.add(description);
        operationContracts.put("Test", contractDescriptions);

        final Map<String, String> spec = generator.generateSpecs(operationContracts);
        final String separator = System.getProperty("line.separator");
        final String expected = spec.get("Test").replaceAll(separator, " ");

        assertThat(expected, equalTo(" ===== Its name  Its desc  Запрос:  include::snippets/validate_its_name/http-request.adoc[]  Ответ:  include::snippets/validate_its_name/http-response.adoc[]  "));
    }

    @Test
    public void testTemplateNotExists() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SpecGenerator("/unknown", "snippets")
        );
    }
}
