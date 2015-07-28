package net.shadowfacts.noportals;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @author shadowfacts
 */
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions("net.shadowfacts.noportals")
public class NoPortalsPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"net.shadowfacts.noportals.NoPortalsClassTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return "net.shadowfacts.noportals.NoPortalsModContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
