package me.entropire.simple_factions.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class PlayerDatabase
{
    private DataBaseContext dataBaseContext;

    public PlayerDatabase(DataBaseContext dataBaseContext)
    {
        this.dataBaseContext = dataBaseContext;

        try (Statement statement = dataBaseContext.con.createStatement())
        {
            statement.execute("""
            CREATE TABLE IF NOT EXISTS Players (
                uuid TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                factionId INTEGER NOT NULL DEFAULT 0,
                chat TEXT NOT NULL DEFAULT public
            )
            """);
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to create/load players table in database: " + e.getMessage());
        }
    }

    public void addPlayer(Player player)
    {
        try (PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("INSERT INTO Players (uuid, name) VALUES (?, ?)"))
        {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, player.getName());
            preparedStatement.execute();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to add player to players table: " + e.getMessage());
        }
    }

    public boolean playerExists(String playerName)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT * FROM Players WHERE name = ?"))
        {
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get player out of players table with player name: " + e.getMessage());
        }
        return false;
    }

    public int getFactionId(Player player)
    {
        int factionId = -1;
        try (PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT factionId FROM Players WHERE uuid = ?"))
        {
            preparedStatement.setString(1, player.getUniqueId().toString());
            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    factionId = resultSet.getInt("factionId");
                }
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get factionId out of players table: " + e.getMessage());
        }
        return factionId;
    }

    public void updateFactionWithPlayerUUID(UUID uuid, int factionId)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("UPDATE Players SET factionId = ? WHERE uuid = ?"))
        {
            preparedStatement.setString(1, String.valueOf(factionId));
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to update factionId in players table with playerUUID" + e.getMessage());
        }
    }

    public void updateFactionWithPlayerName(String name, int factionId)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("UPDATE Players SET factionId = ? WHERE name = ?"))
        {
            preparedStatement.setString(1, String.valueOf(factionId));
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to update factionId in players table with playerName: " + e.getMessage());
        }
    }

    public boolean hasFaction(Player player)
    {
        boolean hasFaction = false;
        try (PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT factionId FROM Players WHERE uuid = ?"))
        {
            preparedStatement.setString(1, player.getUniqueId().toString());
            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    int factionId = resultSet.getInt("factionId");
                    if (factionId > 0)
                    {
                        hasFaction = true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed check if players has a faction in players table: " + e.getMessage());
        }
        return hasFaction;
    }

    public UUID getPlayerUUID(String playerName)
    {
        try (PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT uuid FROM Players WHERE name = ?"))
        {
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return UUID.fromString(resultSet.getString("uuid"));
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get playerUUID out of players table: " + e.getMessage());
        }
        return null;
    }

    public String getPlayerName(String uuid)
    {
        try (PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT name FROM Players WHERE uuid = ?"))
        {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getString("name");
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get player name out of players table: " + e.getMessage());
        }
        return null;
    }

    public String getChat(UUID uuid)
    {
        try (PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT chat FROM Players WHERE uuid = ?"))
        {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getString("chat");
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get player chat out of players table: " + e.getMessage());
        }
        return null;
    }

    public void setChat(UUID uuid, String chat)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("UPDATE Players SET chat = ? WHERE uuid = ?"))
        {
            preparedStatement.setString(1, chat);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to set player chat in players table: " + e.getMessage());
        }
    }
}

