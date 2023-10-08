package cn.exsolo.authserver.utils;

import cn.exsolo.auth.utils.AccessUtil;
import cn.exsolo.comm.utils.TsUtil;
import cn.hutool.core.lang.Pair;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.tuple.Triple;

import javax.crypto.KeyGenerator;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;

/**
 * @author prestolive
 * @date 2021/5/24
 **/
public class RefreshTokenUtil {

    public static String createRefreshToken(HttpServletRequest request, String userId, String loginCode,
                                            String ticketId, int expireTimes, String secretKey) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, expireTimes);
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Pair<String, String> uaInfo = AccessUtil.getUa(request);
        return JWT.create()
                .withClaim("code", loginCode)
                .withClaim("id", userId)
                .withClaim("ticket", ticketId)
                .withClaim("ts", TsUtil.getTimestamp())
                .withClaim("ip", AccessUtil.getRequestClientIP(request))
                .withClaim("ua", uaInfo.getValue())
                .withClaim("platform", uaInfo.getKey())
                .withExpiresAt(cal.getTime()).sign(algorithm);
    }

    public static Triple<String,String,String> getUserInfo(String refreshToken){
        DecodedJWT jwt = JWT.decode(refreshToken);
        String ticket = getTokenProp(jwt,"ticket");
        String id = getTokenProp(jwt,"id");
        String code = getTokenProp(jwt,"code");
        return Triple.of(ticket,id,code);
    }

    public static void verifyRefreshToken(String token,String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
    }

    private static String getTokenProp(DecodedJWT jwt, String field) {
        Claim claim = jwt.getClaim(field);
        if(claim!=null){
            return claim.asString();
        }
        return "";
    }
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        String encodedKey = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        System.out.println("HmacSHA256密钥：");
        System.out.println(encodedKey);
    }


}
