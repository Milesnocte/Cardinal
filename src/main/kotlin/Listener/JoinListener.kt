package Listener

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.RawGatewayEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.hooks.EventListener
import java.util.concurrent.CopyOnWriteArraySet

class JoinListener : EventListener {

    private val newMembers = CopyOnWriteArraySet<Long>()

    override fun onEvent(event: GenericEvent) {
        when (event) {
            is GuildMemberJoinEvent -> onGuildMemberJoinEvent(event)
            is GuildMemberRemoveEvent -> onGuildMemberRemoveEvent(event)
            is RawGatewayEvent -> onRawGatewayEvent(event)
        }
    }

    private fun onGuildMemberJoinEvent(event: GuildMemberJoinEvent) {
        newMembers += event.user.idLong
    }

    private fun onGuildMemberRemoveEvent(event: GuildMemberRemoveEvent) {
        newMembers -= event.user.idLong
    }

    private fun onRawGatewayEvent(event: RawGatewayEvent) {
        if (event.type != "GUILD_MEMBER_UPDATE") return
        val userId = event.payload
            .getObject("user")
            .getString("id")
            .toLong()
        if (userId !in newMembers) return
        val isPending = event.payload
            .getBoolean("is_pending", false)
        if (isPending) return
        newMembers -= userId
        onMembershipScreeningComplete(event, userId)
    }

    private fun onMembershipScreeningComplete(event: RawGatewayEvent, userId: Long) {
        val announceGuildId = event.payload
            .getString("guild_id")
            .toLong()

        val guild = event.jda.getGuildById(announceGuildId)!!

        guild.systemChannel
            ?.sendMessage("Welcome <@${userId}>, please visit <#618647310179762188> to select a class role!")
            ?.queue()
    }
}