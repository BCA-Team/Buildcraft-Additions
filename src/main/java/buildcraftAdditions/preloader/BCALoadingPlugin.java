package buildcraftAdditions.preloader;

import codechicken.core.launch.DepLoader;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @author big_Xplosion
 */
public class BCALoadingPlugin implements IFMLLoadingPlugin {

	public BCALoadingPlugin() {
		DepLoader.load();
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return null;
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
