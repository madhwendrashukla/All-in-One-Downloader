package com.papayacoders.allinonedownloader.WhatsappDownloader.model;

public class ModelStatus {

    String full_path;
    String path;
    int type;
    String pack;

    public ModelStatus(String full_path) {
        this.full_path = full_path;
    }

    public ModelStatus(String full_path, String path) {
        this.full_path = full_path;
        this.path = path;
    }

    public ModelStatus(String full_path, String path, int type,String pack) {
        this.full_path = full_path;
        this.path = path;
        this.type = type;
        this.pack = pack;
    }

    public String getPack() {
        return pack;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFull_path() {
        return full_path;
    }

    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
