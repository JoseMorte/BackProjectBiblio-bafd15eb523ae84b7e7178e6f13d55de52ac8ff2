
package net.ausiasmarch.projectBiblio.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String mensaje) {
        super(mensaje);
    }
}
