package Command.SlashCommands;

import Command.ISlashCommand;
import RoleMenus.SlashMenus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import java.util.*;

public class Menus implements ISlashCommand {

    private static final long INCOMING = 618643756580601857L;
    private static final long FRESHMAN = 581614531789455363L;
    private static final long SOPHOMORE = 618643636665712640L;
    private static final long JUNIOR = 618643685432623135L;
    private static final long SENIOR = 618643717384831017L;
    private static final long GRADUATE = 618643752327708682L;
    private static final long ALUMNI = 449689803219271681L;
    private static final long AI_GAMING = 822305355828559893L;
    private static final long DATA_SCIENCE = 822305557426864170L;
    private static final long SOFTWARE_SYSTEMS = 822305633327120404L;
    private static final long CYBER_SECURITY = 822305682882691094L;
    private static final long HCI = 822305742476148786L;
    private static final long INFO_TECH = 822305775967141930L;
    private static final long SOFTWARE_ENGINEER = 822305835086512178L;
    private static final long WEB_MOBILE = 822305884712992818L;
    private static final long BIO_INFORMATICS = 822305921672937472L;
    private static final long UNDECLARED = 852049528458444831L;
    private static final long CCI_EVENTS = 618652464643571712L;

    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            if(event.getSubcommandName().equals("yearroles")) SlashMenus.SlashYearMenu(event);
            if(event.getSubcommandName().equals("concentration")) SlashMenus.SlashConcetrationMenu(event);
            if(event.getSubcommandName().equals("ccievents")) SlashMenus.SlashCCIEvents(event);
        }else{
            event.reply("You don't have the `MANAGE_ROLES` permission, which is required to run this command!").setEphemeral(true).queue();
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {
        //Give or take CCI Events role
        if(event.getComponentId().startsWith("CCI_")){
            switch(event.getComponentId()) {
                case ("CCI_YES_CCIEVENTS"):
                    if (!event.getGuild().getMembersWithRoles(event.getGuild().getRoleById(CCI_EVENTS)).contains(event.getMember())) {
                        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(CCI_EVENTS)).queue();
                        event.reply("You will now receive pings for events!").setEphemeral(true).queue();
                    } else {
                        event.reply("You have already opted in for pings!").setEphemeral(true).queue();
                    }
                    break;
                case ("CCI_NO_CCIEVENTS"):
                    if (event.getGuild().getMembersWithRoles(event.getGuild().getRoleById(CCI_EVENTS)).contains(event.getMember())) {
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(CCI_EVENTS)).queue();
                        event.reply("You will no longer receive pings for events!").setEphemeral(true).queue();
                    } else {
                        event.reply("You don't need to opt-out for pings if you never opted-in!").setEphemeral(true).queue();
                    }
                    break;
            }
        }
        // Give year role to memeber
        if(event.getComponentId().startsWith("Year_")){
            // Remove existing year role
            SlashMenus.RemoveYearRole(event);
            switch(event.getComponentId()) {
                case ("Year_Incoming"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(INCOMING)).queue();
                    event.reply("Added Incoming Student role!").setEphemeral(true).queue();
                    break;
                case ("Year_Freshman"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(FRESHMAN)).queue();
                    event.reply("Added Freshman role!").setEphemeral(true).queue();
                    break;
                case ("Year_Sophomore"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SOPHOMORE)).queue();
                    event.reply("Added Sophomore role!").setEphemeral(true).queue();
                    break;
                case ("Year_Junior"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(JUNIOR)).queue();
                    event.reply("Added Junior role!").setEphemeral(true).queue();
                    break;
                case ("Year_Senior"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SENIOR)).queue();
                    event.reply("Added Senior role!").setEphemeral(true).queue();
                    break;
                case ("Year_Grad"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(GRADUATE)).queue();
                    event.reply("Added Grad student role!").setEphemeral(true).queue();
                    break;
                case ("Year_Alumni"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(ALUMNI)).queue();
                    event.reply("Added Alumni role!").setEphemeral(true).queue();
                    break;
            }
        }
        // Give concentration role to memeber
        if(event.getComponentId().startsWith("Conc_")){
            //remove existing concentration role
            SlashMenus.RemoveConcRole(event);
            switch(event.getComponentId()) {
                case ("Conc_SE"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SOFTWARE_ENGINEER)).queue();
                    event.reply("Added Software Engineering role!").setEphemeral(true).queue();
                    break;
                case ("Conc_Bioinformatics"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(BIO_INFORMATICS)).queue();
                    event.reply("Added Bioinformatics role!").setEphemeral(true).queue();
                    break;
                case ("Conc_ARG"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(AI_GAMING)).queue();
                    event.reply("Added AI, Robotics, and Gaminge role!").setEphemeral(true).queue();
                    break;
                case ("Conc_Data Science"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(DATA_SCIENCE)).queue();
                    event.reply("Added Data Science role!").setEphemeral(true).queue();
                    break;
                case ("Conc_IT"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(INFO_TECH)).queue();
                    event.reply("Added Information Technology role!").setEphemeral(true).queue();
                    break;
                case ("Conc_WM"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(WEB_MOBILE)).queue();
                    event.reply("Added Web and Mobile role!").setEphemeral(true).queue();
                    break;
                case ("Conc_HCI"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(HCI)).queue();
                    event.reply("Added Human-Computer Interaction role!").setEphemeral(true).queue();
                    break;
                case ("Conc_Cybersecurity"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(CYBER_SECURITY)).queue();
                    event.reply("Added Cybersecurity role!").setEphemeral(true).queue();
                    break;
                case ("Conc_SSN"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(SOFTWARE_SYSTEMS)).queue();
                    event.reply("Added Software, Systems, and Networks role!").setEphemeral(true).queue();
                    break;
                case ("Conc_UD"):
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(UNDECLARED)).queue();
                    event.reply("Added Undeclared role!").setEphemeral(true).queue();
                    break;
            }
        }
    }

    @Override
    public List<String> buttons() {
        return Arrays.asList("Year_Incoming","Year_Freshman","Year_Sophomore","Year_Junior","Year_Senior","Year_Grad","Year_Alumni","Conc_SE","Conc_Bioinformatics",
                "Conc_ARG","Conc_Data Science","Conc_IT","Conc_WM","Conc_HCI","Conc_Cybersecurity","Conc_SSN","Conc_UD","CCI_YES_CCIEVENTS","CCI_NO_CCIEVENTS");
    }

    @Override
    public String commandName() {
        return "menus";
    }
}
