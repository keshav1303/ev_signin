package com.evault.eb.DTO;


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
	public String getEventDocType() {
		return event;
	}
	public void setEventDocType(String eventDocType) {
		this.event = eventDocType;
	}
	@Override
	public String toString() {
		return "Pehchan [Registration=" + Registration + ", year=" + year + ", eventDocType=" + event
				+ ", getRegistration()=" + getRegistration() + ", getYear()=" + getYear() + ", getEventDocType()="
				+ getEventDocType() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
    
    
}
