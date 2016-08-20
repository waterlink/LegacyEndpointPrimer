package doubles

import IssueVerificationToken.SecureTokenSource

class StubSecureTokenSource(private val preparedSecureToken: String) : SecureTokenSource {

    override fun generateToken() = preparedSecureToken

}