package ApiEndpoints.IssueVerificationToken

import IssueVerificationToken.UseCase

class Endpoint(private val useCase: UseCase) {

    fun issueVerificationToken(deviceId: String, phoneNumber: String): IssueVerificationTokenEndpointResponse {
        val issueVerificationToken = useCase.issueVerificationToken(deviceId, phoneNumber)

        return IssueVerificationTokenEndpointResponse(
                issuer = issueVerificationToken.issuer,
                token = issueVerificationToken.secureToken
        )
    }

}
