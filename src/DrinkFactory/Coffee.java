package DrinkFactory;

public class Coffee implements DrinkFact {
    @Override
    public String getDrink() {
        return "You ordered Coffee";
    }
}
