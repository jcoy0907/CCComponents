package com.jcoy0907.collectiveframework.minecraft.config;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.config.*;
import com.austinv11.collectiveframework.minecraft.config.ConfigLoadEvent.Init;
import com.austinv11.collectiveframework.minecraft.config.ConfigLoadEvent.Pre;
import com.austinv11.collectiveframework.utils.ArrayUtils;
import com.austinv11.collectiveframework.utils.ReflectionUtils;
import cpw.mods.fml.relauncher.Side;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.minecraftforge.common.MinecraftForge;
import scala.Console;

public class ConfigRegistry {
    private static List<ConfigRegistry.ConfigProxy> toBeInitialized = new ArrayList();
    public static List<ConfigRegistry.ConfigProxy> configs = new ArrayList();
    private static List<IConfigProxy> proxies = new ArrayList();

    public ConfigRegistry() {
    }

    public static void registerConfig(Object config) throws ConfigException {
        if (!config.getClass().isAnnotationPresent(Config.class)) {
            throw new ConfigException("Config " + config.toString() + " does not contain a Config annotation!");
        } else {
            Config configAnnotation = (Config)config.getClass().getAnnotation(Config.class);

            try {
                ConfigRegistry.ConfigProxy configProxy = new ConfigRegistry.ConfigProxy(configAnnotation, config);
                toBeInitialized.add(configProxy);
            } catch (Exception var3) {
                var3.printStackTrace();
                throw new ConfigException(var3.getMessage());
            }
        }
    }

    public static void registerConfigProxy(IConfigProxy proxy) {
        proxies.add(proxy);
    }

    public static void init() {
        Iterator i$ = toBeInitialized.iterator();

        while(i$.hasNext()) {
            ConfigRegistry.ConfigProxy config = (ConfigRegistry.ConfigProxy)i$.next();
            initialize(config);
        }

        toBeInitialized.clear();
    }

    private static void initialize(ConfigRegistry.ConfigProxy configProxy) {
        IConfigurationHandler handler = configProxy.handler;
        Init event = new Init();
        event.config = handler.convertToString(configProxy.config);
        event.configName = configProxy.fileName;
        event.isRevert = false;
        MinecraftForge.EVENT_BUS.post(event);
        handler.loadFile(configProxy.fileName, configProxy.config, configProxy.fields);
        configs.add(configProxy);
    }

    public static String getKey(Object o) {
        Iterator i$ = proxies.iterator();

        IConfigProxy proxy;
        do {
            if (!i$.hasNext()) {
                return "@NULL@";
            }

            proxy = (IConfigProxy)i$.next();
        } while(!proxy.canSerializeObject(o));

        return proxy.getKey(o);
    }

    public static String serialize(Object o) throws ConfigException {
        Iterator i$ = proxies.iterator();

        IConfigProxy proxy;
        do {
            if (!i$.hasNext()) {
                return "@NULL@";
            }

            proxy = (IConfigProxy)i$.next();
        } while(!proxy.canSerializeObject(o));

        return proxy.serialize(o);
    }

    public static Object deserialize(String key, String string) throws ConfigException {
        Iterator i$ = proxies.iterator();

        IConfigProxy proxy;
        do {
            if (!i$.hasNext()) {
                return null;
            }

            proxy = (IConfigProxy)i$.next();
        } while(!proxy.isKeyUsable(key));

        return proxy.deserialize(key, string);
    }

    public static void onConfigReload(Pre event) {
        if (!event.isCanceled()) {
            ConfigRegistry.ConfigProxy proxy;
            if (!event.isRevert) {
                CollectiveFramework.LOGGER.info("Reloading config '" + event.configName + "'");
                proxy = findConfigProxyForConfigFile(configs, event.configName);
                if (proxy == null) {
                    CollectiveFramework.LOGGER.error("There was an error reloading the config!");
                    return;
                }

                proxy.handler.loadFromString(event.config, proxy.config, proxy.fields);
            } else {
                CollectiveFramework.LOGGER.info("Reverting config '" + event.configName + "'");
                proxy = findConfigProxyForConfigFile(configs, event.configName);
                if (proxy == null) {
                    CollectiveFramework.LOGGER.error("There was an error reverting the config!");
                    return;
                }

                proxy.handler.loadFile(event.config, proxy.config, proxy.fields);
            }
        }

    }

