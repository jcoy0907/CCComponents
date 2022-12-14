package com.jcoy0907.cccomponents.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions(value = {"com.jcoy0907.cccomponents.asm", "java"})
@IFMLLoadingPlugin.SortingIndex(value = Integer.MAX_VALUE)
public class PeripheralsPlusPlusTransformerPlugin implements IFMLLoadingPlugin {
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"com.jcoy0907.cccomponents.asm.Transformer"};
	}
	
	@Override
	public String getModContainerClass() {
		return "com.jcoy0907.cccomponents.asm.DummyContainer";
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
		return "com.jcoy0907.cccomponents.asm.PeripheralsPlusPlusAccessTransformer";
	}
}
