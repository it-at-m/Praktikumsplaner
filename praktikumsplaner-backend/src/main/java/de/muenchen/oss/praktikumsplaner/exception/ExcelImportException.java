package de.muenchen.oss.praktikumsplaner.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class ExcelImportException extends RuntimeException {
    private final List<ExcelImportExceptionInfo> exceptionInfos;

    public record ExcelImportExceptionInfo(int row, String columName, String value) {
    }
}
