package model;

import java.util.List;

public class Stand {
	
	private int number;
	private List<Fruit> fruits;
	
	public Stand(Integer number, List<Fruit> fruits) {
		this.number = number;
		this.fruits = fruits;
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<Fruit> getFruits() {
		return fruits;
	}

	public void setFruits(List<Fruit> fruits) {
		this.fruits = fruits;
	}

	public Fruit getFruitByLabel(String label) {
		for(Fruit fruit : fruits) {
			if(fruit.getLabel().equals(label)) 
				return fruit;
		}
		return null;
		
	}
	
	public boolean checkIfStandContainstFruit(String label) {
		for(Fruit fruit : this.fruits) {
			if(fruit.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}

}
