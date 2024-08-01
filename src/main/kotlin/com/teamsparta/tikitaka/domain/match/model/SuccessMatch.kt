package com.teamsparta.tikitaka.domain.match.model

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "success_match")
@SQLRestriction("deleted_at is null")

class SuccessMatch(
    @Column(name = "match_post_id", nullable = false)
    val matchPostId: Long,

    @Column(name = "host_team_Id", nullable = false)
    val hostTeamId: Long,

    @Column(name = "match_application_id", nullable = false)
    val matchApplicationId: Long,

    @Column(name = "guest_team_id", nullable = false)
    val guestTeamId: Long,

    @Column(name = "match_date", nullable = false)
    val matchDate: LocalDateTime,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "deleted_at", nullable = false)
    val deletedAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}