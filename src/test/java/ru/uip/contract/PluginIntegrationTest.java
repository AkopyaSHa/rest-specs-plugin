package ru.uip.contract;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.uip.contract.plugin.SpecPluginExtension;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.aReadableFile;
import static ru.uip.contract.plugin.SpecPlugin.PLUGIN_ID;
import static ru.uip.contract.plugin.SpecPlugin.TASK_ID;

@Tag("integration")
public class PluginIntegrationTest {
    private Project project;

    @BeforeEach
    public void setUp() {
        this.project = ProjectBuilder.builder()
                .withProjectDir(new File("./"))
                .withGradleUserHomeDir(new File(System.getProperty("java.io.tmpdir")))
                .build();
        this.project.getPluginManager().apply(PLUGIN_ID);
    }

    @Test
    public void testExtensionConfigs() {
        final SpecPluginExtension extension = project.getExtensions().getByType(SpecPluginExtension.class);
        extension.setApiSpec("./src/test/api/api.yaml");
        final Task task = project.getTasks().getByName(TASK_ID);
        final ConfigurableFileCollection files = project.files("./src/test/contracts/getAccounts.yml");
        Map<String, ConfigurableFileCollection> contracts = new HashMap<>();
        contracts.put("GetAccounts", files);
        extension.setOperationContracts(contracts);

        extension.setTemplate("src/main/resources/spec.mustache");

        extension.setOutputDir(project.getBuildDir() + "/generated-snippets/contract-description");

        task.getActions().get(0).execute(task);

        assertThat(
                new File("build/generated-snippets/contract-description/GetAccounts.adoc"),
                aReadableFile()
        );
    }

    @Test
    public void testExtensionConfigsEmptyTemplate() {
        final SpecPluginExtension extension = project.getExtensions().getByType(SpecPluginExtension.class);
        extension.setApiSpec("./src/test/api/api.yaml");
        final Task task = project.getTasks().getByName(TASK_ID);
        final ConfigurableFileCollection files = project.files("./src/test/contracts/getAccounts.yml");
        Map<String, ConfigurableFileCollection> contracts = new HashMap<>();
        contracts.put("GetAccounts", files);
        extension.setOperationContracts(contracts);

        extension.setOutputDir(project.getBuildDir() + "/generated-snippets/contract-description");

        task.getActions().get(0).execute(task);

        assertThat(
                new File("build/generated-snippets/contract-description/GetAccounts.adoc"),
                aReadableFile()
        );
    }

}
