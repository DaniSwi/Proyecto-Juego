package puppy.code;

public class EfectoGotaMala implements EstrategiaEfecto {

    private GotaMala gotaMala;

    public EfectoGotaMala(GotaMala gotaMala) {
        this.gotaMala = gotaMala;
    }

    public void aplicarEfecto(Tarro tarro) {
        gotaMala.aplicarEfecto(tarro);
    }
}