    private static ConfigRegistry.ConfigProxy findConfigProxyForConfigFile(List<ConfigRegistry.ConfigProxy> proxies, String filename) {
        Iterator i$ = proxies.iterator();

        ConfigRegistry.ConfigProxy proxy;
        do {
            if (!i$.hasNext()) {
                return null;
            }

            proxy = (ConfigRegistry.ConfigProxy)i$.next();
        } while(!proxy.fileName.equals(filename));

        return proxy;
    }

    static {
        registerConfigProxy(new DefaultProxy());
    }

    public static class ConfigProxy implements Cloneable {
        public Object config;
        public IConfigurationHandler handler;
        public String fileName;
        public boolean doesSync;
        public TreeMap<String, Map<String, Field>> fields = new TreeMap();

        public ConfigProxy(Config annotation, Object config) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            this.config = config;
            this.handler = (IConfigurationHandler)Class.forName(annotation.handler()).newInstance();
            this.fileName = annotation.fileName().equals("@NULL@") ? config.getClass().getSimpleName() + ".cfg" : annotation.fileName();
            this.doesSync = annotation.doesSync();
            Field[] declared = config.getClass().getDeclaredFields();
            Field[] field = declared;
            int len1$ = declared.length;

            int len$;
            for(len$ = 0; len$ < len1$; ++len$) {
                Field f = field[len$];
                f.setAccessible(true);
                if (ArrayUtils.indexOf(annotation.exclude(), f.getName()) == -1) {
                    if (f.isAnnotationPresent(Description.class)) {
                        this.addToCategory(((Description)f.getAnnotation(Description.class)).category(), f);
                    } else {
                        this.addToCategory("General", f);
                    }
                }
            }

