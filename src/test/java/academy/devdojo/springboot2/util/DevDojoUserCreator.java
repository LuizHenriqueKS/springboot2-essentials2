package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.DevDojoUser;

public class DevDojoUserCreator {

    public final static String ENCRYPTED_PASSWORD = "{bcrypt}$2a$10$Ssgyi63qx2esF2f0ZvblNe.gpfWufa0zMeGkaeFKRPcTjUbNPQ1ea";
    public final static String DECRYPTED_PASSWORD = "academy";

    public static DevDojoUser createUserWithRoleAdmin() {
        return DevDojoUser
                .builder()
                .name("Luiz")
                .username("luiz")
                .password(ENCRYPTED_PASSWORD)
                .authorities("ROLE_USER,ROLE_ADMIN")
                .build();
    }

    public static DevDojoUser createUserWithRoleUser() {
        return DevDojoUser
                .builder()
                .name("DevDojo Academy")
                .username("devdojo")
                .password(ENCRYPTED_PASSWORD)
                .authorities("ROLE_USER")
                .build();
    }

}
