package views;

public enum State{
    ACT("ACT"),
    NSW("NSW"),
    NT("NT"),
    QLD("QLD"),
    SA("SA"),
    TAS("TAS"),
    VIC("VIC"),
    WA("WA");

    private String state;

    State(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
