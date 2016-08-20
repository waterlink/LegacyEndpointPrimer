package doubles

import IssueVerificationToken.VerificationToken
import IssueVerificationToken.VerificationTokenGateway

class FakeVerificationTokenGateway : VerificationTokenGateway {

    val persistedTokens: MutableList<VerificationToken> = mutableListOf()

    override fun persist(verificationToken: VerificationToken) {
        persistedTokens.add(verificationToken)
    }

}