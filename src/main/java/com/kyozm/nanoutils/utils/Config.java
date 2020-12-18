package com.kyozm.nanoutils.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;
import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.settings.NestedSetting;
import com.kyozm.nanoutils.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.joor.Reflect;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private static final File configDir = new File(Loader.instance().getConfigDir(), File.separator + "NanoUtils");
    private static final File configFile = new File(configDir.getAbsolutePath() + File.separator +  "config.json");
    private static final File mcDir = Loader.instance().getConfigDir().getParentFile();
    private static final File modulesDir = new File(mcDir, File.separator + "NanoModules");

    public static void saveConfig() {
        JsonObject config = new JsonObject();

        JsonArray enableMods = new JsonArray();
        ModuleManager.registered.stream().filter(m -> m.isEnabled()).forEach(m -> enableMods.add(m.getClass().getName()));
        config.add("enabledMods", enableMods);

        JsonObject settings = new JsonObject();
        for (Class module : ModuleManager.settings.keySet()) {
            List<Setting> settingList = ModuleManager.settings.get(module);
            JsonObject settings_json = new JsonObject();
            proccessModuleSettings(settingList, settings_json);
            settings.add(module.getName(), settings_json);
        }

        JsonObject guiObjects = new JsonObject();
        for (Module module : ModuleManager.registered) {
            module.saveablePositions.entrySet().stream().forEach((widget) -> {
                if (widget.getValue() != null) {
                    Map<String, Integer> pos = new HashMap<>();
                    pos.put("x", widget.getValue().screenX);
                    pos.put("y", widget.getValue().screenY);
                    guiObjects.add(widget.getKey(), gs().toJsonTree(pos));
                }
            });
        }

        config.add("settings", settings);
        config.add("gui", guiObjects);

        try {
            if (!configDir.exists()) configDir.mkdirs();
            BufferedWriter out = new BufferedWriter(new FileWriter(configFile));
            out.write(config.toString());
            out.close();
        } catch (Exception e) {
            // lol eat shit
        }

    }

    private static JsonObject proccessModuleSettings(List<Setting> settings, JsonObject settings_json) {
        for (Setting setting : settings) {
            if (setting instanceof NestedSetting)
                proccessModuleSettings(((NestedSetting) setting).internal, settings_json);
            settings_json.add(setting.configName, gs().toJsonTree(setting.getVal()));
        }
        return settings_json;
    }

    public static void loadConfig() {
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                JsonElement root = new JsonParser().parse(reader);
                for (JsonElement mod : root.getAsJsonObject().get("enabledMods").getAsJsonArray()) {
                    ModuleManager.getModule(Class.forName(mod.getAsString())).ifPresent(Module::enable);
                }

                JsonObject settings = root.getAsJsonObject().get("settings").getAsJsonObject();
                for (Map.Entry<String, JsonElement> mod : settings.entrySet()) {
                    for (Map.Entry<String, JsonElement> set : mod.getValue().getAsJsonObject().entrySet()) {
                        ModuleManager.setSettingByConfigName(set.getKey(), (Object) gs().fromJson(set.getValue(), Object.class));
                    }
                }

                JsonObject gui = root.getAsJsonObject().get("gui").getAsJsonObject();
                for (Map.Entry<String, JsonElement> widget : gui.entrySet()) {
                    ModuleManager.setGuiPosition(widget.getKey(), (Map<String, Double>) gs().fromJson(widget.getValue(), HashMap.class));
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Couldn't load config: " + e.getMessage());
            }
        }
    }

    public static Gson gs() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NanoColor.class,  colorJsonDeserializer);
        gsonBuilder.registerTypeAdapter(NanoColor.class,  colorJsonSerializer);
        gsonBuilder.registerTypeAdapter(KeyBinding.class,  bindJsonDeserializer);
        gsonBuilder.registerTypeAdapter(KeyBinding.class,  bindJsonSerializer);
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }

    static JsonSerializer<NanoColor> colorJsonSerializer = (src, typeOfSrc, context) -> {
        JsonObject jsonNanoColor = new JsonObject();
        jsonNanoColor.addProperty("rgb", src.getStringWithAlpha());
        jsonNanoColor.addProperty("alpha", src.getAlpha());
        jsonNanoColor.addProperty("isChroma", src.isChroma);
        return jsonNanoColor;
    };

    static JsonDeserializer<NanoColor> colorJsonDeserializer = (json, typeOfSrc, context) -> {
        NanoColor c = NanoColor.fromColor(Color.decode(json.getAsJsonObject().get("rgb").getAsString()));
        NanoColor nc = new NanoColor(c.getRed(), c.getGreen(), c.getBlue()).withAlpha(json.getAsJsonObject().get("alpha").getAsInt());
        nc.isChroma = json.getAsJsonObject().get("isChroma").getAsBoolean();
        return nc;
    };

    static JsonSerializer<KeyBinding> bindJsonSerializer = (src, typeOfSrc, context) -> {
        JsonObject jsonKeyBind = new JsonObject();
        jsonKeyBind.addProperty("modifier", String.valueOf(src.getKeyModifier()));
        jsonKeyBind.addProperty("keycode", src.getKeyCode());
        jsonKeyBind.addProperty("name", src.getKeyDescription());
        return jsonKeyBind;
    };

    static JsonDeserializer<KeyBinding> bindJsonDeserializer = (json, typeOfSrc, context) -> {
        KeyBinding kb = new KeyBinding(json.getAsJsonObject().get("name").getAsString(), Keyboard.KEY_NONE, "NanoUtils");
        kb.setKeyModifierAndCode(KeyModifier.valueFromString(json.getAsJsonObject().get("modifier").getAsString()), json.getAsJsonObject().get("keycode").getAsInt());
        return kb;
    };

    public static List<Module> getRuntimeModules() {
        if (!modulesDir.exists()) { modulesDir.mkdir(); }
        List<Module> modules = new ArrayList<>();
        for (File mod : modulesDir.listFiles()) {
            try (FileReader reader = new FileReader(mod)) {
                Module m = Reflect.compile("modules." + FilenameUtils.removeExtension(mod.getName()), FileUtils.readFileToString(mod)).create().get();
                modules.add(m);
            } catch (Exception e) {
                System.out.println("Couldn't load module: " + mod.getName());
            }
        }
        return modules;
    }

}
