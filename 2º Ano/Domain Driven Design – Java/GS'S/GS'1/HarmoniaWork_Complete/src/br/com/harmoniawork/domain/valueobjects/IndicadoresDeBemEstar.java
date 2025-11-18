package br.com.harmoniawork.domain.valueobjects;

public class IndicadoresDeBemEstar {
    private int fadiga; // 0-100 (maior = mais fadiga)
    private int ergonomia; // 0-100 (maior = melhor)
    private int luz; // 0-100 (adequação)
    private int qualidadeAr; // 0-100
    private int clima; // 0-100

    public IndicadoresDeBemEstar(int fadiga, int ergonomia, int luz, int qualidadeAr, int clima) {
        this.fadiga = clamp(fadiga);
        this.ergonomia = clamp(ergonomia);
        this.luz = clamp(luz);
        this.qualidadeAr = clamp(qualidadeAr);
        this.clima = clamp(clima);
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }

    public int getFadiga() { return fadiga; }
    public int getErgonomia() { return ergonomia; }
    public int getLuz() { return luz; }
    public int getQualidadeAr() { return qualidadeAr; }
    public int getClima() { return clima; }

    public void reduzirFadiga(int delta) {
        this.fadiga = clamp(this.fadiga - delta);
    }

    public void melhorarErgonomia(int delta) {
        this.ergonomia = clamp(this.ergonomia + delta);
    }

    @Override
    public String toString() {
        return String.format("Fadiga:%d Ergonomia:%d Luz:%d Ar:%d Clima:%d",
                fadiga, ergonomia, luz, qualidadeAr, clima);
    }
}
