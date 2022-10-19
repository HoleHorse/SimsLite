package FactoryStuff;

public class Coffee implements DrinkFact {
    @Override
    public void getDrink() {
        System.out.println("You ordered Coffee");
    }
}
