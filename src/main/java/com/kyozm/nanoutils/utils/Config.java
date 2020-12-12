package com.kyozm.nanoutils.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.settings.NestedSetting;
import com.kyozm.nanoutils.settings.Setting;
import net.minecraftforge.fml.common.Loader;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class Config {
    private static final File configDir = new File(Loader.instance().getConfigDir(), File.separator + "NanoUtils");
    private static final File configFile = new File(configDir.getAbsolutePath() + File.separator +  "config.json");

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

        config.add("settings", settings);

        try {
            if (!configDir.exists()) configDir.mkdirs();
            BufferedWriter out = new BufferedWriter(new FileWriter(configFile));
            out.write(config.toString());
            out.close();
        } catch (Exception e) {
            // lol eat shit
        }

    }

    @NotNull
    private static JsonObject proccessModuleSettings(List<Setting> settings, JsonObject settings_json) {
        for (Setting setting : settings) {
            if (setting instanceof NestedSetting)
                proccessModuleSettings(((NestedSetting) setting).internal, settings_json);
            settings_json.add(setting.configName, new Gson().toJsonTree(setting.getVal()));
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
                        ModuleManager.setSettingByConfigName(set.getKey(), (Object) new Gson().fromJson(set.getValue(), Object.class));
                    }
                }

            } catch (Exception e) {
                System.out.println("Couldn't load config");
                System.out.println("Couldn't load config");
                System.out.println("Couldn't load config");
                System.out.println("Couldn't load config");
            }
        }
    }
}
