package ca.doophie.doophrametestapp.models

enum class UserTestThing {
    THINGA,
    THINGB,
    THINGC;
}

data class User (
    var email: String = "",
    var password: String = "",
    var thing: UserTestThing = UserTestThing.THINGA
)

