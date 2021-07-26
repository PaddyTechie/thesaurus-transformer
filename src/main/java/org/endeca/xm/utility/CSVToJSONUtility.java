package org.endeca.xm.utility;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.endeca.xm.constants.KeyValueConstants;
import org.endeca.xm.pojo.Thesaurus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CSVToJSONUtility {

    public static void main(final String[] args) throws IOException {
        final CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
        final CsvMapper csvMapper = new CsvMapper();
        final File input = new File("./src/main/resources/thesaurus.csv");
        final File output = new File("./output/thesaurus.json");

        try (MappingIterator<Thesaurus> thesaurus = csvMapper.readerFor(Thesaurus.class)
            .with(csvSchema)
            .readValues(input)) {
            final Map<String, ObjectNode> individualEntry = getCSVToMap(thesaurus);
            writeMapToJson(output, individualEntry);
        }
    }

    private static Map<String, ObjectNode> getCSVToMap(final MappingIterator<Thesaurus> thesaurus) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, ObjectNode> csvToMap = new HashMap<>();
        thesaurus.readAll().forEach(entry -> buildThesaurusEntriesForEndeca(mapper, csvToMap, entry));
        return csvToMap;
    }

    private static void buildThesaurusEntriesForEndeca(final ObjectMapper mapper, final Map<String, ObjectNode> csvToMap, final Thesaurus entry) {
        final ObjectNode objectNode = mapper.createObjectNode();
        if (!isBlank(entry.getSearchTerms())) {
            objectNode.put(KeyValueConstants.SEARCH_TERMS, entry.getSearchTerms());
        }
        objectNode.putPOJO(KeyValueConstants.SYNONYMS, entry.getSynonyms());
        objectNode.put(KeyValueConstants.TYPE, entry.getType());
        objectNode.put(KeyValueConstants.ECR_TYPE, KeyValueConstants.TYPE_THESAURUS_ENTRY);
        csvToMap.put(entry.getUniqueId(), objectNode);
    }

    private static void writeMapToJson(final File output, final Map<String, ObjectNode> individualEntry) throws IOException {
        new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .writeValue(output, individualEntry);
    }
}
