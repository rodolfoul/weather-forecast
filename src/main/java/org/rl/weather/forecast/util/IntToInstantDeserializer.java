package org.rl.weather.forecast.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;
import java.time.Instant;

public class IntToInstantDeserializer extends JsonDeserializer<Instant> {
	@Override
	public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		TreeNode t = p.getCodec().readTree(p);
		if (t instanceof IntNode) {
			return Instant.ofEpochSecond(((IntNode) t).asInt());
		}
		return null;
	}
}
