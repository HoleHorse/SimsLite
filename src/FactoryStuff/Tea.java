package FactoryStuff;

public class Tea implements DrinkFact {
    @Override
    public String getDrink() {
        return "You ordered Tea";
    }
}
