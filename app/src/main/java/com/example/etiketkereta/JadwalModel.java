package com.example.etiketkereta;

public class JadwalModel {
    private String asal, tujuan, jam;
    private int harga;

    public JadwalModel(String asal, String tujuan, String jam, int harga) {
        this.asal = asal;
        this.tujuan = tujuan;
        this.jam = jam;
        this.harga = harga;
    }

    public String getAsal() { return asal; }
    public String getTujuan() { return tujuan; }
    public String getJam() { return jam; }
    public int getHarga() { return harga; }
}