            field = config.getClass().getFields();
            Field[] arr$ = field;
            len$ = field.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field f = arr$[i$];
                f.setAccessible(true);
                if (ArrayUtils.indexOf(annotation.exclude(), f.getName()) == -1) {
                    if (f.isAnnotationPresent(Description.class)) {
                        this.addToCategory(((Description)f.getAnnotation(Description.class)).category(), f);
                    } else {
                        this.addToCategory("General", f);
                    }
                }
            }

        }

        private void addToCategory(String category, Field f) {
            Object vals;
            if (this.fields.containsKey(category)) {
                vals = (Map)this.fields.get(category);
            } else {
                vals = new TreeMap();
            }

            ((Map)vals).put(f.getName(), f);
            this.fields.put(category, (Map)vals);
        }
    }

    public static class DefaultConfigurationHandler implements IConfigurationHandler {
        private Map<String, Map<String, Field>> current = new HashMap();
        private File cachedFile;

        public DefaultConfigurationHandler() {
        }

        public void setValue(String configValue, String category, Object value, Object config) {
            this.setValue(configValue, category, value, config, true);
        }

        public void setValue(String configValue, String category, Object value, Object config, boolean saveToFile) {
            Map<String, Field> fields = this.current.containsKey(category) ? (Map)this.current.get(category) : new HashMap();
            Field field = ((Map)fields).containsKey(configValue) ? (Field)((Map)fields).get(configValue) : ReflectionUtils.getDeclaredOrNormalField(configValue, config.getClass());

            try {
                field.set(config, value);
            } catch (IllegalAccessException var10) {
                var10.printStackTrace();
            }

            if (saveToFile) {
                try {
                    this.writeFile(this.cachedFile, config);
                } catch (Exception var9) {
                    var9.printStackTrace();
                }
            }

            if (!this.current.containsKey(category) || !((Map)fields).containsKey(configValue)) {
                ((Map)fields).put(configValue, field);
                this.current.put(category, fields);
            }

        }

        public Object getValue(String configValue, String category, Object config) {
            return this.hasValue(configValue, category) && this.current.containsKey(category) && ((Map)this.current.get(category)).containsKey(configValue) ? ((Map)this.current.get(category)).get(configValue) : null;
        }

        public void loadFile(String fileName, Object config, Map<String, Map<String, Field>> hint) {
            this.current = hint;
            this.cachedFile = new File("./config/" + fileName);
            if (this.cachedFile.exists()) {
                try {
                    this.readFile(this.cachedFile, config);
                } catch (Exception var6) {
                    var6.printStackTrace();
                }
            } else {
                try {
                    this.cachedFile.createNewFile();
                    this.writeFile(this.cachedFile, config);
                } catch (Exception var5) {
                    var5.printStackTrace();
                }
            }

        }

        public void loadFromString(String string, Object config, Map<String, Map<String, Field>> hint) {
            try {
                this.readFromReader(new BufferedReader(new StringReader(string)), config);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

        }

        public File getConfigFile(String fileName, Object config) {
            return this.cachedFile;
        }

        public boolean hasValue(String configValue, String category) {
            return this.current.containsKey(category) && ((Map)this.current.get(category)).containsKey(configValue);
        }

        public String convertToString(Object config) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            try {
                this.writeToStream(new PrintStream(stream), config);
                return stream.toString();
            } catch (Exception var4) {
                var4.printStackTrace();
                return null;
            }
        }

        private void writeToStream(PrintStream writer, Object config) throws IllegalAccessException, ConfigException {
            Iterator i$ = this.current.keySet().iterator();

            label42:
            while(i$.hasNext()) {
                String category = (String)i$.next();
                int i = 0;
                i$ = ((Map) this.current.get(category)).keySet().iterator();

                while(true) {
                    String field;
                    Field f;
                    do {
                        if (!i$.hasNext()) {
                            continue label42;
                        }

                        field = (String)i$.next();
                        f = (Field)((Map)this.current.get(category)).get(field);
                        f.setAccessible(true);
                    } while(f.isAnnotationPresent(Description.class) && ((Description)f.getAnnotation(Description.class)).clientSideOnly() && CollectiveFramework.proxy.getSide() == Side.SERVER);

                    if (i == 0) {
                        writer.println(category + " {");
                    }

                    String comment = f.isAnnotationPresent(Description.class) ? ((Description)f.getAnnotation(Description.class)).comment() : "None! Tell the mod author to include a comment!";
                    writer.println("\t" + comment);
                    writer.println("\t" + ConfigRegistry.getKey(f.get(config)) + ":" + field + "=" + ConfigRegistry.serialize(f.get(config)));
                    writer.println();
                    ++i;
                    if (i == ((Map)this.current.get(category)).size()) {
                        writer.println("}");
                    }
                }
            }

            writer.flush();
            writer.close();
        }

        private void writeFile(File file, Object config) throws IOException, IllegalAccessException, ConfigException {
            PrintStream writer = new PrintStream(file);
            this.writeToStream(writer, config);
        }

        private void readFile(File file, Object config) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            this.readFromReader(reader, config);

            try {
                this.writeFile(file, config);
            } catch (ConfigException var5) {
                var5.printStackTrace();
            }

        }

        private void readFromReader(BufferedReader reader, Object config) throws IOException, IllegalAccessException {
            boolean reachedBracket = false;
            int lineCount = 0;

            while(true) {
                String line;
                while((line = reader.readLine()) != null) {
                    if (!reachedBracket && line.contains("{")) {
                        reachedBracket = true;
                    } else if (reachedBracket && line.equals("}")) {
                        reachedBracket = false;
                    } else if (reachedBracket) {
                        if (lineCount < 1) {
                            ++lineCount;
                        } else if (lineCount == 1) {
                            CollectiveFramework.LOGGER.warn("Trying 1");
                            String field = line.substring(line.indexOf(":") + 1, line.indexOf("="));
                            CollectiveFramework.LOGGER.warn("Trying 2");
                            String key = line.substring(0, line.indexOf(":")).replace("\t", "");
                            CollectiveFramework.LOGGER.warn("Trying 3");
                            line = line.substring(line.indexOf("=") + 1);
                            Field f = ReflectionUtils.getDeclaredOrNormalField(field, config.getClass());
                            if (f != null) {
                                try {
                                    f.setAccessible(true);
                                    f.set(config, ConfigRegistry.deserialize(key, line));
                                } catch (ConfigException var10) {
                                    var10.printStackTrace();
                                }
                            }

                            ++lineCount;
                        } else {
                            lineCount = 0;
                        }
                    }
                }

                return;
            }
        }
    }
}
