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

    private val oldIssuer = "com.tddfellow"

    private val fakeVerificationTokenGateway = FakeVerificationTokenGateway()
    private val useCase = UseCase(secureTokenSource, fakeVerificationTokenGateway)

    private val oldApiVersion = "v1"

    @Test
    fun issueVerificationToken_returnsCorrectToken() {
        assertEquals(VerificationToken(oldIssuer, deviceId, phoneNumber, preparedSecureToken),
                useCase.issueVerificationToken(deviceId, phoneNumber, oldApiVersion))
    }

    @Test
    fun issueVerificationToken_persistsCreatedToken() {
        useCase.issueVerificationToken(deviceId, phoneNumber, oldApiVersion)
        assertEquals(listOf(VerificationToken(oldIssuer, deviceId, phoneNumber, preparedSecureToken)),
                fakeVerificationTokenGateway.persistedTokens)
    }

    private val newApiVersion = "v2"
    private val newIssuer = "https://tddfellow.com"

    @Test
    fun issueVerificationToken_generatesNewIssuer_whenApiVersionIsNew() {
        assertEquals(VerificationToken(newIssuer, deviceId, phoneNumber, preparedSecureToken),
                useCase.issueVerificationToken(deviceId, phoneNumber, newApiVersion))
    }

    private val differentDeviceId = "6144e9e9-b3f5-48a9-afa1-8a4bdc3b377a"

    @Test
    fun issueVerificationToken_whenDeviceIdIsDifferent_returnsCorrectToken() {
        assertEquals(VerificationToken(oldIssuer, differentDeviceId, phoneNumber, preparedSecureToken),
                useCase.issueVerificationToken(differentDeviceId, phoneNumber, oldApiVersion))
    }

    @Test
    fun issueVerificationToken_whenDeviceIdIsDifferent_persistsCreatedToken() {
        useCase.issueVerificationToken(differentDeviceId, phoneNumber, oldApiVersion)
        assertEquals(listOf(VerificationToken(oldIssuer, differentDeviceId, phoneNumber, preparedSecureToken)),
                fakeVerificationTokenGateway.persistedTokens)
    }

    private val differentPhoneNumber = "+999 987 65 432 123"

    @Test
    fun issueVerificationToken_whenPhoneNumberIsDifferent_returnsCorrectToken() {
        assertEquals(VerificationToken(oldIssuer, deviceId, differentPhoneNumber, preparedSecureToken),
                useCase.issueVerificationToken(deviceId, differentPhoneNumber, oldApiVersion))
    }

    @Test
    fun issueVerificationToken_whenPhoneNumberIsDifferent_persistsCreatedToken() {
        useCase.issueVerificationToken(deviceId, differentPhoneNumber, oldApiVersion)
        assertEquals(listOf(VerificationToken(oldIssuer, deviceId, differentPhoneNumber, preparedSecureToken)),
                fakeVerificationTokenGateway.persistedTokens)
    }

    private val differentPreparedSecureToken = "LkraGvXok9tlKlfxWGlGbHFIIEPgqDMUbP0eKaiM9VNKtTTVMu"
    private val secureTokenSourceReturningDifferentToken = StubSecureTokenSource(differentPreparedSecureToken)
    private val useCaseGeneratingDifferentToken = UseCase(
            secureTokenSourceReturningDifferentToken,
            fakeVerificationTokenGateway)

    @Test
    fun issueVerificationToken_whenSecureTokenSourceReturnsDifferentToken_returnsCorrectToken() {
        assertEquals(VerificationToken(oldIssuer, deviceId, phoneNumber, differentPreparedSecureToken),
                useCaseGeneratingDifferentToken.issueVerificationToken(deviceId, phoneNumber, oldApiVersion))
    }

    @Test
    fun issueVerificationToken_whenSecureTokenSourceReturnsDifferentToken_persistsCreatedToken() {
        useCaseGeneratingDifferentToken.issueVerificationToken(deviceId, phoneNumber, oldApiVersion)
        assertEquals(listOf(VerificationToken(oldIssuer, deviceId, phoneNumber, differentPreparedSecureToken)),
                fakeVerificationTokenGateway.persistedTokens)
    }
}

