package hu.mktiti.tulkas.server.data.handler

import hu.mktiti.kreator.api.inject
import hu.mktiti.tulkas.server.data.dao.ActorLog
import hu.mktiti.tulkas.server.data.dto.toSimpleDto
import hu.mktiti.tulkas.server.data.repo.ActorLogRepo
import javax.inject.Singleton
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/matches")
@Singleton
@Produces(value = [MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON])
class MatchHandler(
        private val actorLogRepo: ActorLogRepo = inject()
) {

    @GET
    @Path("{matchId}")
    fun matchLog(
            @PathParam("matchId") matchId: Long
    ) = entity {
        actorLogRepo.logsOfMatch(matchId).map(ActorLog::toSimpleDto)
    }

}