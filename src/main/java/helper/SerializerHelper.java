package helper;


import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

import model.Event;
import model.ParticipantDetails;


public class SerializerHelper {
	public String bufferedReaderToString(BufferedReader reader) throws IOException {
		StringBuilder buffer = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	public Event bufferedReaderToJavaObject(BufferedReader reader) throws IOException,NumberFormatException {
		String javaString = bufferedReaderToString(reader);
		Event data = new Gson().fromJson(javaString, Event.class);
		return data;
	}
	public ParticipantDetails bufferedReaderToParticipantObject(BufferedReader reader) throws IOException {
		String javaString = bufferedReaderToString(reader);
		ParticipantDetails data = new Gson().fromJson(javaString, ParticipantDetails.class);
		return data;
	}
	public String javaObjectToJson(Object data) {
		String jsonString = new String();
		try {
			jsonString = new Gson().toJson(data);
		} catch (NullPointerException e) {
			System.out.print("Null pointer exception");
		}
		return jsonString;
	}
	
	public  < T >  T  propertyToObject(String data, Class<T> obj) {
		return new Gson().fromJson(data, obj);
	   }
}
