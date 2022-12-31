package CommandManager.SlashCommands;

import java.util.Arrays;
import net.dv8tion.jda.api.entities.Role;
import java.util.Objects;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import RoleMenus.RWHSlashMenus;
import RoleMenus.SlashMenus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.util.ArrayList;
import java.util.List;
import CommandManager.ISlashCommand;

public class Menus implements ISlashCommand
{
    public List<String> yearNames;
    public List<String> pronounNames;
    public List<String> collegeNames;
    public List<String> concentrationNames;
    public List<String> livingNames;
    public List<String> platformNames;
    public List<String> schoolNames;
    public List<String> alumRoles;
    
    public Menus() {
        yearNames = new ArrayList<>(List.of("Incoming student", "Freshman", "Sophomore", "Junior", "Senior", "Graduate Student", "Alumni"));
        pronounNames = new ArrayList<>(List.of("He/Him", "She/Her", "They/Them", "He/They", "She/They", "Ask pronouns"));
        collegeNames = new ArrayList<>(List.of("Data Science", "Liberal Arts & Sciences", "Health & Human Services", "Engineering", "Education", "Computing & Informatics", "Arts + Architecture", "Business", "Undeclared"));
        concentrationNames = new ArrayList<>(List.of("software-eng", "bio-inf", "ai-gaming", "data-sci", "info-tech", "web-mobile", "hci", "cyber-sec", "software-systems", "Undeclared"));
        schoolNames = new ArrayList<>(List.of("UNCG", "ECU", "NCSU", "WCU", "UNC", "UNCC", "UNCW", "NCA&T", "APP", "UNCA", "ECSU", "WSSU", "FSU", "NCCU", "UNCP", "NCSA", "NCSSM", "Future Student"));
        livingNames = new ArrayList<>(List.of("On Campus", "Off Campus", "Commuter"));
        alumRoles = new ArrayList<>(List.of("PHD", "Masters", "Bachelors"));
        platformNames = new ArrayList<>(List.of("PC Gamers", "XBOX Gamers", "Mobile Gamers", "Playstation Gamers", "Switch Gamers"));
    }
    
    @Override
    public void run(SlashCommandInteractionEvent event) throws Exception {
        if (event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            String subcommandName = event.getSubcommandName();
            switch (subcommandName) {
                case "yearroles" -> SlashMenus.SlashYearMenu(event);

                case "pronounroles" -> SlashMenus.PronounMenu(event);

                case "collegeroles" -> SlashMenus.CollegeMenu(event);

                case "concentration" -> RWHSlashMenus.SlashConcetrationMenu(event);

                case "platforms" -> SlashMenus.PlatformsMenu(event);

                case "living" -> SlashMenus.LivingMenu(event);

            }
        }
        else {
            event.reply("You don't have the `MANAGE_ROLES` permission, which is required to run this command!").setEphemeral(true).queue();
        }
    }
    
    @Override
    public void run(ButtonInteractionEvent event) throws Exception {
        if (Objects.equals(event.getComponent().getId(), "Alum_Bachelors")) {
            SlashMenus.removeRoles(this.alumRoles, event);
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Bachelors", true).get(0)).queue();
            event.reply("Updated alum role!").setEphemeral(true).queue();
        }
        if (Objects.equals(event.getComponent().getId(), "Alum_Masters")) {
            SlashMenus.removeRoles(this.alumRoles, event);
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Masters", true).get(0)).queue();
            event.reply("Updated alum role!").setEphemeral(true).queue();
        }
        if (Objects.equals(event.getComponent().getId(), "Alum_PHD")) {
            SlashMenus.removeRoles(this.alumRoles, event);
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("PHD", true).get(0)).queue();
            event.reply("Updated alum role!").setEphemeral(true).queue();
        }

        if (Objects.equals(event.getComponent().getId(), "EventPing")) {
            Role Events = null;
            try {
                Events = event.getGuild().getRolesByName("Events", true).get(0);
            } catch (Exception e) {
                return;
            }

            if(event.getMember().getRoles().contains(Events)){
                event.getGuild().removeRoleFromMember(event.getMember(), Events).queue();
                event.reply("Removed events ping role!").setEphemeral(true).queue();
            } else {
                event.getGuild().addRoleToMember(event.getMember(), Events).queue();
                event.reply("Added events ping role!").setEphemeral(true).queue();
            }
        }

