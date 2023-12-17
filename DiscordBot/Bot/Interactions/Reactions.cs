using Dapper;
using Discord;
using Discord.WebSocket;
using Models;
using Serilog;

namespace DiscordBot.Bot.Interactions;

public class Reactions
{
    public static async Task ReactionAdd(Cacheable<IUserMessage, ulong> Message, Cacheable<IMessageChannel, ulong> Channel, SocketReaction Reaction)
    {
        try
        {
            var context = new AppDBContext();
            bool debugFroot = false;

            string emote = Reaction.Emote.ToString();
            int starsToAdd = -2;
            
            switch (emote)
            {
                case "<:debugfroot:936344152004755456>":
                    starsToAdd = 0;
                    break;
                case "<:starfroot:991751462302519316>":
                    starsToAdd = 1;
                    break;
                case "<:antifroot:991751461144887337>":
                    starsToAdd = -1;
                    break;
            }
            
            if (starsToAdd == -2) return;

            IUserMessage message = await Message.GetOrDownloadAsync();
            string user = message.Author.Mention;
            string messageId = message.Id.ToString();
            string guildId = message.GetJumpUrl().Split("/")[4];
            DateTime date = DateTime.Now.ToUniversalTime();
            
            await context.Connection().ExecuteAsync("SELECT insert_or_update_stars(@Guild, @Message, @Member, @Stars, @Date)", 
                new {Guild = guildId, Message = messageId, Member = user, Stars = starsToAdd, Date = new DateTime().ToUniversalTime()});
            int currentStars = await context.Connection().QuerySingleAsync<int>("SELECT get_message_stars(@Message)", new { Message = messageId });
            
            if(await context.Connection().QuerySingleAsync<int>("SELECT get_starboard_posted(@Message)", new { Message = messageId }) == 1 && emote != "<:debugfroot:936344152004755456>") return;
            
            if (currentStars >= 5 || emote == "<:debugfroot:936344152004755456>")
            {
                await context.Connection().ExecuteAsync("SELECT set_starboard_posted(@Message)", new {Message = messageId});
                var channel = Program.GetClient().GetGuild(ulong.Parse(guildId)).TextChannels.FirstOrDefault(x => x.Name == "star-board");
                
                if (channel == null) return;
                
                List<EmbedFieldBuilder> fields = new List<EmbedFieldBuilder>();

                if (message.ReferencedMessage != null)
                {
                    fields.Add(new EmbedFieldBuilder()
                    {
                        Name = "Reply to",
                        Value = message.ReferencedMessage.Content,
                        IsInline = false
                    });
                }

                fields.Add(new EmbedFieldBuilder()
                {
                    Name = "Message",
                    Value = message.Content,
                    IsInline = false
                });

                EmbedAuthorBuilder authorBuilder = new EmbedAuthorBuilder();
                authorBuilder.Name = message.Author.GlobalName;
                authorBuilder.IconUrl = message.Author.GetAvatarUrl();

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.Author = authorBuilder;
                embedBuilder.Fields = fields;
                if (message.Attachments.Count > 0)
                {
                    embedBuilder.ImageUrl = message.Attachments.First().Url;
                }

                if (message.Attachments.Count > 1)
                {
                    EmbedFooterBuilder footerBuilder = new EmbedFooterBuilder();
                    footerBuilder.Text = "More attachments in original message, use the jump to link to see them";
                    embedBuilder.Footer = footerBuilder;

                }

                ButtonBuilder buttonBuilder = new ButtonBuilder();
                buttonBuilder.Url = message.GetJumpUrl();
                buttonBuilder.Label = "Jump to Message";
                buttonBuilder.Style = ButtonStyle.Link;

                ComponentBuilder messageComponent = new ComponentBuilder();
                messageComponent.WithButton(buttonBuilder);
                channel.SendMessageAsync(embed: embedBuilder.Build(), components: messageComponent.Build());
            }
        }
        catch (Exception e)
        {
            Log.Logger.Error($"[Messages] {e}");
        }
    }
    
    public static async Task ReactionRemove(Cacheable<IUserMessage, ulong> Message, Cacheable<IMessageChannel, ulong> Channel, SocketReaction Reaction)
    {
        try
        {
            var context = new AppDBContext();

            string emote = Reaction.Emote.ToString();

            int starsToAdd = -2;
            
            switch (emote)
            {
                case "<:debugfroot:936344152004755456>":
                    starsToAdd = 0;
                    break;
                case "<:starfroot:991751462302519316>":
                    starsToAdd = -1;
                    break;
                case "<:antifroot:991751461144887337>":
                    starsToAdd = 1;
                    break;
            }
            
            if (starsToAdd == -2) return;

            IUserMessage message = Message.GetOrDownloadAsync().GetAwaiter().GetResult();
            string user = message.Author.Mention;
            string messageId = message.Id.ToString();
            string guildId = message.GetJumpUrl().Split("/")[4];
            DateTime date = DateTime.Now.ToUniversalTime();

            await context.Connection().ExecuteAsync("SELECT insert_or_update_stars(@Guild, @Message, @Member, @Stars, @Date)", 
                new {Guild = guildId, Message = messageId, Member = user, Stars = starsToAdd, Date = new DateTime().ToUniversalTime()});
            
        }
        catch (Exception e)
        {
            Log.Logger.Error($"[Messages] {e}");
            return;
        }
    }
}