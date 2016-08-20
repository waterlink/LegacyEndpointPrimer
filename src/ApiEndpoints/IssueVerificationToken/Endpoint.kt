package ApiEndpoints.IssueVerificationToken

import ApiEndpoints.Endpoint
import IssueVerificationToken.UseCase

class Endpoint(private val useCase: UseCase) {

    @Endpoint("/api/v1/issueVerificationToken")
    fun issueVerificationToken(deviceId: String, phoneNumber: String): IssueVerificationTokenEndpointResponse {
        val issueVerificationToken = useCase.issueVerificationToken(deviceId, phoneNumber)

        return IssueVerificationTokenEndpointResponse(
                issuer = issueVerificationToken.issuer,
                token = issueVerificationToken.secureToken
        )
    }

}
