package com.studyflow.config;

import com.clerk.backend_api.Clerk;
import com.clerk.backend_api.models.operations.GetClientResponse;
import com.clerk.backend_api.models.operations.GetUserListResponse;

public class TestClerk {
    public static void main(String[] args) throws Exception {
        Clerk client = Clerk.builder().bearerAuth("sk_test_BDUCDr4RCDkNYC4dvPYwgZObioovOS2TbhHMJPMoTM").build();
        System.out.println("SDK erkannt!");
        GetUserListResponse res = client.users().list().call();
        System.out.println(res);
    }
}
