FactionEditor - line 93
## [Info] Aqua
| Info message                                                                | description                                                                    | preformed action      |
|:----------------------------------------------------------------------------|:-------------------------------------------------------------------------------|-----------------------|
| Changed chat to (Chat type)                                                 | Player chat has been changed to public or faction                              | /chat                 |
| New faction (factionName) created                                           | Player has created a new faction with the name (faction name)                  | /faction create       |                 
| You have kicked (playerName) out of your faction                            | Faction owner has kicked member with the name (playerName) from there faction  | /faction kick         |
| You have been kicked from your faction                                      | Player has been kicked from there faction                                      | /faction kick         |
| You have left your faction                                                  | Player has left there faction                                                  | /faction leave        |
| You have deleted your faction                                               | Faction owner has deleted there faction                                        | /faction delete       |
| You have been kicked from your faction because the faction has been deleted | Player has been kicked from there faction because the faction has been deleted | /faction delete       |                
| You have changed your faction name to (factionName)                         | Faction owner has changes there faction name to (factionName)                  | /faction modify name  |
| You have changed your faction color to (factionColor)                       | Faction owner has changes there faction color to (factionColor)                | /faction modify color |
| You have promoted (memberName) to owner                                     | Faction owner has made a member owner of the faction                           | /faction modify owner |
| The owner of (factionName) is (ownerName)                                   | n/a                                                                            | /faction modify owner |

## [Waring] Yellow 
| Waring message                                               | description                                            | preformed action                        |
|:-------------------------------------------------------------|:-------------------------------------------------------|-----------------------------------------|
| Invalid command!                                             | Wrong usage of the command /faction or /faction modify | /faction or /faction modify             |
| Command usage: /chat [public or faction]                     | Wrong usage of the command /chat                       | /chat                                   |
| Command usage /faction create [Faction name]                 | Wrong usage of the command /faction create             | /faction create                         |
| Command usage /faction kick [Player name]                    | Wrong usage of the command /faction kick               | /faction kick                           |
| Command usage /faction invite [Player name]                  | Wrong usage of the command /faction invite             | /faction invite                         |
| Command usage /faction join [Faction name]                   | Wrong usage of the command /faction join               | /faction join                           |
| Command usage /faction modify name [New faction name]        | Wrong usage of the command /faction modify name        | /faction modify name                    |
| Command usage /faction modify name [Color name]              | Wrong usage of the command /faction modify color       | /faction modify color                   |
| Command usage /faction modify owner [Member name]            | Wrong usage of the command /faction modify owner       | /faction modify owner                   |
| Command usage /faction members [Faction name]                | Wrong usage of the command /faction members            | /faction members                        |
| Command usage /faction owner [Faction name]                  | Wrong usage of the command /faction owner              | /faction owner                          |
| There is no faction with the name [Faction name]             | No faction has been found with the name (factionName)  | /faction members or /faction owner      |

## [Error] Red
| Error message                                                | description                                                                                 |
|:-------------------------------------------------------------|:--------------------------------------------------------------------------------------------|
| Only players can preform this command!                       | Non player / console trying to preform a command                                            | 
| You are already in a faction!                                | Player already has a faction                                                                |
| You must be in a faction to preform this action!             | Player trying to preform a command for a faction (by example /faction chat)                 |
| You can not preform this action as owner of the faction!     | Faction owner trying to preform a action for an faction member (by example /faction leave)  |
| You must be the owner of the faction to preform this action! | Faction member trying to preform a action for an faction owner (by example /faction modify) |
| This player is not a part of your faction!                   | Faction action being preformed on non member player (by example /faction modify owner)      |
| You can not kick the owner from the faction!                 | Faction owner trying to kick himself from the faction                                       |
| Your faction data has not been found!                        | Failed te retrieve the faction data of player                                               |
| The name (factionName) is already in use by another faction! | Faction name is already in use by another faction                                           | *
| (colorName) is not a valid color!                            | The supplied colorName is not a known color                                                 |
| (chatType) Is not an valid chat type!                        | The supplied chat is not a valid chat type                                                  |

### [Database Errors] //will do this one some other time 
- Failed to connect to database: "Failed to connect to the dataBase: (errorMessage)"
- Failed to create/load factions table: "Failed to create/load factions table in database: (errorMessage)"
- Failed to add faction to the factions table: "Failed to add faction to the factions table: (errorMessage)" 
- Failed te retrieve objects where name equals (factionName) in factions table: "Failed te retrieve objects where name equals (factionName) in factions table: (errorMessage)"
- Failed to update faction name in factions table: "Failed to update faction name in factions table: (errorMessage)"
- Failed to update faction color in factions table: "Failed to update faction color in factions table: (errorMessage)"
- Failed to update faction owner in factions table: "Failed to update faction owner in factions table: (errorMessage)"
- Failed to update members of faction in factions table: "Failed to update members of faction in factions table: (errorMessage)"
- Failed to get members of faction out factions table: "Failed to get members of faction out factions table: (errorMessage)"
- Failed to get faction data out factions table with faction id: "Failed to get faction data out factions table with faction id: (errorMessage)"
- Failed to get faction data out factions table with faction name: "Failed to get faction data out factions table with faction name: (errorMessage)"
- Failed to get factions out of faction table: "Failed to get factions out of faction table: (errorMessage)"
- Failed to delete faction out of faction table: "Failed to delete faction out of faction table: (errorMessage)"
- Failed to create/load players table in database: "Failed to create/load players table in database: (errorMessage)"
- Failed to add player to players table: "Failed to add player to players table: (errorMessage)"
- Failed to get player out of players table with player name: "Failed to get player out of players table with player name: (errorMessage)"
- Failed to get factionId out of players table: "Failed to get factionId out of players table: (errorMessage)"
- Failed to update factionId in players table with playerUUID: "Failed to update factionId in players table with playerUUID: (errorMessage)"
- Failed to update factionId in players table with playerName: "Failed to update factionId in players table with playerName: (errorMessage)"
- Failed check if players has a faction in players table: "Failed check if players has a faction in players table: (errorMessage)"
- Failed to get playerUUID out of players table: "Failed to get playerUUID out of players table: (errorMessage)"
- Failed to get player name out of players table: "Failed to get player name out of players table: (errorMessage)"
- Failed to get player chat out of players table: "Failed to get player chat out of players table: (errorMessage)"
- Failed to set player chat in players table: "Failed to set player chat in players table: (errorMessage)"
- Failed to get player names where factionId equals 0: "Failed to get player names where factionId equals 0: (errorMessage)"
- Chat type returned from database was not public or faction: "Could not retrieve chat type!"