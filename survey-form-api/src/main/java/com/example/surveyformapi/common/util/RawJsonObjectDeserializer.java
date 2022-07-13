package com.example.surveyformapi.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class RawJsonObjectDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);
        if (node.getNodeType() != JsonNodeType.OBJECT) {
            throw new JsonMappingException(jsonParser, "Json object type is expected for the field");
        }
        return mapper.writeValueAsString(node);
    }
}

