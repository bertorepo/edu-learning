package com.hubert.constants;

import java.util.ArrayList;
import java.util.List;

public class MessageResponse {
	private static final String message = "message";
	private static final String messageType = "messageType";

	public static List<String> generateMessage(String message, String messageType) {
		List<String> listString = new ArrayList<>();
		listString.add(message);
		listString.add(messageType);
		return listString;
	}

	public String getMessage() {
		return message;
	}

	public String getMessageType() {
		return messageType;
	}

}
