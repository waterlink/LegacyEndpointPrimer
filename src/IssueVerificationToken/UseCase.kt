package IssueVerificationToken

class UseCase(private val secureTokenSource: SecureTokenSource,
                   private val verificationTokenGateway: VerificationTokenGateway) {

    private val OLD_ISSUER = "com.tddfellow"
    private val NEW_ISSUER = "https://tddfellow.com"
    private val OLD_API_VERSION = "v1"

    fun issueVerificationToken(deviceId: String, phoneNumber: String, apiVersion: String): VerificationToken {
        val verificationToken = VerificationToken(
                issuer = getIssuerFor(apiVersion),
                deviceId = deviceId,
                phoneNumber = phoneNumber,
                secureToken = secureTokenSource.generateToken()
        )

        verificationTokenGateway.persist(verificationToken)
        return verificationToken
    }

    private fun getIssuerFor(apiVersion: String): String {
        if (apiVersion.equals(OLD_API_VERSION)) {
            return OLD_ISSUER
        }
        return NEW_ISSUER
    }

}