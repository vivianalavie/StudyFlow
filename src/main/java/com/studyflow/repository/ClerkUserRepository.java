package com.studyflow.repository;

import com.clerk.backend_api.Clerk;
import com.clerk.backend_api.Clerk.Builder;
import com.clerk.backend_api.models.errors.AuthException;
import com.clerk.backend_api.models.errors.ClerkErrors;
import com.clerk.backend_api.models.operations.CreateSignInTokenRequestBody;
import com.clerk.backend_api.models.operations.CreateSignInTokenResponse;
import com.clerk.backend_api.models.operations.CreateUserRequestBody;
import com.clerk.backend_api.models.operations.CreateUserResponse;
import com.studyflow.exception.SignupFlowException;
import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SignatureException;
import java.util.Collections;

public class ClerkUserRepository implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(ClerkUserRepository.class);
    private final Clerk clerk;

    public ClerkUserRepository() {
        String bearerToken = System.getenv("CLERK_TOKEN");
        if (bearerToken == null || bearerToken.isEmpty()) {
            throw new IllegalStateException("CLERK_TOKEN environment variable must be set.");
        }

        Builder clerkBuilder = Clerk.builder().bearerAuth(bearerToken);
        this.clerk = clerkBuilder.build();
    }

    @Override
    public AuthResponse signup(UserCredentialsModel user)
    {
        try {
            CreateUserRequestBody userCreationRequest = CreateUserRequestBody.builder()
                    .build()
                    .withEmailAddress(Collections.singletonList(user.email()))
                    .withPassword(user.password());

            CreateUserResponse userCreationResponse = clerk.users().create()
                    .request(userCreationRequest)
                    .call();

            System.out.println(userCreationResponse);
        }  catch (ClerkErrors clerkError) {
            String clerkErrorCode = clerkError.errors().get(0).code();

            log.error(clerkErrorCode);

            switch (clerkErrorCode) {
                case "form_password_pwned":
                    throw new SignupFlowException.PasswordLeaked();
                case "form_email_invalid":
                    throw new SignupFlowException.InvalidEmail();
                case "form_identifier_exists":
                    throw new SignupFlowException.UserExists();
                default:
                    throw new SignupFlowException.SignupError();
            }
        } catch (Exception e) {
            throw new SignupFlowException.SignupError();
        }
        return null;
    }

    @Override
    public void verifyEmail(String token) {

    }

    @Override
    public AuthResponse login(UserCredentialsModel user) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void requestPasswordReset(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }
}
