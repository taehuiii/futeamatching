futeamatching
├── gradle
├── src
│   ├── main
│   │   ├── kotlin
│   │   │   └── com
│   │   │       └── teamsparta
│   │   │           └── tikitaka
│   │   │               ├── api
│   │   │               │   └── weather
│   │   │               │       ├── controller
│   │   │               │       ├── dto
│   │   │               │       └── service
│   │   │               ├── domain
│   │   │               │   ├── common
│   │   │               │   │   ├── baseentity
│   │   │               │   │   ├── exception
│   │   │               │   │   └── util
│   │   │               │   ├── evaluation
│   │   │               │   │   ├── controller
│   │   │               │   │   ├── dto
│   │   │               │   │   ├── model
│   │   │               │   │   ├── repository
│   │   │               │   │   └── service
│   │   │               │   ├── match
│   │   │               │   │   ├── controller
│   │   │               │   │   ├── dto
│   │   │               │   │   ├── model
│   │   │               │   │   ├── repository
│   │   │               │   │   └── service
│   │   │               │   ├── recruitment
│   │   │               │   │   ├── controller
│   │   │               │   │   ├── dto
│   │   │               │   │   ├── model
│   │   │               │   │   ├── repository
│   │   │               │   │   └── service
│   │   │               │   ├── team
│   │   │               │   │   ├── controller
│   │   │               │   │   ├── dto
│   │   │               │   │   ├── model
│   │   │               │   │   ├── repository
│   │   │               │   │   └── service
│   │   │               │   └── users
│   │   │               ├── infra
│   │   │               │   ├── aop
│   │   │               │   │   ├── StopWatch
│   │   │               │   │   └── StopWatchAspect
│   │   │               │   ├── oauth
│   │   │               │   │   ├── kakao
│   │   │               │   │   ├── naver
│   │   │               │   │   └── oauthlogin
│   │   │               │   ├── querydsl
│   │   │               │   │   └── QueryDslSupport
│   │   │               │   ├── redis
│   │   │               │   │   └── RedisConfig
│   │   │               │   ├── security
│   │   │               │   │   ├── config
│   │   │               │   │   │   ├── EmailConfig
│   │   │               │   │   │   ├── PasswordEncoderConfig
│   │   │               │   │   │   ├── SecurityConfig
│   │   │               │   │   │   └── WebConfig
│   │   │               │   │   ├── jwt
│   │   │               │   │   │   ├── CustomAccessDeniedHandler
│   │   │               │   │   │   ├── CustomAuthenticationEntryPoint
│   │   │               │   │   │   ├── CustomPreAuthorize
│   │   │               │   │   │   └── UserPrincipal
│   │   │               │   │   └── swagger
│   │   │               │   │       └── SwaggerConfig
│   │   │               └── TikiTakaApplication.kt
│   └── resources
│       ├── application.yml
│       └── logback.xml
└── build.gradle.kts
