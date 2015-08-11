package ua.com.benlinus92.server;

import java.lang.reflect.Modifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonMessages {
	public Message message[];
	public JsonMessages() { }
	public JsonMessages(Message[] msgs) {
		this.message = new Message[msgs.length];
		System.arraycopy(msgs, 0, this.message, 0, msgs.length);
	}
	
	public synchronized JsonObject createJsonObject() {
		Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).create();
		String json = gson.toJson(this);
		return new JsonParser().parse(json).getAsJsonObject();
	}
	
	public synchronized int getArraySize() {
		return this.message.length;
	}
}
