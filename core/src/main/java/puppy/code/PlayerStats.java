package puppy.code;

public class PlayerStats { //Patron singleton
    private static PlayerStats instance;
    private int highScore;
    private int intentos;

    private PlayerStats() {
        highScore = 0;
        intentos = 0;
    }

    public static PlayerStats getInstance() {
        if (instance == null)
            instance = new PlayerStats();
        return instance;
    }

    public void updateHighScore(int score) {
        if(score > highScore)
            highScore = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void updateIntentos() {
        ++intentos;
    }

    public int getIntentos() {
        return intentos;
    }

}
