package com.github.namiuni.insectcollecting

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class InsectCollecting : JavaPlugin() {
    val insectCollecting = this
    val listener = Listener(insectCollecting)
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(listener,this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}