package com.abo9kr.jinxclient.module;

import com.abo9kr.jinxclient.module.impl.hud.*;
import com.abo9kr.jinxclient.module.impl.items.*;
import com.abo9kr.jinxclient.module.impl.misc.*;
import com.abo9kr.jinxclient.module.impl.player.*;
import com.abo9kr.jinxclient.module.impl.viewmodel.ViewModelPosition;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    private static final List<Module> MODULES = new ArrayList<>();
    private static final Map<Category, List<Module>> BY_CATEGORY = new EnumMap<>(Category.class);

    public static void init() {
        // Items
        register(new OldSwing());
        register(new Glint());
        register(new Drops());
        register(new SwingSpeed());
        register(new NoEatAnimation());

        // Player
        register(new NameTweak());
        register(new NickHider());
        register(new NoFire());
        register(new HitColor());
        register(new DamageTint());
        register(new Hitbox());
        register(new NoDeathAnimation());
        register(new NoElytra());

        // Hud
        register(new TabPing());
        register(new ChatToggle());
        register(new NoBlur());
        register(new ScoreboardHider());
        register(new Announcements());
        register(new ArmorHud());
        register(new Fps());
        register(new Cps());
        register(new Keystrokes());

        // Misc
        register(new TimeChanger());
        register(new Fullbright());
        register(new FogModel());
        register(new Saturation());
        register(new BlockOverlay());
        register(new VisualRatio());
        register(new AutoText());
        register(new KillEffect());
        register(new TotemDisplay());
        register(new NoPackWarn());

        // ViewModel
        register(new ViewModelPosition());
    }

    private static void register(Module module) {
        MODULES.add(module);
        BY_CATEGORY.computeIfAbsent(module.getCategory(), c -> new ArrayList<>()).add(module);
    }

    public static List<Module> getModules() {
        return MODULES;
    }

    public static List<Module> getModules(Category category) {
        return BY_CATEGORY.getOrDefault(category, List.of());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Module> T get(Class<T> clazz) {
        for (Module m : MODULES) {
            if (clazz.isInstance(m)) return (T) m;
        }
        throw new IllegalStateException("Module not registered: " + clazz.getSimpleName());
    }
}
