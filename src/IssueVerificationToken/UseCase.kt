package IssueVerificationToken

open class UseCase(private val secureTokenSource: SecureTokenSource,
                   private val verificationTokenGateway: VerificationTokenGateway) {

    open fun issueVerificationToken(deviceId: String, phoneNumber: String): VerificationToken {
        val verificationToken = VerificationToken(
                issuer = "com.tddfellow",
                deviceId = deviceId,
                phoneNumber = phoneNumber,
                secureToken = secureTokenSource.generateToken()
        )

        verificationTokenGateway.persist(verificationToken)
        return verificationToken
    }

}