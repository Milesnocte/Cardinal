using Discord;
using Discord.WebSocket;
using Serilog;

namespace DiscordBot.Bot.Interactions;

public class Component
{
    private static List<String> yearNames = new() {"Incoming student", "Freshman", "Sophomore", "Junior", "Senior", "Graduate Student", "Alumni"};
    private static List<String> pronounNames = new() {"He/Him", "She/Her", "They/Them", "He/They", "She/They", "Ask pronouns"};
    private static List<String> collegeNames = new() {"Data Science", "Liberal Arts & Sciences", "Health & Human Services", "Engineering", "Education", "Computing & Informatics", "Arts + Architecture", "Business", "Undeclared"};
    private static List<String> concentrationNames = new() {"software-eng", "bio-inf", "ai-gaming", "data-sci", "info-tech", "web-mobile", "hci", "cyber-sec", "software-systems", "Undeclared"};
    private static List<String> livingNames = new() {"On Campus", "Off Campus", "Commuter"};
    private static List<String> platformNames= new() {"PC Gamers", "XBOX Gamers", "Mobile Gamers", "Playstation Gamers", "Switch Gamers"};
    private static List<String> schoolNames = new() {"UNCG", "ECU", "NCSU", "WCU", "UNC", "CLT", "UNCW", "NCA&T", "APP", "UNCA", "ECSU", "WSSU", "FSU", "NCCU", "UNCP", "NCSA", "NCSSM", "Future Student"};
    
    private static DiscordSocketClient _socketClient;

    public Component(DiscordSocketClient client)
    {
        _socketClient = client;
    }

    public static async Task ButtonExecuted(SocketMessageComponent arg)
    {
        try
        {
            var component = arg.Data.Value;
            var guild = _socketClient.GetGuild(arg.GuildId.Value);
            var user = guild.GetUser(arg.User.Id);

            switch (component)
            {
                case "EventPing":
                    var events = guild.Roles.FirstOrDefault(x => x.Name.ToLower() == "events");
                    if (user.Roles.Contains(events)) await user.RemoveRoleAsync(events);
                    await user.AddRoleAsync(events);
                    break;
                case "VCPing":
                    var vcPing = guild.Roles.FirstOrDefault(x => x.Name.ToLower() == "vcping");
                    if (user.Roles.Contains(vcPing)) await user.RemoveRoleAsync(vcPing);
                    await user.AddRoleAsync(vcPing);
                    break;
                case "ChatPing":
                    var chatPing = guild.Roles.FirstOrDefault(x => x.Name.ToLower() == "chatping");
                    if (user.Roles.Contains(chatPing)) await user.RemoveRoleAsync(chatPing);
                    await user.AddRoleAsync(chatPing);
                    break;
            }
        }
        catch (Exception e)
        {
            Log.Logger.Error($"[Component] {e}");
        }
    }

