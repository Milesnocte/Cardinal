package CommandManager.SlashCommands;

import CommandManager.ISlashCommand;
import RoleMenus.RWHSlashMenus;
import RoleMenus.SlashMenus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menus implements ISlashCommand {

    public List<String> yearNames = new ArrayList<>(List.of("Incoming student", "Freshman", "Sophomore", "Junior", "Senior", "Graduate Student", "Alumni"));
    public List<String> pronounNames = new ArrayList<>(List.of("He/Him", "She/Her", "They/Them", "He/They", "She/They", "Ask pronouns"));
    public List<String> collegeNames = new ArrayList<>(List.of("Data Science", "Liberal Arts & Sciences", "Health & Human Services", "Engineering", "Education", "Computing & Informatics", "Arts + Architecture", "Business", "Undeclared"));
    public List<String> concentrationNames = new ArrayList<>(List.of("ai-gaming", "data-sci", "software-systems", "cyber-sec", "hci", "info-tech", "software-eng", "web-mobile", "bio-inf", "Undeclared"));

    @Override
    public void run(SlashCommandEvent event) throws Exception {
        if (event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            switch (event.getSubcommandName()) {
                case "yearroles" -> {
                    SlashMenus.SlashYearMenu(event);
                }
                case "pronounroles" -> {
                    SlashMenus.PronounMenu(event);
                }
                case "collegeroles" -> {
                    SlashMenus.CollegeMenu(event);
                }
                case "polirole" -> {
                    SlashMenus.PoliMenu(event);
                }
                case "concentration" -> {
                    RWHSlashMenus.SlashConcetrationMenu(event);
                }
                case "ccievents" -> {
                    RWHSlashMenus.SlashCCIEvents(event);
                }
            }
        } else {
            event.reply("You don't have the `MANAGE_ROLES` permission, which is required to run this command!").setEphemeral(true).queue();
        }
    }

    @Override
    public void run(ButtonClickEvent event) throws Exception {

        //Give or take Debate role
        if (event.getComponentId().startsWith("POLI_")) {

            Role politics;

            try {
                politics = (event.getGuild().getRolesByName("Debate", true).get(0));
            }catch (IndexOutOfBoundsException e){
                event.getGuild().createRole().setName("Debate").complete();
                politics = (event.getGuild().getRolesByName("Debate", true).get(0));
            }

            final long DEBATE = politics.getIdLong();

            switch (event.getComponentId()) {
                case ("POLI_YES"):
                    if (!event.getGuild().getMembersWithRoles(event.getGuild().getRoleById(DEBATE)).contains(event.getMember())) {
                        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(DEBATE)).queue();
                        event.reply("You will now have access to politics!").setEphemeral(true).queue();
                    } else {
                        event.reply("You have already opted in for politics!").setEphemeral(true).queue();
                    }
                    break;
                case ("POLI_NO"):
                    if (event.getGuild().getMembersWithRoles(event.getGuild().getRoleById(DEBATE)).contains(event.getMember())) {
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(DEBATE)).queue();
                        event.reply("You will no longer have access to politics!").setEphemeral(true).queue();
                    } else {
                        event.reply("You don't need to opt-out for politics if you never opted-in!").setEphemeral(true).queue();
                    }
                    break;
            }
        }

        if (event.getComponentId().startsWith("College_")) {

            List<Role> collegeRoles = new ArrayList<>();

            for(int k = 0; k < collegeNames.size(); k++){
                try {
                    collegeRoles.add(event.getGuild().getRolesByName(collegeNames.get(k), true).get(0));
                }catch (IndexOutOfBoundsException e){
                    event.getGuild().createRole().setName(collegeNames.get(k)).complete();
                    collegeRoles.add(event.getGuild().getRolesByName(collegeNames.get(k), true).get(0));
                }
            }

            // Remove college role
            SlashMenus.removeRoles(collegeNames, event);

            switch (event.getComponentId()) {
                case ("College_Data") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(0)).queue();
                    event.reply("Added Data Science role!").setEphemeral(true).queue();
                }
                case ("College_Liberal") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(1)).queue();
                    event.reply("Added Liberal Arts role!").setEphemeral(true).queue();
                }
                case ("College_Health") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(2)).queue();
                    event.reply("Added Health role!").setEphemeral(true).queue();
                }
                case ("College_Engineering") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(3)).queue();
                    event.reply("Added Engineering role!").setEphemeral(true).queue();
                }
                case ("College_Education") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(4)).queue();
                    event.reply("Added Education role!").setEphemeral(true).queue();
                }
                case ("College_Computing") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(5)).queue();
                    event.reply("Added CCI role!").setEphemeral(true).queue();
                }
                case ("College_Arts") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(6)).queue();
                    event.reply("Added Arts + Architecture role!").setEphemeral(true).queue();
                }
                case ("College_Business") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(7)).queue();
                    event.reply("Added Business role!").setEphemeral(true).queue();
                }
                case ("College_Undec") -> {
                    event.getGuild().addRoleToMember(event.getMember(), collegeRoles.get(8)).queue();
                    event.reply("Added Undeclared role!").setEphemeral(true).queue();
                }
            }
        }

        // Give year role to memeber
        if (event.getComponentId().startsWith("Pron_")) {

            List<Role> pronounRoles = new ArrayList<>();

            for(int k = 0; k < pronounNames.size(); k++){
                try {
                    pronounRoles.add(event.getGuild().getRolesByName(pronounNames.get(k), true).get(0));
                }catch (IndexOutOfBoundsException e){
                    event.getGuild().createRole().setName(pronounNames.get(k)).complete();
                    pronounRoles.add(event.getGuild().getRolesByName(pronounNames.get(k), true).get(0));
                }
            }

            // Allows for the selection of multiple roles, removes if they select one they already have
            switch (event.getComponentId()) {
                case ("Pron_He") -> {
                    if(event.getMember().getRoles().contains(pronounRoles.get(0))){
                        event.getGuild().removeRoleFromMember(event.getMember(), pronounRoles.get(0)).queue();
                        event.reply("Removed He/Him role!").setEphemeral(true).queue();
                        break;
                    }
                    event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(0)).queue();
                    event.reply("Added He/Him role!").setEphemeral(true).queue();
                }
                case ("Pron_She") -> {
                    if(event.getMember().getRoles().contains(pronounRoles.get(1))){
                        event.reply("Removed She/Her role!").setEphemeral(true).queue();
                        event.getGuild().removeRoleFromMember(event.getMember(), pronounRoles.get(1)).queue();
                        break;
                    }
                    event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(1)).queue();
                    event.reply("Added She/Her role!").setEphemeral(true).queue();
                }
                case ("Pron_They") -> {
                    if(event.getMember().getRoles().contains(pronounRoles.get(2))){
                        event.getGuild().removeRoleFromMember(event.getMember(), pronounRoles.get(2)).queue();
                        event.reply("Removed They/Them role!").setEphemeral(true).queue();
                        break;
                    }
                    event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(2)).queue();
                    event.reply("Added They/Them role!").setEphemeral(true).queue();
                }
                case ("Pron_HeThey") -> {
                    if(event.getMember().getRoles().contains(pronounRoles.get(3))){
                        event.getGuild().removeRoleFromMember(event.getMember(), pronounRoles.get(3)).queue();
                        event.reply("Removed He/They role!").setEphemeral(true).queue();
                        break;
                    }
                    event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(3)).queue();
                    event.reply("Added He/They role!").setEphemeral(true).queue();
                }
                case ("Pron_SheThey") -> {
                    if(event.getMember().getRoles().contains(pronounRoles.get(4))){
                        event.getGuild().removeRoleFromMember(event.getMember(), pronounRoles.get(4)).queue();
                        event.reply("Removed She/They role!").setEphemeral(true).queue();
                        break;
                    }
                    event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(4)).queue();
                    event.reply("Added She/They role!").setEphemeral(true).queue();
                }
                case ("Pron_Ask") -> {
                    if(event.getMember().getRoles().contains(pronounRoles.get(5))){
                        event.getGuild().removeRoleFromMember(event.getMember(), pronounRoles.get(5)).queue();
                        event.reply("Removed Ask Me role!").setEphemeral(true).queue();
                        break;
                    }
                    event.getGuild().addRoleToMember(event.getMember(), pronounRoles.get(5)).queue();
                    event.reply("Added Ask Me role!").setEphemeral(true).queue();
                }
            }
        }

        // Give year role to member
        if (event.getComponentId().startsWith("Year_")) {

            List<Role> yearRoles = new ArrayList<>();

            for(int k = 0; k < yearNames.size(); k++){
                try {
                    yearRoles.add(event.getGuild().getRolesByName(yearNames.get(k), true).get(0));
                }catch (IndexOutOfBoundsException e){
                    event.getGuild().createRole().setName(yearNames.get(k)).complete();
                    yearRoles.add(event.getGuild().getRolesByName(yearNames.get(k), true).get(0));
                }
            }

            // Remove existing year role
            SlashMenus.removeRoles(yearNames, event);
            switch (event.getComponentId()) {
                case ("Year_Incoming") -> {
                    event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(0)).queue();
                    event.reply("Added Incoming Student role!").setEphemeral(true).queue();
                }
                case ("Year_Freshman") -> {
                    event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(1)).queue();
                    event.reply("Added Freshman role!").setEphemeral(true).queue();
                }
                case ("Year_Sophomore") -> {
                    event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(2)).queue();
                    event.reply("Added Sophomore role!").setEphemeral(true).queue();
                }
                case ("Year_Junior") -> {
                    event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(3)).queue();
                    event.reply("Added Junior role!").setEphemeral(true).queue();
                }
                case ("Year_Senior") -> {
                    event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(4)).queue();
                    event.reply("Added Senior role!").setEphemeral(true).queue();
                }
                case ("Year_Grad") -> {
                    event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(5)).queue();
                    event.reply("Added Grad student role!").setEphemeral(true).queue();
                }
                case ("Year_Alumni") -> {
                    event.getGuild().addRoleToMember(event.getMember(), yearRoles.get(6)).queue();
                    event.reply("Added Alumni role!").setEphemeral(true).queue();
                }
            }
        }
        // Give concentration role to memeber
        if (event.getComponentId().startsWith("Conc_")) {

            List<Role> concentrationRoles = new ArrayList<>();

            for(int k = 0; k < concentrationNames.size(); k++){
                try {
                    concentrationRoles.add(event.getGuild().getRolesByName(concentrationNames.get(k), true).get(0));
                }catch (IndexOutOfBoundsException e){
                    event.getGuild().createRole().setName(concentrationNames.get(k)).complete();
                    concentrationRoles.add(event.getGuild().getRolesByName(concentrationNames.get(k), true).get(0));
                }
            }

            //remove existing concentration role
            SlashMenus.removeRoles(collegeNames, event);
            switch (event.getComponentId()) {
                case ("Conc_SE") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(0)).queue();
                    event.reply("Added Software Engineering role!").setEphemeral(true).queue();
                }
                case ("Conc_Bioinformatics") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(1)).queue();
                    event.reply("Added Bioinformatics role!").setEphemeral(true).queue();
                }
                case ("Conc_ARG") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(2)).queue();
                    event.reply("Added AI, Robotics, and Gaming role!").setEphemeral(true).queue();
                }
                case ("Conc_Data Science") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(3)).queue();
                    event.reply("Added Data Science role!").setEphemeral(true).queue();
                }
                case ("Conc_IT") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(4)).queue();
                    event.reply("Added Information Technology role!").setEphemeral(true).queue();
                }
                case ("Conc_WM") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(5)).queue();
                    event.reply("Added Web and Mobile role!").setEphemeral(true).queue();
                }
                case ("Conc_HCI") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(6)).queue();
                    event.reply("Added Human-Computer Interaction role!").setEphemeral(true).queue();
                }
                case ("Conc_Cybersecurity") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(7)).queue();
                    event.reply("Added Cybersecurity role!").setEphemeral(true).queue();
                }
                case ("Conc_SSN") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(8)).queue();
                    event.reply("Added Software, Systems, and Networks role!").setEphemeral(true).queue();
                }
                case ("Conc_UD") -> {
                    event.getGuild().addRoleToMember(event.getMember(), concentrationRoles.get(9)).queue();
                    event.reply("Added Undeclared role!").setEphemeral(true).queue();
                }
            }
        }
    }

    @Override
    public List<String> buttons() {
        return Arrays.asList("Year_Incoming", "Year_Freshman", "Year_Sophomore", "Year_Junior", "Year_Senior", "Year_Grad", "Year_Alumni", "Conc_SE", "Conc_Bioinformatics",
                "Conc_ARG", "Conc_Data Science", "Conc_IT", "Conc_WM", "Conc_HCI", "Conc_Cybersecurity", "Conc_SSN", "Conc_UD", "Pron_He", "Pron_She", "Pron_They",
                "Pron_HeThey", "Pron_SheThey", "Pron_Ask", "College_Data", "College_Liberal", "College_Health", "College_Engineering", "College_Education",
                "College_Computing", "College_Arts", "College_Business", "College_Undec", "POLI_YES", "POLI_NO");
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
