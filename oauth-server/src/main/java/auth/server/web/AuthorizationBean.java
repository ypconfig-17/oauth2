package auth.server.web;

import lombok.Data;

/**
 * @className: AuthorizationBean
 * @description:
 * @author: yang pan
 */
@Data
public class AuthorizationBean {

    private String  id;
    private String  registeredClientId;
    private String  principalName;
    private String  authorizationGrantType;
    private String  attributes;
    private String  state;
}
