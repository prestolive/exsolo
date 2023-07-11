package cn.exsolo.auth.utils;

import cn.exsolo.auth.bo.TokenInfo;
import cn.exsolo.comm.utils.TsUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;

/**
 * @author prestolive
 * @date 2023/5/24
 **/
public class TokenUtil {

    public static TokenInfo createTokenInfo(
                                     String userId,
                                     String loginCode,
                                     String userName,
                                     String ticket,
                                     String ip,String ua){
        TokenInfo token = new TokenInfo();
        token.setUserId(userId);
        token.setLoginCode(loginCode);
        token.setUserName(userName);
        token.setTicket(ticket);
        token.setTs(TsUtil.getTimestamp());
        token.setIp(ip);
//        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        token.setUa(ua);
        return token;
    }

    /**
     *
     * @param tokenInfo
     * @param expireTimes
     * @param privateKey
     * @return
     */
    public static String crateToken(TokenInfo tokenInfo,int expireTimes,String privateKey){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MILLISECOND,expireTimes);
        Algorithm algorithm =  Algorithm.RSA256(null,(RSAPrivateKey) getPrivateKeyByStr(privateKey));
        return JWT.create()
                .withClaim("code",tokenInfo.getLoginCode())
                .withClaim("name",tokenInfo.getUserName())
                .withClaim("id",tokenInfo.getUserId())
                .withClaim("ip",tokenInfo.getIp())
                .withClaim("ticket",tokenInfo.getTicket())
                .withClaim("ts",tokenInfo.getTs())
                .withClaim("ua",tokenInfo.getUa())
                .withExpiresAt(cal.getTime()).sign(algorithm);
    }

