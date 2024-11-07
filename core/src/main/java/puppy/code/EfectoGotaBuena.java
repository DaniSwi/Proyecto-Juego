package puppy.code;

public class EfectoGotaBuena implements EstrategiaEfecto {

    public GotaBuena gotaBuena;

    public EfectoGotaBuena(GotaBuena gotaBuena) {
        this.gotaBuena = gotaBuena;
    }

    public void aplicarEfecto(Tarro tarro) {
        gotaBuena.aplicarEfecto(tarro);
    }

}
