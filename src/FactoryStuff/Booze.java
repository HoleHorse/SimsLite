package FactoryStuff;

public class Booze implements DrinkFact {
    @Override
    public String getDrink() {
        return "You ordered Booze";
    }
}
