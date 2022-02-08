package app;

import com.google.gson.Gson;
import io.jooby.Context;
import io.jooby.Cookie;
import io.jooby.MediaType;
import io.jooby.annotations.*;
import io.jooby.exception.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Tag(name = "Controller", description = "Controller for user login and logout")
@Path("/")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final Gson gson = new Gson();

    private final EntityManager entityManager;

    public Controller(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Endpoint for logging in
     *
     * @param loginRequest model object, username and password
     * @param context      the jooby HTTP request context,
     *                     lets you interact with and manipulate the HTTP request and HTTP response
     * @return response object
     */
    @POST("/auth/login")
    @Produces(MediaType.JSON)
    @Consumes(MediaType.JSON)
    public Response login(@RequestBody LoginRequest loginRequest, @ContextParam Context context) {
        logger.info("login request received");
        var user = getUserByUsername(loginRequest.username);
        if (user.getPassword().equals(loginRequest.password)) {
            var cookie = generateCookie(user);
            context.setResponseCookie(cookie);
            return new Response("login success");
        }
        return new Response("login failed");
    }

    /**
     * Obtain user by username from the embedded H2 database
     *
     * @param username the username for user
     * @return user
     */
    private AppUser getUserByUsername(String username) {
        return entityManager.find(AppUser.class, username);
    }

    /**
     * Method to generate a cookie once the user has been authenticated
     *
     * @param appUser the user object
     * @return cookie
     */
    private Cookie generateCookie(AppUser appUser) {
        var header = Jwts.header().setType("JWT");
        var jwt = Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setIssuer("backend-test")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
                .setSubject("backend-test")
                .claim("username", appUser.getUsername())
                .signWith(key)
                .compact();

        var cookie = new Cookie("JWT_COOKIE", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    /**
     * Endpoint for logout
     *
     * @param jwt     the jwt cookie obtained when logged in
     * @param context the jooby HTTP request context,
     *                lets you interact with and manipulate the HTTP request and HTTP response
     * @return response object
     */
    @POST("/auth/logout")
    @Produces(MediaType.JSON)
    public Response logout(@CookieParam("JWT_COOKIE") String jwt, @ContextParam Context context) {
        logger.info("logout request received");
        var cookie = generateExpiredCookie();
        context.setResponseCookie(cookie);
        return new Response("logout success");
    }

    /**
     * Method to generate a cookie that is instantly expired, "overwrites" the cookie obtained when logged in
     *
     * @return the expired cookie
     */
    private Cookie generateExpiredCookie() {
        var header = Jwts.header().setType("JWT");
        header.setType("JWT");
        var now = new Date(System.currentTimeMillis());
        var jwt = Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setIssuer("backend-test")
                .setIssuedAt(now)
                .setExpiration(now)
                .setSubject("backend-test")
                .signWith(key)
                .compact();

        var cookie = new Cookie("JWT_COOKIE", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    /**
     * Model class for a login request
     */
    public static class LoginRequest {
        private final String username;
        private final String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return this.username;
        }

        public String getPassword() {
            return this.password;
        }
    }

    /**
     * Model class for responses
     */
    public static class Response {
        private final String message;

        public Response(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }

    /**
     * Endpoint for getting user information when user is logged in
     *
     * @param jwt the jwt cookie obtained when logged in
     * @return user information JSON
     */
    @GET("/home")
    @Produces(MediaType.JSON)
    public String getUserInfo(@CookieParam("JWT_COOKIE") String jwt) {
        if (isTokenValid(jwt)) {
            var username = getUsernameClaim(jwt);
            var user = getUserByUsername(username);
            return gson.toJson(user);
        }
        throw new UnauthorizedException("not authorized to access user info");
    }

    /**
     * Check if a token is valid (not expired and encoded with the correct key)
     *
     * @param jwt the jwt cookie obtained when logged in
     * @return true if token is valid, otherwise false
     */
    private boolean isTokenValid(String jwt) {
        try {
            var body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return Timestamp.valueOf(LocalDateTime.now())
                    .before(Timestamp.from(Instant.ofEpochSecond((Integer) body.get("exp"))));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the claim from token containing the username of the logged in user
     *
     * @param jwt the jwt cookie obtained when logged in
     * @return The username if username claim present otherwise throws exception
     * @throws UnauthorizedException if no username claim in token
     */
    private String getUsernameClaim(String jwt) throws UnauthorizedException {
        try {
            var body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return getUserByUsername((String) body.get("username")).getUsername();
        } catch (Exception e) {
            throw new UnauthorizedException("not authorized to access user info");
        }
    }
}
