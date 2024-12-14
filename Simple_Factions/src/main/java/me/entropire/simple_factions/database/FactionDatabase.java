package me.entropire.simple_factions.database;

import me.entropire.simple_factions.objects.Colors;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class FactionDatabase
{
    private final Colors colors = new Colors();
    private DataBaseContext dataBaseContext;

    public FactionDatabase(DataBaseContext dataBaseContext)
    {
        this.dataBaseContext = dataBaseContext;

        try (Statement statement = dataBaseContext.con.createStatement())
        {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS Factions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    color TEXT NOT NULL,
                    owner TEXT NOT NULL,
                    members TEXT NOT NULL
                )
                """);
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to create/load factions table in database: " + e.getMessage());
        }
    }

    public void addFaction(Faction faction)
    {
        try (PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("INSERT INTO Factions (name, color, owner, members) VALUES (?, ?, ?, ?)"))
        {
            preparedStatement.setString(1, faction.getName());
            preparedStatement.setString(2, colors.getColorNameWithChatColor(faction.getColor()));
            preparedStatement.setString(3, faction.getOwner().toString());
            preparedStatement.setString(4, String.join(",", faction.getMembers()));
            preparedStatement.execute();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to add faction to the factions table: " + e.getMessage());
        }
    }

    public boolean factionExistsByName(String factionName)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT * FROM Factions WHERE name = ?"))
        {
            preparedStatement.setString(1, factionName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to check if name was already in use in factions table: " + e.getMessage());
            return true;
        }
    }

    public void updateFactionName(int factionId, String newName)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("UPDATE Factions SET name = ? WHERE id = ?"))
        {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, String.valueOf(factionId));
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to update faction name in factions table: " + e.getMessage());
        }
    }

    public void updateFactionColor(int factionId, String newColor)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("UPDATE Factions SET color = ? WHERE id = ?"))
        {
            preparedStatement.setString(1, newColor);
            preparedStatement.setString(2, String.valueOf(factionId));
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to update faction color in factions table:" + e.getMessage());
        }
    }

    public void updateFactionOwner(int factionId, String newOwnerUUid)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("UPDATE Factions SET owner = ? WHERE id = ?"))
        {
            preparedStatement.setString(1, newOwnerUUid);
            preparedStatement.setString(2, String.valueOf(factionId));
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to update faction owner in factions table:" + e.getMessage());
        }
    }

    public void updateFactionMembers(int factionId, String member, Boolean add)
    {
        ArrayList<String> membersList;
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT members FROM Factions WHERE id = ?"))
        {
            preparedStatement.setString(1, String.valueOf(factionId));
            ResultSet resultSet = preparedStatement.executeQuery();
            membersList = new ArrayList<>(Arrays.asList(resultSet.getString("members").split(",")));

            if(add)
            {
                membersList.add(member);
            }
            else
            {
                membersList.remove(member);
            }

            try(PreparedStatement preparedStatement2 = dataBaseContext.con.prepareStatement("UPDATE Factions SET members = ? WHERE id = ?"))
            {
                preparedStatement2.setString(1, String.join(",", membersList));
                preparedStatement2.setString(2, String.valueOf(factionId));
                preparedStatement2.executeUpdate();
            }
            catch (Exception e)
            {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to update members of faction in factions table: " + e.getMessage());
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get members of faction out factions table: " + e.getMessage());
        }
    }

    public Faction getFactionDataById(int factionId)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT * FROM Factions WHERE id = ?"))
        {
            preparedStatement.setString(1, String.valueOf(factionId));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String color = resultSet.getString("color");
                String owner = resultSet.getString("owner");

                String membersString = resultSet.getString("members");
                ArrayList<String> membersList = new ArrayList<>(Arrays.asList(membersString.split(",")));

                return new Faction(id, name, colors.getChatColorWithColorName(color), UUID.fromString(owner), membersList);
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get faction data of faction out factions table with faction id: " + e.getMessage());
        }
        return null;
    }

    public Faction getFactionDataByName(String factionName)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT * FROM Factions WHERE name = ?"))
        {
            preparedStatement.setString(1, factionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String color = resultSet.getString("color");
                String owner = resultSet.getString("owner");

                String membersString = resultSet.getString("members");
                ArrayList<String> membersList = new ArrayList<>(Arrays.asList(membersString.split(",")));

                return new Faction(id, name, colors.getChatColorWithColorName(color), UUID.fromString(owner), membersList);
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get faction data of faction out factions table with faction name: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<String> getFactions()
    {
        ArrayList<String> factionNames = new ArrayList<>();
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("SELECT * FROM Factions"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                String name = resultSet.getString("name");
                factionNames.add(name);
            }
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to get factions out of faction table: " + e.getMessage());
        }
        return factionNames;
    }

    public void deleteFaction(int factionId)
    {
        try(PreparedStatement preparedStatement = dataBaseContext.con.prepareStatement("DELETE FROM Factions WHERE id = ?"))
        {
            preparedStatement.setString(1, String.valueOf(factionId));
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to delete faction out of faction table: " + e.getMessage());
        }
    }
}