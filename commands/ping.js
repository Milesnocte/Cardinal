const {SlashCommandBuilder} = require("discord.js");

module.exports = {
    data: new SlashCommandBuilder()
        .setName('ping')
        .setDescription('Replies with Pong!'),
    async execute(interaction) {
        var time = (Date.now() - interaction.createdAt)
        await interaction.reply('Pong in ' + time + 'ms')
    }
};