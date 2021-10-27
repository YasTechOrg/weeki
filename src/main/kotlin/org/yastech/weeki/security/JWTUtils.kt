package org.yastech.weeki.security

import io.jsonwebtoken.*
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.yastech.weeki.model.UserDetail
import java.sql.Date
import java.time.LocalDate

@Component
class JWTUtils
{
    private val secretKey: String = "K8VMg24gsCyYmvE8uRxKpFhT82DKFFLQ9tCrvDhPhNd6Z9k6ed"

    fun generateJwtToken(authentication: Authentication): String
    {
        val userPrincipal: UserDetail = authentication.principal as UserDetail

        return Jwts.builder()

            .setSubject(userPrincipal.username)

            .setIssuedAt(Date.valueOf(LocalDate.now()))

            .setExpiration(Date.valueOf(LocalDate.now().plusDays(5)))

            .signWith(SignatureAlgorithm.HS512, secretKey)

            .compact()
    }

    fun getUserNameFromJwtToken(token: String): String
    {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String): Boolean
    {
        return try
        {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken)
            true
        }
        catch (e: SignatureException)
        {
            false
        }
        catch (e: MalformedJwtException)
        {
            false
        }
        catch (e: ExpiredJwtException)
        {
            false
        }
        catch (e: UnsupportedJwtException)
        {
            false
        }
        catch (e: IllegalArgumentException)
        {
            false
        }
    }
}