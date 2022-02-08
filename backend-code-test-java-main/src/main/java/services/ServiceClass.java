package services;

import java.util.ArrayList;
import java.util.List;

import model.Fruit;
import model.Stand;

public class ServiceClass {
	
	private static final Double MIN_VALUE = 100.0;
	private static final String  FRUIT_CHERRIES_LABEL = "cherries";
	private static final String  FRUIT_PEACHES_LABEL = "peaches";
	private static final String  FRUIT_PEARS_LABEL = "pears";

	private List<Stand> stands = new ArrayList<Stand>();
	
	private void initStands() {
		
		List<Fruit> fruitsAll = new ArrayList<Fruit>();
		fruitsAll.add(new Fruit(FRUIT_CHERRIES_LABEL, 9.30));
		fruitsAll.add(new Fruit(FRUIT_PEACHES_LABEL, 6.5));
		fruitsAll.add(new Fruit(FRUIT_PEARS_LABEL, 5.30));
		
		stands.add(new Stand(1, fruitsAll));
		stands.add(new Stand(2, fruitsAll));
		stands.add(new Stand(3, fruitsAll));
	}
	
	 /**
     * Return the number of the first fruit stand that has the lowest possible total price for a basket of cherries and a basket of peaches.
     * @param stands
     * @return
     */
	public String getStandWithMinTotalPrice() {
		
		double totalPriceMin = MIN_VALUE;
		double totalPrice = 0;
		Stand standMin = null;
		
		this.initStands();
		
		for(Stand stand : stands) {
			if(stand != null) {
				Fruit standCherriesPrice = stand.getFruitByLabel(FRUIT_CHERRIES_LABEL);
				Fruit standPeachesPrice = stand.getFruitByLabel(FRUIT_PEACHES_LABEL);
				
				if(standCherriesPrice != null && standPeachesPrice != null) {
					totalPrice = standCherriesPrice.getPrice() + standPeachesPrice.getPrice();
					
					if(totalPrice < totalPriceMin) {
						totalPriceMin = totalPrice;
						standMin = stand;
					}
				}
			}
		}
		return "Sand with min total price of cherries and peaches is number: " + 
		standMin.getNumber() + " Total Price: " + totalPriceMin;
	}
	
	/**
	 * Scenario 1 - modification:
	 * Return first stand with pears and (cherries or peaches)
	 * @param stands
	 * @return
	 */
	public String getFirstStandWithPearsAndCherriesOrPeaches() {
		
		this.initStands();
		
		for(Stand stand : stands) {
			if(stand != null) {
				
				Fruit standCherriesPrice = stand.getFruitByLabel(FRUIT_CHERRIES_LABEL);
				Fruit standPeachesPrice = stand.getFruitByLabel(FRUIT_PEACHES_LABEL);
				Fruit standPearsPrice = stand.getFruitByLabel(FRUIT_PEARS_LABEL);
				
				if(standPearsPrice != null  && (standCherriesPrice != null || standPeachesPrice != null)) {
					
					double priceTotal = standPearsPrice.getPrice();
					if(standCherriesPrice != null) 
						priceTotal += standCherriesPrice.getPrice();
					else 
						priceTotal += standPeachesPrice.getPrice();
					
					return "First stand with Pears and (Cherries or peaches) is number " + stand.getNumber() + " Total Price: " + priceTotal;
				}
			}
		}
		return null;
	}
	
	/**
	 * Scenario 3 - modification:
	 * Return stands, price, what fruits were purchased and by how many stands the selection was made.
	 * @return
	 */
	public String getStandsFruitPricePurchased() {
		
		this.initStands();
		
		List<String> selectedStands = new ArrayList<String>();
		boolean isBuyCherries = false;
		boolean isBuyPeaches = false;
		boolean isBuyPears = false;
		
		boolean standHasCherries;
		boolean standHasPeaches;
		boolean standHasPears;
		
		int countSelectStand = 0;
		
		String msg = "";
		
		for(Stand stand : stands) {
					
			if(stand != null) {

				standHasCherries = stand.checkIfStandContainstFruit(FRUIT_CHERRIES_LABEL);
				standHasPeaches = stand.checkIfStandContainstFruit(FRUIT_PEACHES_LABEL);
				standHasPears = stand.checkIfStandContainstFruit(FRUIT_PEARS_LABEL);
				
				if(standHasCherries && !isBuyCherries) {
					msg = FRUIT_CHERRIES_LABEL + " is buy to stand " + stand.getNumber() + 
							". Price: " +  stand.getFruitByLabel(FRUIT_CHERRIES_LABEL).getPrice() + " \n";
					selectedStands.add(msg);
					isBuyCherries = true;
					countSelectStand++;
				} 
				
				if(standHasPeaches && !isBuyPeaches) {
					msg = FRUIT_PEACHES_LABEL + " is buy to stand " + stand.getNumber() + 
							". Price: " +  stand.getFruitByLabel(FRUIT_PEACHES_LABEL).getPrice() + " \n";
					selectedStands.add(msg);
					isBuyPeaches = true;
					countSelectStand++;
				} 
				
				if(standHasPears && !isBuyPears) {
					msg = FRUIT_PEARS_LABEL + " is buy to stand " + stand.getNumber() + 
							". Price: " +  stand.getFruitByLabel(FRUIT_PEARS_LABEL).getPrice() + " \n";
					selectedStands.add(msg);
					isBuyPears = true;
					countSelectStand++;
				}
			}
		} 
		return msg + " Count stand selection is " + countSelectStand;			
	}

}
