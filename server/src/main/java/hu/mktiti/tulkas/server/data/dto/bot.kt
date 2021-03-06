package hu.mktiti.tulkas.server.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import hu.mktiti.tulkas.server.data.dao.Bot

data class SimpleBotDto(
        val name: String,
        val ownerUsername: String,
        val game: String,
        val rank: Int?
)

fun Bot.toSimpleDto(ownerName: String, gameName: String) = SimpleBotDto(name, ownerName, gameName, rank)

fun List<Pair<Bot, String>>.toSimpleDtos(ownerName: String) = map { it.first.toSimpleDto(ownerName, it.second) }

data class DetailedBotData(
        val name: String,
        val ownerUsername: String,
        val game: String,
        val rank: Int?,
        val played: List<SimpleMatchDto>
)

data class BotUploadData(
        @JsonProperty("name") val name: String,
        @JsonProperty("game") val game: String,
        @JsonProperty("jarString") val jarString: String
)