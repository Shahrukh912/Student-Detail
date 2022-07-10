package com.srk.studentdetails;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class Modal implements Serializable {
    public int _id;
    public String name;
    public String imageId;
    public Bitmap image;
    public ArrayList<Score> scores = new ArrayList<Score>();

    public Bitmap getImage() { return image;}

    public void setImage(Bitmap image) { this.image = image; }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

}

class Score implements Serializable{
    public double score;
    public String type;

    public Score(double score, String type) {
        this.score = score;
        this.type = type;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