//    public static String sign(TokenInfo info, long expire_time) {
//        Date date = new Date(expire_time);
//        Algorithm algorithm =  Algorithm.RSA256("","")
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        // 附带username信息
//        return JWT.create()
//                .withClaim("id", info.getId())
//                .withClaim("code", info.getCode())
//                .withClaim("name", info.getName())
//                .withClaim("ticket", UUID.randomUUID().toString().replace("-",""))//随机凭据，如果有些系统不希望暴露id，可以用
//                .withClaim("ts", dtf.format(LocalTime.now()))//签发时间
//                .withClaim("iss","crm-prod")//签发人 字串、服务器名称、服务器ip都可以
//                .withExpiresAt(date)
//                .sign(algorithm);
//    }

    public static void verify(String token,String publicKeyStr){
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) getPublicKeyByStr(publicKeyStr),null);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
    }

    public static String getUserCode(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("code").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

//
//    public static String getIp(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//            if (ip.equals("127.0.0.1")) {
//                //根据网卡取本机配置的IP
//                InetAddress inet = null;
//                try {
//                    inet = InetAddress.getLocalHost();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                ip = inet.getHostAddress();
//            }
//        }
//        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//        if (ip != null && ip.length() > 15) {
//            if (ip.indexOf(",") > 0) {
//                ip = ip.substring(0, ip.indexOf(","));
//            }
//        }
//        return ip;
//    }

    private static PublicKey getPublicKeyByStr(String publicKeyStr) {
        try {
            byte[] keyBytes = Base64.getMimeDecoder().decode(publicKeyStr.getBytes());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }


    private static PrivateKey getPrivateKeyByStr(String privateKey) {
        try {
            byte[] keyBytes = Base64.getMimeDecoder().decode(privateKey.getBytes());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec (keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen;
        keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("privateKey:"+privateKeyStr);
        System.out.println("publicKey:"+publicKeyStr);

        Algorithm algorithm =  Algorithm.RSA256(null,privateKey);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,1);
        String token=  JWT.create().withClaim("name","wuby").withExpiresAt(cal.getTime()).sign(algorithm);
        System.out.println("token:"+token);
        verify(token,publicKeyStr);
//        Algorithm algorithm2 = Algorithm.RSA256(publicKey,null);
//        JWTVerifier verifier = JWT.require(algorithm2).build();
//        DecodedJWT jwt = verifier.verify(token);
        System.out.println("pass");


    }
    /**
     *
     privateKey:MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCLw8reC3VuvmTTribTH+EuQMILH0Y/6z2gafIBsZiQmpSXK106VrVU7d5ULg00/hMt4qzzEC4R9B6/NWmK6TlhC0tZIyYPycj4IjrVnZyGbalINQ/21pTZ5OIZliRVp3PTR8hFb2XnwCTo40tTBXoa17j+LGntRoma9Ed8eSxVNuH+CyoZg3XxyNuF2Of6Gbr6necvUZlvqydGB9YqCh/pTG6DA0yN7SdLD3KVqd+jS6S1Br1JyMY/MeEuZA27/MSCICjJsAU3QwKwmUxJAQw0P//J54nG7vNGHyoqeyM1IVS1gmB6Gs2bKv5WA138Iv9y7iu5rQd6sL/wkLf/Eu1lAgMBAAECggEAWEZsrnwMSssEQdKgAP8EqcMLDB1PsHVy8guYZ8t08fhAW45vDrbPRvoKT57ahprnIDcoR0jz4DEQ7nHc3Hjb3dhVWdJiatxnC5oLEPrAbwN6RPoGwD/duhXfXIvBHTxrYonXC0wF0C9C4LkWzTQLYELaO+dvbAb+SJl7QToDbzoPx4wYp5vR8Px0fyzR0FwCBditmNLhx8RFPzg2YR8SAXPItkccEITP+KqklRA7bDXoHtDua9pUWhzXYCSl9sAyrx2rwsqJ1wU9iCmNBakKJAouha0PCOpR1YFHVAjT9QtLlSfTI9EdoM+sbGHJWE1PM2/Af5t0bnxaCpeXkHRzQQKBgQDQalnYwWFrZnlLHLcyXci8lqzX9hKUhdvCqbQL5p+eGC/tZYDOspI492Ef+lPz3bJL19R2oD02H8GWy9rEWlHeGddwXDqPKXdwHtD8Ak9GoWkgir2fIDULDAYC3WnNQF+VYvmJNwKcnqXv2ATc8ycGhsCkDU5XUySVwBmLwdaFkQKBgQCrrOO6Mz0gXV51ufAfBr+lhkSkUcC7T/R7ap/2XSKV2g48RIeUVxqh8zWdq9yxd3qh2023OUVnQ7Q868wvXdUFUoSoBeoAD2vX88VmRHFdyfq7sstDRVdJ6gNN5bcuvxKgPezDko2MRy26cvJ22sshGXSsDI2qjsnqPAh0a2wwlQJ/P4An3+MLy61tR3V4W+A3ST6fay2Oe7JNQwJx2S5rc7/q2moS1OeTpce50AR1j9f8ex9HnN01yUfjtA5omWrrhsBY4+M19Git+3toMWCCxwJnBYNAQzuM7dC8BCNyGReECAAgB667HP05kdo/oRJ00Yg9Kb7kH7c1bmsZXXux4QKBgGp1C8Krg3xOagUXCJA03yMF4AsFpz6AllryA8ACgS/ryGgXFvwmB53l85mSuYhTTviPxeGY4jDnCx28GyJETZS/VwPapwDijz2bLQ39408FsTYz6VgN59MuCl6D1NRoKGS2AdxFRQF4IiM8pJ6Pjuc3oeIO5sqdQRi/oX6QHscBAoGAZNFWYYapO9ZBFXlxtqTlO/uhYUdlhVMSWBKqmHX2DmcPJ0fCsCGoG0fA4+MRf2w/20XCIYwOCkW7GRLA6WHw8WcUsEk89iix6eedrV5niyQeeK44LKX2tuG5MthDvklIDlpGrOlr/8s+RNOmQAB7eWekxSw6XeWTqlDrdjSAAK0=
     publicKey:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi8PK3gt1br5k064m0x/hLkDCCx9GP+s9oGnyAbGYkJqUlytdOla1VO3eVC4NNP4TLeKs8xAuEfQevzVpiuk5YQtLWSMmD8nI+CI61Z2chm2pSDUP9taU2eTiGZYkVadz00fIRW9l58Ak6ONLUwV6Gte4/ixp7UaJmvRHfHksVTbh/gsqGYN18cjbhdjn+hm6+p3nL1GZb6snRgfWKgof6UxugwNMje0nSw9ylanfo0uktQa9ScjGPzHhLmQNu/zEgiAoybAFN0MCsJlMSQEMND//yeeJxu7zRh8qKnsjNSFUtYJgehrNmyr+VgNd/CL/cu4rua0HerC/8JC3/xLtZQIDAQAB
     token:eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJuYW1lIjoid3VieSIsImV4cCI6MTY4NTA5MDI2OH0.CoJiM4FuleLPlXVJspPBrtEiQABzcsyR7GNI981fFv86aLdcvs3WOxnMp6VVz1Gl0fVHe9NEgXd62U5oNpY0jb5r0ahLISWKuDxafhSfD3JIzuCT2pf3MadQtZVmWa6BhUdzUaMqNQyC98F8Kw4J3sF9omSEYrtkcfSq-u9uS2Yl-DAf5h4Bre7xwJgZ2rZmCRB1_rAHxAYS-OA0zpKPSMO7QN4WILRB_OxwFZv_HVjSVGyqRvOXviSjjHOIAQ5Ybrl4vkjsbDyQrnkU3rKswKSbYDGPb0JxO1qDOCDmLCc8AH7eZMoBoxRWjCY9nGzAuxb7SQREDFvIWD9LNcKpzQ

     *
     *
     */
}
