package IssueVerificationToken

import doubles.FakeVerificationTokenGateway
import doubles.StubSecureTokenSource
import org.junit.Assert.assertEquals
import org.junit.Test

class UseCaseTest {

    private val preparedSecureToken = "ytE7pqVKgdCQrrtDWxT6eOGqroRQO101sHsTAXo6nOXt8SXPw7Qedopbovvf"
    private val secureTokenSource = StubSecureTokenSource(preparedSecureToken)

    private val deviceId = "66251624-1a7c-402a-95f5-f3e807020dc4"
    private val phoneNumber = "+999 123 45 678 912"

    private val expectedIssuer = "com.tddfellow"

    private val fakeVerificationTokenGateway = FakeVerificationTokenGateway()
    private val useCase = UseCase(secureTokenSource, fakeVerificationTokenGateway)

    @Test
    fun issueVerificationToken_returnsCorrectToken() {
        assertEquals(VerificationToken(expectedIssuer, deviceId, phoneNumber, preparedSecureToken),
                useCase.issueVerificationToken(deviceId, phoneNumber))
    }

    @Test
    fun issueVerificationToken_persistsCreatedToken() {
        useCase.issueVerificationToken(deviceId, phoneNumber)
        assertEquals(listOf(VerificationToken(expectedIssuer, deviceId, phoneNumber, preparedSecureToken)),
                fakeVerificationTokenGateway.persistedTokens)
    }

    private val differentDeviceId = "6144e9e9-b3f5-48a9-afa1-8a4bdc3b377a"

    @Test
    fun issueVerificationToken_whenDeviceIdIsDifferent_returnsCorrectToken() {
        assertEquals(VerificationToken(expectedIssuer, differentDeviceId, phoneNumber, preparedSecureToken),
                useCase.issueVerificationToken(differentDeviceId, phoneNumber))
    }

    @Test
    fun issueVerificationToken_whenDeviceIdIsDifferent_persistsCreatedToken() {
        useCase.issueVerificationToken(differentDeviceId, phoneNumber)
        assertEquals(listOf(VerificationToken(expectedIssuer, differentDeviceId, phoneNumber, preparedSecureToken)),
                fakeVerificationTokenGateway.persistedTokens)
    }

    private val differentPhoneNumber = "+999 987 65 432 123"

    @Test
    fun issueVerificationToken_whenPhoneNumberIsDifferent_returnsCorrectToken() {
        assertEquals(VerificationToken(expectedIssuer, deviceId, differentPhoneNumber, preparedSecureToken),
                useCase.issueVerificationToken(deviceId, differentPhoneNumber))
    }

    @Test
    fun issueVerificationToken_whenPhoneNumberIsDifferent_persistsCreatedToken() {
        useCase.issueVerificationToken(deviceId, differentPhoneNumber)
        assertEquals(listOf(VerificationToken(expectedIssuer, deviceId, differentPhoneNumber, preparedSecureToken)),
                fakeVerificationTokenGateway.persistedTokens)
    }

    private val differentPreparedSecureToken = "LkraGvXok9tlKlfxWGlGbHFIIEPgqDMUbP0eKaiM9VNKtTTVMu"
    private val secureTokenSourceReturningDifferentToken = StubSecureTokenSource(differentPreparedSecureToken)
    private val useCaseGeneratingDifferentToken = UseCase(
            secureTokenSourceReturningDifferentToken,
            fakeVerificationTokenGateway)

    @Test
    fun issueVerificationToken_whenSecureTokenSourceReturnsDifferentToken_returnsCorrectToken() {
        assertEquals(VerificationToken(expectedIssuer, deviceId, phoneNumber, differentPreparedSecureToken),
                useCaseGeneratingDifferentToken.issueVerificationToken(deviceId, phoneNumber))
    }

    @Test
    fun issueVerificationToken_whenSecureTokenSourceReturnsDifferentToken_persistsCreatedToken() {
        useCaseGeneratingDifferentToken.issueVerificationToken(deviceId, phoneNumber)
        assertEquals(listOf(VerificationToken(expectedIssuer, deviceId, phoneNumber, differentPreparedSecureToken)),
                fakeVerificationTokenGateway.persistedTokens)
    }
}

