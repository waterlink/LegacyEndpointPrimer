import ApiEndpoints.IssueVerificationToken.Endpoint
import IssueVerificationToken.*
import spark.Spark

fun main(args: Array<String>) {
    val secureTokenSource = SimpleSecureTokenSource()
    val verificationTokenGateway = DummyVerificationTokenGateway()

    val issueVerificationTokenUseCase = UseCase(secureTokenSource, verificationTokenGateway)
    val issueVerificationTokenEndpoint = Endpoint(issueVerificationTokenUseCase)

    Spark.get("/api/v1/issueVerificationToken") { request, response ->
        issueVerificationTokenEndpoint.issueVerificationToken(
                deviceId = request.queryParams("deviceId"),
                phoneNumber = request.queryParams("phoneNumber"),
                tokenIssuer = OldTokenIssuer()
        )
    }

    Spark.get("/api/v2/issueVerificationToken") { request, response ->
        issueVerificationTokenEndpoint.issueVerificationToken(
                deviceId = request.queryParams("deviceId"),
                phoneNumber = request.queryParams("phoneNumber"),
                tokenIssuer = UrlTokenIssuer()
        )
    }
}
