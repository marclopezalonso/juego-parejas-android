package com.example.jocparelles;

public class Carta {
    private int id;
    private int imagenId;
    private boolean volteada;
    private boolean emparejada;

    public Carta(int id, int imagenId) {
        this.id = id;
        this.imagenId = imagenId;
        this.volteada = false;
        this.emparejada = false;
    }

    public int getId() { return id; }
    public int getImagenId() { return imagenId; }
    public boolean isVolteada() { return volteada; }
    public boolean isEmparejada() { return emparejada; }

    public void setVolteada(boolean volteada) { this.volteada = volteada; }
    public void setEmparejada(boolean emparejada) { this.emparejada = emparejada; }
}