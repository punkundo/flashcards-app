/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablepane;

/**
 *
 * @author Admin
 */
public class table {
    private int id;
    private String english;
    private String vietnamese;
    private String pronunciation;
    private String note;
    private byte [] image;

    public table(int id, String english, String vietnamese, String pronunciation, String note, byte[] image) {
        this.id = id;
        this.english = english;
        this.vietnamese = vietnamese;
        this.pronunciation = pronunciation;
        this.note = note;
        this.image = image;
    }

    public int getId() {
        return id;
    }
    public String getEnglish() {
        return english;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getNote() {
        return note;
    }

    public byte[] getImage() {
        return image;
    }
    
}
   