package IssueVerificationToken

interface SecureTokenSource {

    fun generateToken(): String

}