package IssueVerificationToken

class UseCase(private val secureTokenSource: SecureTokenSource,
              private val verificationTokenGateway: VerificationTokenGateway) {

    fun issueVerificationToken(deviceId: String, phoneNumber: String, tokenIssuer: TokenIssuer): VerificationToken {
        val verificationToken = VerificationToken(
                issuer = tokenIssuer.getName(),
                deviceId = deviceId,
                phoneNumber = phoneNumber,
                secureToken = secureTokenSource.generateToken()
        )

        verificationTokenGateway.persist(verificationToken)
        return verificationToken
    }

}