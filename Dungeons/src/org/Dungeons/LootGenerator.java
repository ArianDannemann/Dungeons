package org.Dungeons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.inventory.ItemStack;

public class LootGenerator {
	
	private Selector selector = new Selector();
	
	public ItemStack[] generateChestContent(int chestSize, Map<Loot, Double> lootTable) {
		List<ItemStack> chestContent = new ArrayList<>();
		Random rdm = new Random();
		
		for (int i = 0; i < chestSize; i++) {
			
			// Select random loot from loot table
			Loot loot = (Loot) this.selector.selectRandomObjectFromWeightedList(lootTable);
			
			// Generate item stack according to loot class
			ItemStack item = new ItemStack(loot.item);
			item.setAmount(rdm.nextInt(loot.maximumAmount - loot.minimumAmount + 1) + loot.minimumAmount);
			chestContent.add(item);
		}
		
		ItemStack[] chestContentArray = new ItemStack[chestContent.size()];
		chestContentArray = chestContent.toArray(chestContentArray);
		
		return chestContentArray;
	}
}
