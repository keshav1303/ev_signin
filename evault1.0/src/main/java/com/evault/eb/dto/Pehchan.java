package com.evault.eb.dto;


public class Pehchan {
	
    public String Registration;
    public String year;
    public String event;
    
	public String getRegistration() {
		return Registration;
	}
	public void setRegistration(String registration) {
		Registration = registration;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	@Override
	public String toString() {
		return "Pehchan [Registration=" + Registration + ", year=" + year + ", event=" + event + "]";
	}
}
	
