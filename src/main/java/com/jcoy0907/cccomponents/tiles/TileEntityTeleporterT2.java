package com.jcoy0907.cccomponents.tiles;

public class TileEntityTeleporterT2 extends TileEntityTeleporter {

	public TileEntityTeleporterT2() {
		super();
		publicName = "teleporterT2";
	}

	@Override
	public String getName() {
		return "tileEntityTeleporterT2";
	}

	@Override
	public int getMaxLinks() {
		return 8;
	}
}
