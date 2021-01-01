package com.example.myapplication;

public class Queries {
    private int id;
    private boolean status;
    private String kegiatan;
    private boolean penting;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public boolean isPenting() {
        return penting;
    }

    public void setPenting(boolean penting) {
        this.penting = penting;
    }
}
