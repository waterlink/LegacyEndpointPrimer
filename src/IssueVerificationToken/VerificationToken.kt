package IssueVerificationToken

data class VerificationToken(val issuer: String,
                             val deviceId: Any,
                             val phoneNumber: Any,
                             val secureToken: String) {

}