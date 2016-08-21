package ApiEndpoints.IssueVerificationToken

import IssueVerificationToken.OldTokenIssuer
import IssueVerificationToken.TokenIssuer
import IssueVerificationToken.UseCase

class Endpoint(private val useCase: UseCase) {

    fun issueVerificationToken(deviceId: String, phoneNumber: String, tokenIssuer: TokenIssuer): IssueVerificationTokenEndpointResponse {
        val issueVerificationToken = useCase.issueVerificationToken(deviceId, phoneNumber, tokenIssuer)

        return IssueVerificationTokenEndpointResponse(
                issuer = issueVerificationToken.issuer,
                token = issueVerificationToken.secureToken
        )
    }

}

