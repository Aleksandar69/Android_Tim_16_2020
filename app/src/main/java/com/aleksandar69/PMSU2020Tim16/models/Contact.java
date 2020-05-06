package com.aleksandar69.PMSU2020Tim16.models;

import com.aleksandar69.PMSU2020Tim16.R;

public class Contact {
    private String first;
    private String last;
    private String display;
    private String email;
    private Integer imageSourceID;

    @Override
    public String toString() {
        return this.display + "\n" + this.email ;
    }

    public String getLast() {
        return last;
    }

    public Integer getImageSourceID() {
        return imageSourceID;
    }

    public String getFirst() {
        return first;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplay() {
        return display;
    }

    public Contact(String first, String last, String display, String email, Integer imageSourceID) {
        this.first = first;
        this.last = last;
        this.display = display;
        this.email = email;
        this.imageSourceID = imageSourceID;
    }

    //postaviti nove slike u drawable

    public static final Contact[] contacts = {
      new Contact("Elena", "Krunic", "Elena Krunic", "elenakrunic@gmail.com", R.mipmap.ic_launcher_round),
      new Contact("Vincent", "Andolini", "Vincent Andolini", "vincent@gmail.com", R.mipmap.ic_launcher_round),
      new Contact("Mico", "Micic", "Mico Micic", "mico@gmail.com",  R.mipmap.ic_launcher_round)
    };
}
