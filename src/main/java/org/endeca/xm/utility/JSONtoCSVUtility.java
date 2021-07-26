package org.endeca.xm.utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.endeca.xm.constants.KeyValueConstants.SEARCH_TERMS;
import static org.endeca.xm.constants.KeyValueConstants.SYNONYMS;
import static org.endeca.xm.constants.KeyValueConstants.TYPE;
import static org.endeca.xm.constants.KeyValueConstants.UNIQUE_ID;

public class JSONtoCSVUtility {

    public static void main(final String[] args) throws IOException {
        final File input = new File("./src/main/resources/endeca-thesaurus.json");
        final File outputFile = new File("./output/thesaurus.csv");
        final Map<String, Object> thesaurusRoot = new ObjectMapper().readValue(input, Map.class);
        final CsvMapper csvMapper = getCsvMapper();
        final CsvSchema csvSchema = getCsvSchema();

        writeToJson(outputFile, thesaurusRoot, csvMapper, csvSchema);
    }

    private static void writeToJson(final File outputFile, final Map<String, Object> thesaurusRoot,
        final CsvMapper csvMapper, final CsvSchema csvSchema) {
        final ObjectMapper mapper = new ObjectMapper();
        final List<JsonNode> thesaurusEntries = new ArrayList<>();
        thesaurusRoot.entrySet()
            .stream()
            .filter(field -> !field.getKey().startsWith("ecr"))
            .forEach(entry -> {
                ((LinkedHashMap<String, Object>) entry.getValue()).put(UNIQUE_ID, entry.getKey());
                writeToCSV(outputFile, csvMapper, csvSchema, thesaurusEntries, mapper.valueToTree(entry.getValue()));
            });
    }

    private static void writeToCSV(final File outputFile, final CsvMapper csvMapper, final CsvSchema csvSchema,
        final List<JsonNode> thesaurusEntries, final JsonNode jsonNode) {
        thesaurusEntries.add(jsonNode);
        try {
            csvMapper.writerFor(List.class)
                .with(csvSchema)
                .writeValue(outputFile, thesaurusEntries);
        } catch (final IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static CsvMapper getCsvMapper() {
        final CsvMapper csvMapper = new CsvMapper();
        csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        return csvMapper;
    }

    private static CsvSchema getCsvSchema() {
        return CsvSchema.builder()
            .addColumn(SEARCH_TERMS)
            .addColumn(SYNONYMS)
            .addColumn(TYPE)
            .addColumn(UNIQUE_ID).build().withHeader();
    }
}