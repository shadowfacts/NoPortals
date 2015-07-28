package net.shadowfacts.noportals;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

/**
 * @author shadowfacts
 */
public class NoPortalsModContainer extends DummyModContainer {

	public NoPortalsModContainer() {
		super(new ModMetadata());

		ModMetadata data = getMetadata();
		data.modId = "NoPortals";
		data.name = "NoPortals";
		data.description = "Prevents portals to the overworld from being created when the dragon dies.";
		data.version = "1.7.10-1.0.0";
		data.authorList = Arrays.asList("Shadowfacts");
	}
}
