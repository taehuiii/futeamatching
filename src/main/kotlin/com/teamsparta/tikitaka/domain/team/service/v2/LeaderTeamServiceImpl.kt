package com.teamsparta.tikitaka.domain.team.service.v2

import com.teamsparta.tikitaka.domain.common.exception.ModelNotFoundException
import com.teamsparta.tikitaka.domain.team.dto.request.ReassignRoleRequest
import com.teamsparta.tikitaka.domain.team.dto.response.DelegateLeaderResponse
import com.teamsparta.tikitaka.domain.team.dto.response.ReassignRoleResponse
import com.teamsparta.tikitaka.domain.team.dto.response.RemoveMemberResopnse
import com.teamsparta.tikitaka.domain.team.model.teammember.TeamRole
import com.teamsparta.tikitaka.domain.team.repository.teamMember.TeamMemberRepository
import com.teamsparta.tikitaka.domain.users.repository.UsersRepository
import com.teamsparta.tikitaka.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LeaderTeamServiceImpl(
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UsersRepository,
) : LeaderTeamService {

    @Transactional
    override fun reassignRole(
        principal: UserPrincipal,
        teamMemberId: Long,
        request: ReassignRoleRequest,
    ): ReassignRoleResponse {

        val leader = teamMemberRepository.findByUserId(principal.id)

        if (leader.teamRole != TeamRole.LEADER) {
            throw IllegalArgumentException("Only the current leader can reassign roles")
        }

        val teamMember = teamMemberRepository.findByIdOrNull(teamMemberId)
            ?: throw ModelNotFoundException("team member", teamMemberId)


        when (request.role) {
            TeamRole.SUB_LEADER -> {
                if (teamMember.teamRole == TeamRole.MEMBER) {
                    teamMember.teamRole = TeamRole.SUB_LEADER
                } else {
                    throw IllegalArgumentException(" The team member is already a sub-leader ")
                }
            }

            TeamRole.MEMBER -> {
                if (teamMember.teamRole == TeamRole.SUB_LEADER) {
                    teamMember.teamRole = TeamRole.MEMBER
                } else {
                    throw IllegalArgumentException(" The team member is already a member ")
                }
            }

            else -> throw IllegalArgumentException("Invalid role for reassignment: ${request.role}")
        }

        val user = userRepository.findById(teamMember.userId)
            .orElseThrow { ModelNotFoundException("user", teamMember.userId) }

        user.updateUserRole(principal, teamMember.teamRole)

        return ReassignRoleResponse.from(teamMember)
    }


    @Transactional
    override fun delegateLeader(principal: UserPrincipal, teamMemberId: Long): DelegateLeaderResponse {
        val currentLeader = teamMemberRepository.findByUserId(principal.id)

        if (currentLeader.teamRole != TeamRole.LEADER) {
            throw IllegalArgumentException("Only the current leader can delegate the leader role")
        }

        val newLeader = teamMemberRepository.findByIdOrNull(teamMemberId)
            ?: throw ModelNotFoundException("new Leader", teamMemberId)

        if (newLeader.teamRole != TeamRole.SUB_LEADER) {
            throw IllegalArgumentException("The new leader must be a sub-leader")
        }

        currentLeader.teamRole = TeamRole.MEMBER
        newLeader.teamRole = TeamRole.LEADER

        val userCurrent = userRepository.findById(currentLeader.userId)
            .orElseThrow { ModelNotFoundException("user", currentLeader.userId) }

        userCurrent.updateUserRole(principal, currentLeader.teamRole)

        val userNew = userRepository.findById(newLeader.userId)
            .orElseThrow { ModelNotFoundException("user", newLeader.userId) }

        userNew.updateUserRole(principal, newLeader.teamRole)

        return DelegateLeaderResponse.from(newLeader)
    }

    @Transactional
    override fun removeMember(principal: UserPrincipal, teamMemberId: Long): RemoveMemberResopnse {
        val leader = teamMemberRepository.findByUserId(principal.id)

        if (leader.teamRole != TeamRole.LEADER) {
            throw IllegalArgumentException("Only the current leader can remove members")
        }

        val teamMember = teamMemberRepository.findByIdOrNull(teamMemberId)
            ?: throw ModelNotFoundException("team member", teamMemberId)

        teamMember.softDelete()

        return RemoveMemberResopnse.from(teamMember)
    }
}
