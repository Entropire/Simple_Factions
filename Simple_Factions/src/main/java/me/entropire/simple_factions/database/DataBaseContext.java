package me.entropire.simple_factions.database;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseContext
{
    private String path;

    public DataBaseContext(String path)
    {
        this.path = path;
    }

    public Connection CreateConnection()
    {
        try
        {
            return  DriverManager.getConnection("jdbc:sqlite:" + path);
        }
        catch (SQLException e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to connect to the dataBase: " + e.getMessage());
            return null;
        }
    }
}