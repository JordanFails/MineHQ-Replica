package com.frozenorb.redstone.threads;

import com.frozenorb.commonlibs.utils.TPSUtility;
import com.frozenorb.redstone.RedstonePlugin;
import com.frozenorb.redstone.RedstonePluginSettings;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class PayloadThread extends Thread {

    public PayloadThread(){

    }

    @Override
    public void run() {
        try(Jedis jedis = RedstonePlugin.getRedisHelper().getPool().getResource()){
            /* Get the data from the current server and display it in a hash map */
            Map<String, String> data = new HashMap<>();
            data.put("onlinePlayers", Bukkit.getOnlinePlayers().size() + "");
            data.put("state");
            data.put("group")
            data.put("tps", TPSUtility.getRecentTps()[0] + "");

            /* Put that data into the database */
            jedis.hmset("Redstone-Server:" + RedstonePluginSettings.SERVER_NAME, data);
            RedstonePlugin.getRedisHelper().getPool().returnResource(jedis);
            jedis.close();
        }
    }
}
