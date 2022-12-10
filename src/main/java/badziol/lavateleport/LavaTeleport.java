package badziol.lavateleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public final class LavaTeleport extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        System.out.println("Event test 1 : Lava Teleport 1.01");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        System.out.println("Wylaczam event test 1");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage("Czesc "+event.getPlayer().getName()+" !!");
    }

    @EventHandler
    public void onLavaHit(EntityDamageEvent event){
        LivingEntity byt = (LivingEntity) event.getEntity();
        boolean przezLawe = Optional.ofNullable(byt.getLastDamageCause())
                .map(EntityDamageEvent::getCause)
                .filter(damageCause -> damageCause.equals(EntityDamageEvent.DamageCause.LAVA))
                .isPresent();
        if (przezLawe){
            //System.out.println(byt.getName()+" kompie sie w lawie."); //cokolwiek zywego
            if (byt instanceof Player){
                Player p = (Player) byt;
                p.sendMessage("Wylaz z lawy");
                System.out.println(p.getName()+" rozgrzewa kosci ->" +
                        " X: " + Math.round(p.getLocation().getX()) +
                        " Z: " + Math.round(p.getLocation().getZ())
                );
                Location tepnijDo = new Location(Bukkit.getWorld("world"),-37,108,29);

                //lava p1 : x =-50  , z = 53
                //lava p2 : x = -52 , z = 38
                //lava p3 : x = -66, z = 38
                //lava p4 : x =  -66, z = 53
                //narozniki prostakata, tak beda sprawdzane
                //do przemyslenia : klasyczne sortowanie punktow ??? do wyznaczania stref ??
                //wspolzedne na pale
                double p1x = -50;
                double p1z = 53;
                double p2x = -50;
                double p2z = 38;
                double p3x = -66;
                double p3z = 38;
                double p4x = -66;
                double p4z = 53;
                //nasz gracz
                double px = p.getLocation().getX();
                double pz = p.getLocation().getZ();

                if (px >= p3x && px <=p1x ){ //tu pseudo posortowane -66..x..-50
                    if (pz >=p3z && pz <=p4z){ //tu pseudo posortowane 38..z..53
                        System.out.println("Wlasciwa lawa z ktorej tepam.");
                        p.setFireTicks(0); //wyczysc efekt palenia sie.
                        p.teleport(tepnijDo);
                        p.sendMessage("Po teleporcie.");
                    }
                }
            }
        }

    }
}
