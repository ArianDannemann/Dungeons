package org.Dungeons.PointsOfInterest;

import java.util.Map;

import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.Dungeons.Selector;
import org.bukkit.Location;
import org.bukkit.Material;

import com.PluginBase.Chat;
import com.PluginBase.LocationHelper;

public class Collapse extends PointOfInterest {

	private final int range = 15, amount = 30;

	private Selector selector = new Selector();

	public void generatePointOfInterest(@SuppressWarnings("unused") Main main, Location pointOfInterestLocation,
			Map<Material, Double> structureMaterials) {
		for (int i = 0; i < this.amount; i++) {
			Location rockLocation = LocationHelper.getInstance().getRandomNearbyPosition(pointOfInterestLocation,
					this.range);
			rockLocation.getBlock()
					.setType((Material) this.selector.selectRandomObjectFromWeightedList(structureMaterials));
		}
	}

	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		Chat.getInstance().sendWarningMessageToConsole(main,
				"Tried to generate 'collapse' POI without structure material table");
	}
}
