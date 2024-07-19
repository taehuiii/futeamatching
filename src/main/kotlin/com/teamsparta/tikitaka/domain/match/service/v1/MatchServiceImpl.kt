package com.teamsparta.tikitaka.domain.match.service.v1

import com.teamsparta.tikitaka.domain.common.Region
import com.teamsparta.tikitaka.domain.common.exception.AccessDeniedException
import com.teamsparta.tikitaka.domain.match.dto.MatchResponse
import com.teamsparta.tikitaka.domain.match.dto.PostMatchRequest
import com.teamsparta.tikitaka.domain.match.dto.UpdateMatchRequest
import com.teamsparta.tikitaka.domain.match.model.Match
import com.teamsparta.tikitaka.domain.match.model.SortCriteria
import com.teamsparta.tikitaka.domain.match.repository.MatchRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class MatchServiceImpl(
    private val matchRepository: MatchRepository,

    ) : MatchService {

    @Transactional
    override fun postMatch(
        principal: UserPrincipal,
        request: PostMatchRequest,
    ): MatchResponse {

        val match = matchRepository.save(
            Match.of(
                title = request.title,
                matchDate = request.matchDate,
                location = request.location,
                content = request.content,
                matchStatus = false,
                teamId = request.teamId,
                userId = principal.id,
                region = request.region,
            )
        )
        //todo : team 구인공고 상태 변경

        return MatchResponse.from(match)
    }

    @Transactional
    override fun updateMatch(
        principal: UserPrincipal,
        matchId: Long,
        request: UpdateMatchRequest,
    ): MatchResponse {

        val match = matchRepository.findByIdOrNull(matchId)
            ?: throw RuntimeException("") //todo : custom exception

        if (match.userId != principal.id && !principal.authorities.contains(SimpleGrantedAuthority("ROLE_LEADER"))) throw AccessDeniedException(
            "You do not have permission to delete."
        )

        match.updateMatch(request)

        return MatchResponse.from(match)
    }

    @Transactional
    override fun deleteMatch(
        principal: UserPrincipal,
        matchId: Long,
    ): MatchResponse {
        val match = matchRepository.findByIdOrNull(matchId)
            ?: throw RuntimeException("Match not found") //todo : custom exception

        if (match.userId != principal.id && !principal.authorities.contains(SimpleGrantedAuthority("ROLE_LEADER"))) throw AccessDeniedException(
            "You do not have permission to delete."
        )

        match.softDelete()

        return MatchResponse.from(match)
    }

    override fun getMatches(pageable: Pageable): Page<MatchResponse> {
        return matchRepository.findAll(pageable)
            .map { match -> MatchResponse.from(match) }
    }

    override fun getAvailableMatchesAndSort(pageable: Pageable, sortCriteria: SortCriteria): Page<MatchResponse> {
        return matchRepository.getAvailableMatchesAndSort(pageable, sortCriteria)
    }

    override fun getMatchesByRegionAndSort(
        region: Region,
        pageable: Pageable,
        sortCriteria: SortCriteria
    ): Page<MatchResponse> {
        return matchRepository.getMatchesByRegionAndSort(region, pageable, sortCriteria)
    }

    override fun getMatchDetails(
        matchId: Long
    ): MatchResponse {
        return matchRepository.findByIdOrNull(matchId)
            ?.let { match -> MatchResponse.from(match) }
            ?: throw RuntimeException("Match not found") //todo : custom exception
    }

    override fun searchMatch(pageable: Pageable, keyword: String): Page<MatchResponse> {
        return matchRepository.searchMatchByPageableAndKeyword(pageable, keyword)
    }

}
