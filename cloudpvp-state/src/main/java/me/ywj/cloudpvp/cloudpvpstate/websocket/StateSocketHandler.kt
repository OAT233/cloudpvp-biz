package me.ywj.cloudpvp.cloudpvpstate.websocket

import me.ywj.cloudpvp.cloudpvpstate.constant.StateEnum
import me.ywj.cloudpvp.cloudpvpstate.entity.PlayerState
import me.ywj.cloudpvp.cloudpvpstate.service.PlayerStateService
import me.ywj.cloudpvp.core.type.SteamId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler

/**
 * StateSocketHandler
 *
 * @author sheip9
 * @since 2024/10/17 12:21
 */
@Controller
class StateSocketHandler @Autowired constructor(private val playerStateService: PlayerStateService) : AbstractWebSocketHandler(), WebSocketHandler {
    private val PLAYER_MAP = HashMap<SteamId, PlayerState>()
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val steamId = session.attributes["steamId"] as SteamId
        val player = PlayerState(steamId).apply {
            state = StateEnum.ONLINE
        }
        PLAYER_MAP[steamId] = player
        playerStateService.setState(player)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val steamId = session.attributes["steamId"] as SteamId
        playerStateService.onDisconnect(PLAYER_MAP[steamId]!!)
        PLAYER_MAP.remove(steamId)
    }
}