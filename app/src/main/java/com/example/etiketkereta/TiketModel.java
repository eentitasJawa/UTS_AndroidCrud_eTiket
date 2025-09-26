package com.example.etiketkereta;

public class TiketModel {
    private int id, jumlah, harga, bayar;
    private String nama, tujuan, tanggal;

    public TiketModel(int id, String nama, String tujuan, String tanggal, int jumlah, int harga, int bayar) {
        this.id = id;
        this.nama = nama;
        this.tujuan = tujuan;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.harga = harga;
        this.bayar = bayar;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getTujuan() { return tujuan; }
    public String getTanggal() { return tanggal; }
    public int getJumlah() { return jumlah; }
    public int getHarga() { return harga; }
    public int getBayar() { return bayar; }

    public void setNama(String nama) { this.nama = nama; }
    public void setTujuan(String tujuan) { this.tujuan = tujuan; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setHarga(int harga) { this.harga = harga; }
    public void setBayar(int bayar) { this.bayar = bayar; }
}
