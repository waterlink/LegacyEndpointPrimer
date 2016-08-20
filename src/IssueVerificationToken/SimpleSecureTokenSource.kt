package IssueVerificationToken

import java.util.*

class SimpleSecureTokenSource : SecureTokenSource {

    override fun generateToken(): String {
        return UUID.randomUUID().toString()
    }

}