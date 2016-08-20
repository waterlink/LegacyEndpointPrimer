package IssueVerificationToken

class UseCase(private val secureTokenSource: SecureTokenSource,
              private val verificationTokenGateway: VerificationTokenGateway) {

    fun issueVerificationToken(deviceId: String, phoneNumber: String): VerificationToken {
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