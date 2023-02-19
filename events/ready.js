const { Events, ActivityType} = require('discord.js');
const {updateCommands} = require("../deploy-commands");

module.exports = {
    name: Events.ClientReady,
    once: true,
    execute(client) {
        console.log(`Ready! Logged in as ${client.user.tag}`);
        var nuberMembers = 0;
        for (let guild of client.guilds.cache.toJSON()) {
            nuberMembers += guild.memberCount
        }
        client.user.setActivity(`${nuberMembers} users`, { type: ActivityType.Watching });
    },
    updateCommands
};