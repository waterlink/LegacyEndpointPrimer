package ApiEndpoints.IssueVerificationToken

import IssueVerificationToken.SecureTokenSource
import IssueVerificationToken.UseCase
import IssueVerificationToken.VerificationToken
import IssueVerificationToken.VerificationTokenGateway
import doubles.FakeVerificationTokenGateway
import doubles.StubSecureTokenSource
import org.junit.Assert.assertEquals
import org.junit.Test

class EndpointTest {

    private val secureToken = "secure_token"
    private val secureTokenSource = StubSecureTokenSource(secureToken)
    private val verificationTokenGateway = FakeVerificationTokenGateway()
    private val useCase = UseCase(secureTokenSource, verificationTokenGateway)

    private val endpoint = Endpoint(useCase)

    private val deviceId = "device_id"
    private val phoneNumber = "phone_number"
    private val expectedIssuer = "com.tddfellow"

    @Test
    fun issueVerificationToken() {
        assertEquals(IssueVerificationTokenEndpointResponse(expectedIssuer, secureToken),
                endpoint.issueVerificationToken(deviceId, phoneNumber))
    }

    @Test
    fun issueVerificationToken_usesPassedInParametersToIssueToken() {
        endpoint.issueVerificationToken(deviceId, phoneNumber)
        assertEquals(VerificationToken(expectedIssuer, deviceId, phoneNumber, secureToken),
                verificationTokenGateway.persistedTokens.first())
    }

    private val differentDeviceId = "different_device_id"
    private val differentPhoneNumber = "different_phone_number"

    @Test
    fun issueVerificationToken_usesPassedInParametersToIssueToken_whenTheyAreDifferent() {
        endpoint.issueVerificationToken(differentDeviceId, differentPhoneNumber)
        assertEquals(VerificationToken(expectedIssuer, differentDeviceId, differentPhoneNumber, secureToken),
                verificationTokenGateway.persistedTokens.first())
    }

    private val differentSecureToken = "different_secure_token"
    private val secureTokenSourceGeneratingDifferentSecureToken = StubSecureTokenSource(differentSecureToken)
    private val useCaseReturningDifferentSecureToken = UseCase(
            secureTokenSourceGeneratingDifferentSecureToken,
            verificationTokenGateway)

    private val endpointReturningDifferentSecureToken = Endpoint(useCaseReturningDifferentSecureToken)

    @Test
    fun issueVerificationToken_whenUseCaseReturnsDifferentSecureToken() {
        assertEquals(IssueVerificationTokenEndpointResponse(expectedIssuer, differentSecureToken),
                endpointReturningDifferentSecureToken.issueVerificationToken(deviceId, phoneNumber))
    }

}
