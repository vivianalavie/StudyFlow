package com.studyflow.repository;

import com.clerk.backend_api.Clerk;
import com.clerk.backend_api.Clerk.Builder;
import com.clerk.backend_api.models.components.User;
import com.clerk.backend_api.models.errors.ClerkErrors;
import com.clerk.backend_api.models.operations.*;
import com.studyflow.exception.SignupFlowException;
import com.studyflow.model.auth.SignUpResponse;
import com.studyflow.model.auth.UserCredentialsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public SignUpResponse signup(UserCredentialsModel user)
    {
        try {
            CreateUserRequestBody userCreationRequest = CreateUserRequestBody.builder()
                    .build()
                    .withEmailAddress(Collections.singletonList(user.email()))
                    .withPassword(user.password());

            CreateUserResponse userCreationResponse = clerk.users().create()
                    .request(userCreationRequest)
                    .call();

            log.info(userCreationResponse.user().toString());

            return new SignUpResponse(
                    userCreationResponse.user().flatMap(User::id)
            );

        }  catch (ClerkErrors clerkError) {
            String clerkErrorCode = clerkError.errors().get(0).code();

            log.error(clerkErrorCode);
            throw switch (clerkErrorCode) {
                case "form_password_pwned" -> new SignupFlowException.PasswordLeaked();
                case "form_email_invalid" -> new SignupFlowException.InvalidEmail();
                case "form_identifier_exists" -> new SignupFlowException.UserExists();
                case "form_password_length_too_short" -> new SignupFlowException.PasswordTooShort();
                default -> new SignupFlowException.SignupError();
            };
        } catch (Exception e) {
            throw new SignupFlowException.SignupError();
        }
    }



    @Override
    public void deleteUser(String userId) {
        try {
            DeleteUserResponse deleteUserResponse = clerk.users().delete()
                    .userId(userId)
                    .call();

            log.info(deleteUserResponse.toString());

        } catch (ClerkErrors clerkErrors) {
            if (clerkErrors.errors().get(0).code().equals("resource_not_found")) {
                log.error("User not found");
            } else {
                log.error("Unknown error");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void getJWKS() {

    }

}
