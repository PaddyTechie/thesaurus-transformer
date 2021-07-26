package org.endeca.xm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Thesaurus {

    @JsonProperty
    private String       uniqueId;
    @JsonProperty
    private String       searchTerms;
    @JsonProperty
    private List<String> synonyms;
    @JsonProperty
    private String       type;
    @JsonProperty("ecr:lastModifiedBy")
    private String       lastModifiedBy;
    @JsonProperty("ecr:lastModified")
    private String       lastModified;
    @JsonProperty("ecr:createDate")
    private String       createDate;

    public String getUniqueId() {
        return uniqueId;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public String getType() {
        return type;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getCreateDate() {
        return createDate;
    }
}
