package com.abo9kr.jinxclient.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/** Lets "JinxClient" be opened from the vanilla Mods screen via Mod Menu, same as pressing J. */
public class JinxModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new JinxScreen();
    }
}
