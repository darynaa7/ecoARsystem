package diploma.project.eco_ar.core.domain.validation

import diploma.project.eco_ar.core.R
import diploma.project.eco_ar.core.domain.string.StringProvider

class ValidationChecker(private val stringProvider: StringProvider) {

    val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")

    fun checkName(name: String): ValidationError? {
        val name = name.trim()

        return when {
            name.isEmpty() -> ValidationError(stringProvider.provide(R.string.name_cannot_be_empty))
            else -> null
        }
    }

    fun checkEmail(email: String): ValidationError? {
        val email = email.trim()

        return when {
            email.isEmpty() -> ValidationError(stringProvider.provide(R.string.email_cannot_be_empty))
            !emailRegex.matches(email) -> ValidationError(stringProvider.provide(R.string.your_email_is_not_valid))
            else -> null
        }
    }

    fun checkPassword(password: String): ValidationError? {
        val password = password.trim()

        return when {
            password.isEmpty() -> ValidationError(stringProvider.provide(R.string.password_cannot_be_empty))
            !passwordRegex.matches(password) -> ValidationError(stringProvider.provide(R.string.wrong_password_description))
            else -> null
        }
    }

    fun checkRepeatedPassword(password: String, repeatedPassword: String): ValidationError? {
        val password = password.trim()
        val repeatedPassword = repeatedPassword.trim()

        return when {
            password != repeatedPassword -> ValidationError(stringProvider.provide(R.string.passwords_do_not_match))
            else -> null
        }
    }
}