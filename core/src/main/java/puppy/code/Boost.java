package puppy.code;

public interface Boost {
    public void boost(Tarro tarro);
    float duracionBoost = 5.0f;
    public String getNombreBoost();
    public int getTiempoRestante();
    public String getTeclaActivacion();
}
