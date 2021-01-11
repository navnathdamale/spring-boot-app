package com.springsecurity.datatransferobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * This class is required for storing the username and password we recieve from the client.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTRequestDTO implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
}
