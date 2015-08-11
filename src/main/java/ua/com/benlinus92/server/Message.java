package ua.com.benlinus92.server;

import java.io.*;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
 	private String date;
	private String from;
	private String to;
	private String text;
	
	public Message() { }
	public Message(String from, String text, String date_milis) {
		this.from = from;
		this.text = text;
		this.date = date_milis;
		this.to = "";
	}
	public Message(String from, String to, String text, String date_milis) {
		this.from = from;
		this.to = to;
		this.text = text;
		this.date = date_milis;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return this.text;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFrom() {
		return this.from;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTo() {
		return this.to;
	}
	public void setDate(String date_milis) {
		this.date = date_milis;
	}
	public String getDate() {
		return this.date;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("[").append(date)
				.append(", From: ").append(from).append(", To: ").append(to)
				.append("] ").append(text).toString();
	}
}
