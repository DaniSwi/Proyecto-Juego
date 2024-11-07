package puppy.code;

public class EfectoGotaNormal implements EstrategiaEfecto {

    private GotaNormal gotaNormal;

    public EfectoGotaNormal(GotaNormal gotaNormal) {
        this.gotaNormal = gotaNormal;
    }

    public void aplicarEfecto(Tarro tarro) {
        gotaNormal.aplicarEfecto(tarro);
    }
}
