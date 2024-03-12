package definingAndCallingFunctions

class User(val id: Int, val name: String, val addr: String)

fun saveUser(user: User) {
    if (user.name.isEmpty()) {
        throw IllegalArgumentException("Can't save user ${user.id}: empty Name")
    }

    if (user.addr.isEmpty()) {
        throw IllegalArgumentException("Can't save user ${user.id}: empty Address")
    }

    // Save user to the database
}

fun saveUserWithLocalFunction(user: User) {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${user.id}: empty $fieldName")
        }
    }

    validate(user.name, "Name")
    validate(user.addr, "Address")

    // Save user to the database
}

fun main() {
    val user = User(1, "", "Seoul")
    saveUser(user)
}
