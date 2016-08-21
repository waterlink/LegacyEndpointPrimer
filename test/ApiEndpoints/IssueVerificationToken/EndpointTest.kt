package ApiEndpoints.IssueVerificationToken

import IssueVerificationToken.OldTokenIssuer
import IssueVerificationToken.UrlTokenIssuer
import IssueVerificationToken.UseCase
import IssueVerificationToken.VerificationToken
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
    private val oldIssuer = "com.tddfellow"

    private val oldTokenIssuer = OldTokenIssuer()

    @Test
    fun issueVerificationToken() {
        assertEquals(IssueVerificationTokenEndpointResponse(oldIssuer, secureToken),
                endpoint.issueVerificationToken(deviceId, phoneNumber, oldTokenIssuer))
    }

    @Test
    fun issueVerificationToken_usesPassedInParametersToIssueToken() {
        endpoint.issueVerificationToken(deviceId, phoneNumber, oldTokenIssuer)
        assertEquals(VerificationToken(oldIssuer, deviceId, phoneNumber, secureToken),
                verificationTokenGateway.persistedTokens.first())
    }

    private val differentDeviceId = "different_device_id"
    private val differentPhoneNumber = "different_phone_number"

    @Test
    fun issueVerificationToken_usesPassedInParametersToIssueToken_whenTheyAreDifferent() {
        endpoint.issueVerificationToken(differentDeviceId, differentPhoneNumber, oldTokenIssuer)
        assertEquals(VerificationToken(oldIssuer, differentDeviceId, differentPhoneNumber, secureToken),
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
        assertEquals(IssueVerificationTokenEndpointResponse(oldIssuer, differentSecureToken),
                endpointReturningDifferentSecureToken.issueVerificationToken(deviceId, phoneNumber, oldTokenIssuer))
    }

    private val urlTokenIssuer = UrlTokenIssuer()
    private val newIssuer = "https://tddfellow.com"

    @Test
    fun issueVerificationToken_whenNewerVersionIsProvided_returnsTokenWithNewIssuer() {
        assertEquals(IssueVerificationTokenEndpointResponse(newIssuer, differentSecureToken),
                endpointReturningDifferentSecureToken.issueVerificationToken(deviceId, phoneNumber, urlTokenIssuer))
    }

}
