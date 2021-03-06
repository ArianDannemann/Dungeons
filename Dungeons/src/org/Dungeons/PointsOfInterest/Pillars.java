package org.Dungeons.PointsOfInterest;

import java.util.Map;

import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.Dungeons.Selector;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import com.PluginBase.Chat;
import com.PluginBase.LocationHelper;

public class Pillars extends PointOfInterest {

	private final int range = 15, amount = 30;

	private Selector selector = new Selector();

	public void generatePointOfInterest(@SuppressWarnings("unused") Main main, Location pointOfInterestLocation,
			Map<Material, Double> structureMaterials) {
		for (int i = 0; i < this.amount; i++) {
			Location pillarLocation = LocationHelper.getInstance().getRandomNearbyPosition(pointOfInterestLocation,
					this.range);
			for (int y = 0; y < 4; y++) {
				LocationHelper.getInstance().offsetLocation(pillarLocation, new Vector(0, y, 0)).getBlock()
						.setType((Material) this.selector.selectRandomObjectFromWeightedList(structureMaterials));
			}
		}
	}

	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		Chat.getInstance().sendWarningMessageToConsole(main,
				"Tried to generate 'pillars' POI without structure material table");
	}
}
