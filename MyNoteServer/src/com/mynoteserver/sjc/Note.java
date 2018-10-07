package com.mynoteserver.sjc;

import java.io.Serializable;

public class Note implements Serializable{
	   private String keyWord;
	    private String note = "";

	    public String getKeyWord() {
	        return keyWord;
	    }

	    public void setKeyWord(String keyWord) {
	        this.keyWord = keyWord;
	    }

	    public String getNote() {
	        return note;
	    }

	    public void setNote(String note) {
	        this.note = note;
	    }

	    public Note(String keyWord, String note) {
	        this.keyWord = keyWord;
	        this.note = note;
	    }

	    public Note() {

	    }

}
