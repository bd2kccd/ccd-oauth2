# ccd-oauth2
An OAuth 2.0 module for CCD applications

### Dependencies
ccd-db-0.6.2

### Requirement to Install
- Oracle Java 8 ([http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html))
- Maven 3.x - ([https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi))

### Install
`mvn clean install`

### To add to your Spring application 
- Add the following to your main class    
`@Import({CCDOAuth2.class})`

### Custom Resource Server
The _resource server_ filters requests by authentication. ccd-security-oauth2 uses the default resource server configuration, which does the following:
- Disallows access to any resource endpoints by unauthenticated clients
- Allows access to endpoints provided by the Spring Security OAuth2 framework

The URLs provided by the Spring Security OAuth2 framework are as follows:
- `/oauth/authorize` the authorization endpoint
- `/oauth/token` the token endpoint
- `/oauth/confirm_access` used by user to post approvals for grants
- `/oauth/error` used to render errors in the authorization server
- `/oauth/check_token` used by Resource Servers to decode access tokens
- `/oauth/token_key` exposes public key for token verification if using JWT tokens

If this configuration is undesirable, you may implement your own `ResourceServerConfigurerAdapter` with the `@EnableResourceServer` annotation

### Example
Here is a simple authentication example using curl.

```
# request token
$ curl -u client:secret http://localhost:8080/oauth/token -d grant_type=password -d username=user -d password=pass
# a JSON formatted response with token
{"access_token":"e9dfafa4-efa3-4d0b-8c5d-e393508506eb","token_type":"bearer","refresh_token":"32cc00a1-66a9-4666-aa0c-4de75babfa95","expires_in":43199,"scope":"read write"}
# future requests for resources use this token
$ curl -H "Authorization: Bearer e9dfafa4-efa3-4d0b-8c5d-e393508506eb" http://localhost:8080/
```

When the access token expires, a request for a new one can be made using the refresh token
```
# refresh token
$ curl -u client:secret http://localhost:8080/oauth/token -d grant_type=refresh_token -d refresh_token=32cc00a1-66a9-4666-aa0c-4de75babfa95
# a new access token is provided 
{"access_token":"93d1ce4c-522b-45b0-9f04-dd73d7ff5304","token_type":"bearer","refresh_token":"32cc00a1-66a9-4666-aa0c-4de75babfa95","expires_in":42188,"scope":"read write"}
```
