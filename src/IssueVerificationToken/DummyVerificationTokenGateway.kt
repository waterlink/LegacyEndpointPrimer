package IssueVerificationToken

class DummyVerificationTokenGateway : VerificationTokenGateway {
    override fun persist(verificationToken: VerificationToken) {
        System.out.printf("Persisted verification token: %s\n", verificationToken)
    }
}