    public static async Task SelectMenuExecuted(SocketMessageComponent arg)
    {
        try
        {
            var component = arg.Data.CustomId;
            var guild = _socketClient.GetGuild(arg.GuildId.Value);
            var user = guild.GetUser(arg.User.Id);

            await arg.DeferAsync(true);

            List<ulong> rolesToAdd = new();
            List<ulong> rolesToRemove = new();

            if (component.StartsWith("college"))
            {
                foreach (var collegeName in collegeNames)
                {
                    rolesToRemove.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeName.ToLower())!.Id);
                }

                foreach (var dataValue in arg.Data.Values)
                {
                    if (dataValue == "College_Data")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[0].ToLower())!
                            .Id);
                    }

                    if (dataValue == "College_Liberal")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[1].ToLower())!
                            .Id);
                    }

                    if (dataValue == "College_Health")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[2].ToLower())!
                            .Id);
                    }

                    if (dataValue == "College_Engineering")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[3].ToLower())!
                            .Id);
                    }

                    if (dataValue == "College_Education")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[4].ToLower())!
                            .Id);
                    }

                    if (dataValue == "College_Computing")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[5].ToLower())!
                            .Id);
                    }

                    if (dataValue == "College_Arts")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[6].ToLower())!
                            .Id);
                    }

                    if (dataValue == "College_Business")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[7].ToLower())!
                            .Id);
                    }

                    if (dataValue == "College_Undec")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeNames[8].ToLower())!
                            .Id);
                    }
                }
            }

            if (component.StartsWith("pronouns"))
            {
                foreach (var collegeName in pronounNames)
                {
                    rolesToRemove.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeName.ToLower())!.Id);
                }

                foreach (var dataValue in arg.Data.Values)
                {
                    if (dataValue == "Pron_He")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == pronounNames[0].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Pron_She")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == pronounNames[1].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Pron_They")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == pronounNames[2].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Pron_HeThey")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == pronounNames[3].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Pron_SheThey")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == pronounNames[4].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Pron_Ask")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == pronounNames[5].ToLower())!
                            .Id);
                    }
                }
            }

            if (component.StartsWith("years"))
            {
                foreach (var collegeName in yearNames)
                {
                    rolesToRemove.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeName.ToLower())!.Id);
                }

                foreach (var dataValue in arg.Data.Values)
                {
                    if (dataValue == "Year_Incoming")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == yearNames[0].ToLower())!.Id);
                    }

                    if (dataValue == "Year_Freshman")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == yearNames[1].ToLower())!.Id);
                    }

                    if (dataValue == "Year_Sophomore")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == yearNames[2].ToLower())!.Id);
                    }

                    if (dataValue == "Year_Junior")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == yearNames[3].ToLower())!.Id);
                    }

                    if (dataValue == "Year_Senior")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == yearNames[4].ToLower())!.Id);
                    }

                    if (dataValue == "Year_Grad")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == yearNames[5].ToLower())!.Id);
                    }

                    if (dataValue == "Year_Alumni")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == yearNames[6].ToLower())!.Id);
                    }
                }
            }

            if (component.StartsWith("concentration"))
            {
                foreach (var collegeName in concentrationNames)
                {
                    rolesToRemove.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeName.ToLower())!.Id);
                }

                foreach (var dataValue in arg.Data.Values)
                {
                    if (dataValue == "Conc_SE")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[0].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_Bioinformatics")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[1].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_ARG")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[2].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_DataScience")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[3].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_IT")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[4].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_WM")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[5].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_HCI")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[6].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_Cybersecurity")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[7].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_SSN")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[8].ToLower())!.Id);
                    }

                    if (dataValue == "Conc_UD")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x =>
                            x.Name.ToLower() == concentrationNames[9].ToLower())!.Id);
                    }
                }
            }

            if (component.StartsWith("platform"))
            {
                foreach (var collegeName in platformNames)
                {
                    rolesToRemove.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeName.ToLower())!.Id);
                }

                foreach (var dataValue in arg.Data.Values)
                {
                    if (dataValue == "Platform_PC")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == platformNames[0].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Platform_XBOX")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == platformNames[1].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Platform_Mobile")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == platformNames[2].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Platform_PS")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == platformNames[3].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Platform_Switch")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == platformNames[4].ToLower())!
                            .Id);
                    }
                }
            }

            if (component.StartsWith("living"))
            {
                foreach (var collegeName in livingNames)
                {
                    rolesToRemove.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeName.ToLower())!.Id);
                }

                foreach (var dataValue in arg.Data.Values)
                {
                    if (dataValue == "Living_On")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == livingNames[0].ToLower())!.Id);
                    }

                    if (dataValue == "Living_Off")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == platformNames[1].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Living_Commuter")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == platformNames[2].ToLower())!
                            .Id);
                    }
                }
            }

            if (component.StartsWith("schools"))
            {
                foreach (var collegeName in schoolNames)
                {
                    rolesToRemove.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == collegeName.ToLower())!.Id);
                }

                foreach (var dataValue in arg.Data.Values)
                {
                    if (dataValue == "UNCG")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[0].ToLower())!.Id);
                    }

                    if (dataValue == "ECU")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[1].ToLower())!.Id);
                    }

                    if (dataValue == "NCSU")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[2].ToLower())!.Id);
                    }

                    if (dataValue == "WCU")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[3].ToLower())!.Id);
                    }

                    if (dataValue == "UNC")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[4].ToLower())!.Id);
                    }

                    if (dataValue == "UNCC")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[5].ToLower())!.Id);
                    }

                    if (dataValue == "UNCW")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[6].ToLower())!.Id);
                    }

                    if (dataValue == "NCA&T")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[7].ToLower())!.Id);
                    }

                    if (dataValue == "APP")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[8].ToLower())!.Id);
                    }

                    if (dataValue == "UNCA")
                    {
                        rolesToAdd.Add(
                            guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[9].ToLower())!.Id);
                    }

                    if (dataValue == "ECSU")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[10].ToLower())!
                            .Id);
                    }

                    if (dataValue == "WSSU")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[11].ToLower())!
                            .Id);
                    }

                    if (dataValue == "FSU")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[12].ToLower())!
                            .Id);
                    }

                    if (dataValue == "NCCU")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[13].ToLower())!
                            .Id);
                    }

                    if (dataValue == "UNCP")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[14].ToLower())!
                            .Id);
                    }

                    if (dataValue == "NCSA")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[15].ToLower())!
                            .Id);
                    }

                    if (dataValue == "NCSSM")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[16].ToLower())!
                            .Id);
                    }

                    if (dataValue == "Future_Student")
                    {
                        rolesToAdd.Add(guild.Roles.FirstOrDefault(x => x.Name.ToLower() == schoolNames[17].ToLower())!
                            .Id);
                    }
                }
            }

            if (rolesToAdd.Count == 0) return;
            await user.ModifyAsync(x => x.RoleIds = user.Roles.Select(r => r.Id)
                .Except(new[] { user.Guild.Id })
                .Except(rolesToRemove)
                .Concat(rolesToAdd)
                .Distinct()
                .ToList());
            await arg.FollowupAsync("Updated Roles!", ephemeral: true);
        }
        catch (Exception e)
        {
            Log.Logger.Error($"[Component] {e}");
        }
    }
}