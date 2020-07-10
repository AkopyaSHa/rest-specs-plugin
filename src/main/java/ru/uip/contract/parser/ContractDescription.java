package ru.uip.contract.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ContractDescription {

    private final static String NAME_PREFIX = "validate";
    private final static String NAME_DELIMITER = "_";


    private final String name;
    private final String description;
    @JsonIgnore
    private final String snippetName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ContractDescription(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
        snippetName = String.format("%s%s%s", NAME_PREFIX, NAME_DELIMITER, snippetName());
    }

    private String snippetName() {
       return name.toLowerCase()
               .replaceAll("\\s+", NAME_DELIMITER)
               .replaceAll("[^а-яА-Яa-zA-Z_$0-9]", "")
               .replaceAll("_+", NAME_DELIMITER);
    }
}
