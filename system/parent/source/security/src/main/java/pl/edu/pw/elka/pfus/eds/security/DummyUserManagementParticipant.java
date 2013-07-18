package pl.edu.pw.elka.pfus.eds.security;

import org.objectledge.authentication.UserAlreadyExistsException;
import org.objectledge.authentication.UserInUseException;
import org.objectledge.authentication.UserManagementParticipant;
import org.objectledge.authentication.UserUnknownException;

import java.security.Principal;

// TODO DummyUserManagementParticipant
public class DummyUserManagementParticipant implements UserManagementParticipant {
    @Override
    public boolean supportsRemoval() {
        return false;
    }

    @Override
    public void createAccount(Principal user) throws UserAlreadyExistsException {
    }

    @Override
    public void removeAccount(Principal user) throws UserUnknownException, UserInUseException {
    }
}
