package com.github.namiuni.insectcollecting

import com.okkero.skedule.schedule
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class Listener(val plugin: InsectCollecting): Listener {
    @EventHandler
    fun onMoveEvent(event: PlayerMoveEvent) {
        val player = event.player
        val nearbyGrassList = player.getLineOfSight(null,24).filter { it.type == Material.GRASS }
        for (grass in nearbyGrassList) {
            val nearbyArmorStand = grass.location.getNearbyEntities(16.0,16.0,16.0).filter { it.type == EntityType.ARMOR_STAND || it.type == EntityType.PLAYER}
            if (nearbyArmorStand.isNotEmpty()) return
            if (Random.nextInt(10) == 1) {
                val armorStand = grass.world.spawnEntity(grass.location,EntityType.ARMOR_STAND) as ArmorStand
                armorStand.isVisible = false
                armorStand.isSilent = true
                armorStand.isInvulnerable = true
                armorStand.setGravity(false)
                armorStand.setItem(EquipmentSlot.HAND, ItemStack(Material.LEVER))


                val scheduler = Bukkit.getScheduler()
                scheduler.schedule(plugin) {
                    repeating(60)
                    while (armorStand.getItem(EquipmentSlot.HAND) == ItemStack(Material.LEVER)) {
                        grass.world.spawnParticle(Particle.BARRIER, grass.location, 10)
                        yield()
                    }
                }
            }
        }
    }
}