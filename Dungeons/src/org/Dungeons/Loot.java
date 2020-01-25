package org.Dungeons;

import org.bukkit.inventory.ItemStack;

/**
 * This is the base class for all randomly generate loot
 * @author Arian Dannemann
 */
public class Loot {
	
	// The type of item that will be generated
	public ItemStack item;
	// The mininum and maxiumum amounts of this item
	public int minimumAmount, maximumAmount;
	
	public Loot(ItemStack item) {
		this.item = item;
		this.minimumAmount = 1;
		this.maximumAmount = 1;
	}
	
	public Loot(ItemStack item, int maximumAmount) {
		this.item = item;
		this.minimumAmount = 1;
		this.maximumAmount = maximumAmount;
	}
	
	public Loot(ItemStack item, int minimumAmount, int maximumAmount) {
		this.item = item;
		this.minimumAmount = minimumAmount;
		this.maximumAmount = maximumAmount;
	}
}
