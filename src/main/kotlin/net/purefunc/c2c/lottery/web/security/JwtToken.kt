package net.purefunc.c2c.lottery.web.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import net.purefunc.c2c.lottery.ext.randomUUID
import java.util.Date

class JwtToken(
    private val code: String,
) {

    companion object {
        private const val ISSUER = "c2c-lottery-service"
        private const val SECRET = "4da44bf91de842379dc82c10788947f9fbfe099d669c45c5ba1137fc559b97da"

        fun generate(
            subject: String,
            issueAt: Long,
            lifetimeDay: Int,
            lifetimeHour: Int,
            lifetimeMinute: Int,
            lifetimeSecond: Int,
        ) = (((24L * 60L * 60L * 1000L) * lifetimeDay) +
                ((60L * 60L * 1000L) * lifetimeHour) +
                ((60L * 1000L) * lifetimeMinute) +
                ((1000L) * lifetimeSecond)).let {
            Jwts.builder()
                .setId(randomUUID())
                .setIssuer(ISSUER)
                .setSubject(subject)
                .setAudience(null)
                .setIssuedAt(Date(issueAt))
                .setNotBefore(Date(issueAt))
                .setExpiration(Date(issueAt + it))
                .signWith(Keys.hmacShaKeyFor(SECRET.toByteArray()), SignatureAlgorithm.HS512)
                .compact()
        }
    }

    fun retrieveSubject() =
        Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(SECRET.toByteArray()))
            .build()
            .parseClaimsJws(code)
            .body
            .subject!!
}