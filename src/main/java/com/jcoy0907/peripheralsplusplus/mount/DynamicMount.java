package com.jcoy0907.peripheralsplusplus.mount;

import com.austinv11.collectiveframework.multithreading.SimpleRunnable;
import com.austinv11.collectiveframework.utils.WebUtils;
import com.jcoy0907.peripheralsplusplus.PeripheralsPlusPlus;
import com.jcoy0907.peripheralsplusplus.tiles.TileEntityAnalyzer;
import com.jcoy0907.peripheralsplusplus.utils.*;
import com.google.gson.Gson;
import com.jcoy0907.peripheralsplusplus.tiles.TileEntityAnalyzer;
import com.jcoy0907.peripheralsplusplus.utils.JSONFileList;
import com.jcoy0907.peripheralsplusplus.utils.JSONIndex;
import com.jcoy0907.peripheralsplusplus.utils.Util;
import dan200.computercraft.api.filesystem.IMount;
import dan200.computercraft.api.peripheral.IPeripheral;

import java.io.*;
import java.util.List;

public class DynamicMount implements IMount {

	public static final String MOUNT_DIRECTORY = PeripheralsPlusPlus.BASE_PPP_DIR+"ppp_mount";
	public static final String DIRECTORY = "/ppp";
	public static final String JSON_VER = "1.1";
	private IPeripheral peripheral;

	public DynamicMount(IPeripheral peripheral) {
		this.peripheral = peripheral;
	}

	public static void prepareMount() {
		new SimpleRunnable() {
			
			@Override
			public void run() {
				try {
					Gson gson = new Gson();
					JSONIndex index = gson.fromJson(Util.listToString(WebUtils.readGithub("PeripheralsPlusPlus", "lua/index.json")), JSONIndex.class);
					if (!index.ver.equals(JSON_VER))
						PeripheralsPlusPlus.LOGGER.warn("JSON Version mismatch! Is your version of Peripherals++ outdated?");
					String[] dirs = index.dirs;
					PeripheralsPlusPlus.LOGGER.info(dirs.length+" directories found! Attempting to update (if necessary)...");
					for (String d : dirs) {
						JSONFileList files = gson.fromJson(Util.listToString(WebUtils.readGithub("PeripheralsPlusPlus", "lua/"+d+"/index.json")), JSONFileList.class);
						if (Util.checkFileVersion(MOUNT_DIRECTORY+"/"+d, files)) {
							String[] files1 = files.files;
							for (String f : files1) {
								File file = new File((MOUNT_DIRECTORY+"/"+d+"/"+f).replace(".lua", ""));
								file.mkdirs();
								file.delete();//FIXME:Too inefficient
								file.createNewFile();
								FileWriter writer = new FileWriter(file);
								writer.write(Util.listToString(WebUtils.readGithub("PeripheralsPlusPlus", "lua/"+d+"/"+f)));
								writer.close();
							}
						}
					}
					PeripheralsPlusPlus.LOGGER.info("Mount has been successfully prepared!");
				} catch (Exception e) {
					PeripheralsPlusPlus.LOGGER.error("An exception was thrown attempting to prepare mount programs; if your internet connection is fine, please report the following to the mod author:");
					e.printStackTrace();
				} finally {
					this.disable(true);
				}
			}
			
			@Override
			public String getName() {
				return "Dynamic Mount Update Thread";
			}
		}.start();
	}

	@Override
	public boolean exists(String path) throws IOException {
		return new File(MOUNT_DIRECTORY+"/"+path).exists();
	}

	@Override
	public boolean isDirectory(String path) throws IOException {
		return new File(MOUNT_DIRECTORY+"/"+path).isDirectory();
	}

	@Override
	public void list(String path, List<String> contents) throws IOException {
		File file = new File(MOUNT_DIRECTORY+"/"+path);
		for (File f : file.listFiles()) {
			String type = getSafeType();
			if (f.getName().equals(type) || file.getAbsolutePath().contains(type))
				if (!path.contains("index"))
					contents.add(f.getName());
		}
	}

	@Override
	public long getSize(String path) throws IOException {
		return new File(MOUNT_DIRECTORY+"/"+path).getTotalSpace();
	}

	@Override
	public InputStream openForRead(String path) throws IOException {
		return new FileInputStream(new File(MOUNT_DIRECTORY+"/"+path));
	}

	private String getSafeType() {
		return peripheral instanceof TileEntityAnalyzer ? "analyzers" : peripheral.getType(); //FIXME: Hardcoding is no bueno
	}
}
