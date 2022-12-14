package com.jcoy0907.cccomponents.hooks;

public class InvalidUpgradeIDException extends Exception {
	
	public InvalidUpgradeIDException(IPocketComputerUpgrade computerUpgrade) {
		super("Upgrade "+computerUpgrade+" attempted to register with id "+computerUpgrade.getUpgradeID());
	}
}
