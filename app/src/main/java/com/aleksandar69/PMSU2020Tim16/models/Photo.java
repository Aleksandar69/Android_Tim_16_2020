package com.aleksandar69.PMSU2020Tim16.models;

public class Photo {
    private int _id;
    private String path;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Photo(int _id, String path) {
        this._id = _id;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "_id=" + _id +
                ", path='" + path + '\'' +
                '}';
    }
}
