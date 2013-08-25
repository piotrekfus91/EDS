package pl.edu.pw.elka.pfus.eds.logic.validator;

import pl.edu.pw.elka.pfus.eds.domain.entity.*;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidMimeTypeException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;

public class LogicValidator {
    private LogicValidator() {

    }

    public static boolean isMoveToSameDirectory(int destinationDirectoryId, Directory sourceDirectory) {
        return sourceDirectory.getId().equals(destinationDirectoryId);
    }

    public static void validateOwnershipOverDocument(User currentUser, Document document) {
        if(!currentUser.isOwnerOfDocument(document))
            throw new InvalidPrivilegesException();
    }

    public static void validateOwnershipOverDirectory(User currentUser, Directory directory) {
        if(!currentUser.isOwnerOfDirectory(directory))
            throw new InvalidPrivilegesException();
    }

    public static void validateOwnershipOverResourceGroup(User currentUser, ResourceGroup resourceGroup) {
        if(!currentUser.equals(resourceGroup.getFounder()))
            throw new InvalidPrivilegesException();
    }

    public static void validateExistence(Object object) {
        if(object == null)
            throw new ObjectNotFoundException();
    }

    public static void validateMimeTypeEnabled(MimeType mimeType) {
        if(mimeType == null || !mimeType.isEnabled())
            throw new InvalidMimeTypeException();
    }

    public static boolean isFileWithNameInDirectory(Directory directory, String name) {
        for(Document document : directory.getDocuments()) {
            if(name.equals(document.getName()))
                return true;
        }
        return false;
    }
}
