import ApiEndpoints.IssueVerificationToken.Endpoint
import IssueVerificationToken.DummyVerificationTokenGateway
import IssueVerificationToken.SimpleSecureTokenSource
import IssueVerificationToken.UseCase
import spark.Spark

fun main(args: Array<String>) {
    val secureTokenSource = SimpleSecureTokenSource()
    val verificationTokenGateway = DummyVerificationTokenGateway()

    val issueVerificationTokenUseCase = UseCase(secureTokenSource, verificationTokenGateway)
    val issueVerificationTokenEndpoint = Endpoint(issueVerificationTokenUseCase)

    Spark.get("/api/v1/issueVerificationToken") { request, response ->
        issueVerificationTokenEndpoint.issueVerificationToken(
                deviceId = request.queryParams("deviceId"),
                phoneNumber = request.queryParams("phoneNumber")
        )
    }
}
