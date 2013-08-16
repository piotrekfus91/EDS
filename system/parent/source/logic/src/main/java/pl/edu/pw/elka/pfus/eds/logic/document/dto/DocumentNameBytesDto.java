package pl.edu.pw.elka.pfus.eds.logic.document.dto;

public class DocumentNameBytesDto {
    private String name;
    private byte[] bytes;

    public DocumentNameBytesDto(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
