package ru.uip.contract.generator;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import ru.uip.contract.parser.ContractDescription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpecGenerator {

    private final MustacheFactory mf;
    private final Mustache m;

    public SpecGenerator(String templateName) {
        try {
            Path path = Path.of(templateName);
            final BufferedReader templateReader = Files.newBufferedReader(path);
            this.mf = new DefaultMustacheFactory();
            this.m = mf.compile(templateReader, "spec.mustache");
            templateReader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public SpecGenerator() {
        this.mf = new DefaultMustacheFactory();
        this.m = mf.compile("spec.mustache");
    }

    public Map<String, String> generateSpecs(Map<String, Set<ContractDescription>> operationContracts) {

        Map<String, String> operationDocs = new HashMap<>();
        for(Map.Entry<String, Set<ContractDescription>> operationContract: operationContracts.entrySet()) {
            final Set<ContractDescription> descriptions = operationContract.getValue();
            final String operationId = operationContract.getKey();
            final String operationDescription = generateOperationDescription(descriptions);
            operationDocs.put(operationId, operationDescription);
        }
        return operationDocs;
    }

    public String generateOperationDescription(Set<ContractDescription> descriptions) {
        StringWriter writer = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        context.put("descriptions", descriptions);
        m.execute(writer, context);
        return writer.toString();
    }

}
