package at.crimsonbit.viergewinntbot.adapter;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ExceptionAdapter implements JsonSerializer<Exception> {
	@Override
	public JsonElement serialize(Exception src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("cause", new JsonPrimitive(String.valueOf(src.getCause())));
		jsonObject.add("message", new JsonPrimitive(src.getMessage()));
		return jsonObject;
	}
}