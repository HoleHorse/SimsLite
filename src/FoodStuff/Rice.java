package FoodStuff;

import FoodStuff.Food;
import FoodStuff.FoodDecorator;

public class Rice extends FoodDecorator {

	public Rice(Food food) {
		super(food);
	}

	@Override
	public int getFoodPrice() {
		return super.getFoodPrice() + 200;
	}

	@Override
	public String getFoodName() {
		return super.getFoodName() + "with Rice ";
	}

}
