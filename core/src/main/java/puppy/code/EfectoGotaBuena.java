package puppy.code;

public class EfectoGotaBuena implements EstrategiaEfecto {

    private GotaBuena gotaBuena;

    public EfectoGotaBuena(GotaBuena gotaBuena) {
        this.gotaBuena = gotaBuena;
    }

    public void aplicarEfecto(Tarro tarro) {
        gotaBuena.reproducirSonido();
        tarro.aumentarVida();
    }

}
