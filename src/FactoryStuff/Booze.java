package FactoryStuff;

public class Booze implements DrinkFact {
    @Override
    public void getDrink() {
        System.out.println("You ordered Booze");
    }
}
