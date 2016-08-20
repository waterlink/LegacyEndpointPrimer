package IssueVerificationToken

data class VerificationToken(val issuer: String,
                             val deviceId: String,
                             val phoneNumber: String,
                             val secureToken: String) {

}