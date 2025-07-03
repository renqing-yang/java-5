package week5;

/**
 * how do you secure your application / rest endpoint / server ..
 * what is https (flow of https)
 * what is certificate
 * what is Oauth2.0
 * what is open id
 * how do you use spring security
 * what / why jwt
 * what is CORS
 * what is CSRF
 * what is sql injection
 * diff symmetric key and asymmetric key
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 * plain text
 * encoding + decoding
 * encryption + decryption
 *   1. symmetric
 *          secret key
 *   2. asymmetric
 *          public key
 *          private key
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 * HTTPS = HTTP + SSL/TLS
 *
 *          client              -hi>               server [hold private key]
 *                <- certificate[finger print / public key]
 *
 *                  -> public key[random string] ->
 *
 *                     generate key based on random string
 *
 *                    <-     hash(string)  <-
 *
 *                      symmetric key [data]
 *
 *  *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 * Authentication : identity verification -> 401
 *  1. username
 *  2. password (hash)
 *  3. email / phone verification
 *
 *  *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 * Authorization: role  -> 403
 *      JWT: header.payload.signature
 *          payload: userid, exp time, role, ...
 *          signature = encrypted[header.payload]
 *
 *      JWT = encode(header.payload.encrypt(header.payload))
 *      OAuth 2.0
 *           implicit
 *                  client
 *                  |
 *               api gateway -  security service
 *                 |
 *               servers
 *
 *              1. user login -> success -> get a jwt from security service
 *              2. user save jwt token to http-only cookie
 *              3. user send request to fetch data
 *                  frontend code will put token in header "Authorization"
 *              4. security service verify your jwt
 *                  ..
 *
 *           explicit
 *
 *                  client  -  3rd party login
 *                   |
 *                  my app
 *
 *             1. click google login -> frontend page redirected to google login
 *             2. google login success -> redirect to a url?access_code
 *             3. frontend send access_code to my app
 *             4. my app bring client id , secret , access_code to google security center to get access token
 *
 *      OpenId
 *          1. id token : authentication status (phone, name, ...)
 *          2. access token : role token
 *
 *  /endpoint1  read, write
 *
 *  Role m - m policy(read access for xx endpoint
 *  admin1: [read endpoint1,  write endpoint1]
 *  admin2: [read endpoint1]
 *  *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 * CORS
 *      1. browser -> option /endpoint1 -> server
 *      2. browser <- access control origin / header / method <- server
 *      3. browser send real request ? or just give user error ?
 * CSRF
 * SQL Injection
 *      "select * from xx where username = " + username + " and password = " + password
 *
 *      user input box
 *      username: "xx"
 *      password: "xx; drop table"
 *
 *      "select * from xx where username = xx and password = xx; drop table"
 *
 *      "select * from xx where username = 'xx' and password = 'xx; drop table'"
 *
 * DDOS
 *       *       *       *       *       *       *       *       *       *       *       *       *       *       *
 *
 *       user  <-> filter1 <->  filter2 <->  dispatcher servlet(/*) -> handler mapping -> controller
 *
 * Spring Security flow
 * Authentication
 *  1. "UsernamePasswordAuthenticationFilter" (/login + post)
 *          extract username, password from form request body
 *  2. "AuthenticationProvider" (verify user identity)
 *  3. "DAOAuthenticationProvider"
 *          load user info by using "UserDetailsService"
 *          compare user input and the info from user details service
 *  4. return jwt token to user
 *
 *
 * Authorization
 *  1. write customized JWT filter
 *          get authorization header
 *          verify jwt token
 *          if jwt is valid
 *              save user info into "SecurityContext",  default impl is "ThreadLocal"
 *  2. @PreAuthorize annotation on endpoint/ methods
 *          spring security retrieve user principle from thread local (security context)
 *  3. spring security will clean up security context before user get response
 *
 *
 * TODO
 * 1. config spring security, http security in configuration
 * 2. impl UserDetailsService
 * 3. impl UserDetails
 * TODO Authentication
 * 1. create endpoint /auth/login
 * 2. inject AuthenticationManager to controller
 * 3. compare user input and the data from database
 * 4. configure jwt library
 * 5. return jwt token to user
 * TODO Authorization
 * 1. create customized jwt filter
 * 2. add filter into spring security chain
 * 3. add @PreAuthorize("hasRole[Admin, xx]")
 *
 *
 *
 */