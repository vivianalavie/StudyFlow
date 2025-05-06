package com.studyflow.repository;

import com.clerk.backend_api.Clerk;
import com.clerk.backend_api.Clerk.Builder;
import com.clerk.backend_api.models.operations.CreateSignInTokenRequestBody;
import com.clerk.backend_api.models.operations.CreateSignInTokenResponse;
import com.clerk.backend_api.models.operations.CreateUserRequestBody;
import com.clerk.backend_api.models.operations.CreateUserResponse;
import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;

import java.util.Collections;

public class ClerkUserRepository implements UserRepository {

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
    public AuthResponse signup(UserCredentialsModel user)  {
        CreateUserRequestBody req = CreateUserRequestBody.builder( )
                .build().withEmailAddress(Collections.singletonList(user.email())).withPassword(user.password());

        try {
            CreateUserResponse res = clerk.users().create()
                    .request(req)
                    .call();

            if (res.user().isPresent()) {
                CreateSignInTokenRequestBody accessTokenReq = CreateSignInTokenRequestBody.builder()
                        .userId(res.user().get().id().get())
                        .build();

                CreateSignInTokenResponse accessTokenRes = clerk.signInTokens().create()
                        .request(accessTokenReq)
                        .call();

                System.out.println(accessTokenRes);

            } else {
                return null;
            }

            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }




        return null;
    }

    @Override
    public AuthResponse login(UserCredentialsModel user) {
        return null;
    }
}
