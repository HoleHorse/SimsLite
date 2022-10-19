package FactoryStuff;

public class Tea implements DrinkFact {
    @Override
    public void getDrink() {
        System.out.println("You ordered Tea");
    }
}
