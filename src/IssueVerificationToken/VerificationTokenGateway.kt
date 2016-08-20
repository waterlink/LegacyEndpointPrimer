package IssueVerificationToken

interface VerificationTokenGateway {

    fun persist(verificationToken: VerificationToken)

}