        if (Objects.equals(event.getComponent().getId(), "MeetupPing")) {
            Role Meetups = null;
            try {
                Meetups = event.getGuild().getRolesByName("Meetups", true).get(0);
            } catch (Exception e) {
                return;
            }

            if(event.getMember().getRoles().contains(Meetups)){
                event.getGuild().removeRoleFromMember(event.getMember(), Meetups).queue();
                event.reply("Removed meetups ping role!").setEphemeral(true).queue();
            } else {
                event.getGuild().addRoleToMember(event.getMember(), Meetups).queue();
                event.reply("Added meetups ping role!").setEphemeral(true).queue();
            }
        }

        if (Objects.equals(event.getComponent().getId(), "VCPing")) {
            Role VCPing = null;
            try {
                VCPing = event.getGuild().getRolesByName("VCPing", true).get(0);
            } catch (Exception e) {
                return;
            }

            if(event.getMember().getRoles().contains(VCPing)){
                event.getGuild().removeRoleFromMember(event.getMember(), VCPing).queue();
                event.reply("Removed VC ping role!").setEphemeral(true).queue();
            } else {
                event.getGuild().addRoleToMember(event.getMember(), VCPing).queue();
                event.reply("Added VC ping role!").setEphemeral(true).queue();
            }
        }
        if (Objects.equals(event.getComponent().getId(), "ChatPing")) {
            Role ChatPing = null;
            try {
                ChatPing = event.getGuild().getRolesByName("ChatPing", true).get(0);
            } catch (Exception e) {
                return;
            }

            if(event.getMember().getRoles().contains(ChatPing)){
                event.getGuild().removeRoleFromMember(event.getMember(), ChatPing).queue();
                event.reply("Removed Chat ping role!").setEphemeral(true).queue();
            } else {
                event.getGuild().addRoleToMember(event.getMember(), ChatPing).queue();
                event.reply("Added Chat ping role!").setEphemeral(true).queue();
            }
        }
    }
    
    @Override
    public void run(SelectMenuInteractionEvent event) throws Exception {
        if (Objects.equals(event.getComponent().getId(), "college")) {
            List<Role> collegeRoles = new ArrayList<>();
            for (String collegeName : this.collegeNames) {
                try {
                    collegeRoles.add(event.getGuild().getRolesByName(collegeName, true).get(0));
                }
                catch (IndexOutOfBoundsException e) {
                    event.getGuild().createRole().setName(collegeName).complete();
                    collegeRoles.add(event.getGuild().getRolesByName(collegeName, true).get(0));
                }
            }
            SlashMenus.removeRoles(this.collegeNames, event);
            for (String roles : event.getValues()) {
                switch (roles) {
                    case "College_Data" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(0)).queue();

                    case "College_Liberal" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(1)).queue();

                    case "College_Health" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(2)).queue();

                    case "College_Engineering" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(3)).queue();

                    case "College_Education" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(4)).queue();

                    case "College_Computing" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(5)).queue();

                    case "College_Arts" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(6)).queue();

                    case "College_Business" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(7)).queue();

                    case "College_Undec" -> event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(8)).queue();

                }
            }
            event.reply("Updated college role!").setEphemeral(true).queue();
        }
        if (Objects.equals(event.getComponent().getId(), "pronouns")) {
            List<Role> pronounRoles = new ArrayList<>();
            for (String pronounName : this.pronounNames) {
                try {
                    pronounRoles.add(event.getGuild().getRolesByName(pronounName, true).get(0));
                }
                catch (IndexOutOfBoundsException e) {
                    event.getGuild().createRole().setName(pronounName).complete();
                    pronounRoles.add(event.getGuild().getRolesByName(pronounName, true).get(0));
                }
            }
            SlashMenus.removeRoles(this.pronounNames, event);
            for (String roles : event.getValues()) {
                switch (roles) {
                    case "Pron_He" -> event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(0)).queue();

                    case "Pron_She" -> event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(1)).queue();

                    case "Pron_They" -> event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(2)).queue();

                    case "Pron_HeThey" -> event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(3)).queue();

                    case "Pron_SheThey" -> event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(4)).queue();

                    case "Pron_Ask" -> event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(5)).queue();

                }
            }
            event.reply("Pronouns updated!").setEphemeral(true).queue();
        }
        if (Objects.equals(event.getComponent().getId(), "years")) {
            List<Role> yearRoles = new ArrayList<>();
            for (String yearName : this.yearNames) {
                try {
                    yearRoles.add(event.getGuild().getRolesByName(yearName, true).get(0));
                }
                catch (IndexOutOfBoundsException e) {
                    event.getGuild().createRole().setName(yearName).complete();
                    yearRoles.add(event.getGuild().getRolesByName(yearName, true).get(0));
                }
            }
            SlashMenus.removeRoles(this.yearNames, event);
            for (String roles : event.getValues()) {
                switch (roles) {
                    case "Year_Incoming" -> event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(0)).queue();

                    case "Year_Freshman" -> event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(1)).queue();

                    case "Year_Sophomore" -> event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(2)).queue();

                    case "Year_Junior" -> event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(3)).queue();

                    case "Year_Senior" -> event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(4)).queue();

                    case "Year_Grad" -> event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(5)).queue();

                    case "Year_Alumni" -> event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(6)).queue();

                }
            }
            event.reply("Updated year role").setEphemeral(true).queue();
        }
        if (Objects.equals(event.getComponent().getId(), "concentration")) {
            List<Role> concentrationRoles = new ArrayList<>();
            for (String concentrationName : this.concentrationNames) {
                try {
                    concentrationRoles.add(event.getGuild().getRolesByName(concentrationName, true).get(0));
                }
                catch (IndexOutOfBoundsException e) {
                    event.getGuild().createRole().setName(concentrationName).complete();
                    concentrationRoles.add(event.getGuild().getRolesByName(concentrationName, true).get(0));
                }
            }
            SlashMenus.removeRoles(this.concentrationNames, event);
            for (String roles : event.getValues()) {
                switch (roles) {
                    case "Conc_SE" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(0)).queue();

                    case "Conc_Bioinformatics" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(1)).queue();

                    case "Conc_ARG" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(2)).queue();

                    case "Conc_DataScience" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(3)).queue();

                    case "Conc_IT" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(4)).queue();

                    case "Conc_WM" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(5)).queue();

                    case "Conc_HCI" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(6)).queue();

                    case "Conc_Cybersecurity" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(7)).queue();

                    case "Conc_SSN" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(8)).queue();

                    case "Conc_UD" -> event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(9)).queue();

                }
            }
            event.reply("Updated concentration role!").setEphemeral(true).queue();
        }
        if (Objects.equals(event.getComponent().getId(), "platform")) {
            List<Role> platformRoles = new ArrayList<>();
            for (String platformName : this.platformNames) {
                try {
                    platformRoles.add(event.getGuild().getRolesByName(platformName, true).get(0));
                }
                catch (IndexOutOfBoundsException e) {
                    event.getGuild().createRole().setName(platformName).complete();
                    platformRoles.add(event.getGuild().getRolesByName(platformName, true).get(0));
                }
            }
            SlashMenus.removeRoles(this.platformNames, event);
            for (String roles : event.getValues()) {
                switch (roles) {
                    case "Platform_PC" -> event.getGuild().addRoleToMember(event.getMember(), platformRoles.get(0)).queue();

                    case "Platform_XBOX" -> event.getGuild().addRoleToMember(event.getMember(), platformRoles.get(1)).queue();

                    case "Platform_Mobile" -> event.getGuild().addRoleToMember(event.getMember(), platformRoles.get(2)).queue();

                    case "Platform_PS" -> event.getGuild().addRoleToMember(event.getMember(), platformRoles.get(3)).queue();

                    case "Platform_Switch" -> event.getGuild().addRoleToMember(event.getMember(), platformRoles.get(4)).queue();

                }
            }
            event.reply("Updated platform roles!").setEphemeral(true).queue();
        }
        if (Objects.equals(event.getComponent().getId(), "living")) {
            List<Role> livingRoles = new ArrayList<>();
            for (String livingName : this.livingNames) {
                try {
                    livingRoles.add(event.getGuild().getRolesByName(livingName, true).get(0));
                }
                catch (IndexOutOfBoundsException e) {
                    event.getGuild().createRole().setName(livingName).complete();
                    livingRoles.add(event.getGuild().getRolesByName(livingName, true).get(0));
                }
            }
            SlashMenus.removeRoles(this.livingNames, event);
            for (String roles : event.getValues()) {
                switch (roles) {
                    case "Living_On" -> event.getGuild().addRoleToMember(event.getMember(), livingRoles.get(0)).queue();

                    case "Living_Off" -> event.getGuild().addRoleToMember(event.getMember(), livingRoles.get(1)).queue();

                    case "Living_Commuter" -> event.getGuild().addRoleToMember(event.getMember(), livingRoles.get(2)).queue();

                }
            }
            event.reply("Updated location role!").setEphemeral(true).queue();
        }
        if (Objects.equals(event.getComponent().getId(), "schools")) {
            event.deferReply().setEphemeral(true).queue();
            List<Role> schoolRoles = new ArrayList<>();
            for (String schoolName : this.schoolNames) {
                try {
                    schoolRoles.add(event.getGuild().getRolesByName(schoolName, true).get(0));
                }
                catch (IndexOutOfBoundsException e) {
                    event.getGuild().createRole().setName(schoolName).complete();
                    schoolRoles.add(event.getGuild().getRolesByName(schoolName, true).get(0));
                }
            }
            SlashMenus.removeRoles(this.schoolNames, event);
            for (String roles : event.getValues()) {
                switch (roles) {
                    case "UNCG" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(0)).queue();

                    case "ECU" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(1)).queue();

                    case "NCSU" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(2)).queue();

                    case "WCU" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(3)).queue();

                    case "UNC" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(4)).queue();

                    case "UNCC" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(5)).queue();

                    case "UNCW" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(6)).queue();

                    case "NCA&T" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(7)).queue();

                    case "APP" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(8)).queue();

                    case "UNCA" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(9)).queue();

                    case "ECSU" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(10)).queue();

                    case "WSSU" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(11)).queue();

                    case "FSU" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(12)).queue();

                    case "NCCU" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(13)).queue();

                    case "UNCP" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(14)).queue();

                    case "NCSA" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(15)).queue();

                    case "NCSSM" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(16)).queue();

                    case "Future_Student" -> event.getGuild().addRoleToMember(event.getMember(), schoolRoles.get(17)).queue();

                }
            }
            event.getHook().editOriginal("Updated school role!").queue();
        }
    }
    
    @Override
    public List<String> buttons() {
        return Arrays.asList("Year_Incoming", "Year_Freshman", "Year_Sophomore", "Year_Junior", "Year_Senior", "Year_Grad",
                "Year_Alumni", "Conc_SE", "Conc_Bioinformatics", "Conc_ARG", "Conc_Data Science", "Conc_IT", "Conc_WM",
                "Conc_HCI", "Conc_Cybersecurity", "Conc_SSN", "Conc_UD", "Pron_He", "Pron_She", "Pron_They", "Pron_HeThey",
                "Pron_SheThey", "Pron_Ask", "College_Data", "College_Liberal", "College_Health", "College_Engineering",
                "College_Education", "College_Computing", "College_Arts", "College_Business", "College_Undec", "Living_On",
                "Living_Off", "Living_Commuter", "Platform_PC", "Platform_XBOX", "Platform_Mobile", "Platform_PS", "Platform_Switch",
                "pronouns", "years", "college", "concentration", "platform", "living", "EventPing", "MeetupPing", "schools",
                "UNCG", "ECU", "NCSU", "WCU", "UNC", "UNCC", "UNCW", "NCA&T", "APP", "UNCA", "ECSU", "WSSU", "FSU",
                "NCCU", "UNCP", "NCSA", "NCSSM", "Future Student", "VCPing", "ChatPing", "Alum_Bachelors", "Alum_Masters", "Alum_PHD");
    }
    
    @Override
    public String commandName() {
        return "menus";
    }
    
    @Override
    public Boolean enabled() {
        return true;
    }
    
    @Override
    public String description() {
        return null;
    }
}
