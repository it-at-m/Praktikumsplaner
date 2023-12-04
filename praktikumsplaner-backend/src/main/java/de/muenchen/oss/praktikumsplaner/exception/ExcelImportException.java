package de.muenchen.oss.praktikumsplaner.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
public class ExcelImportException extends RuntimeException {
    private final List<ExcelImportExceptionInfo> exceptionInfos;

    @AllArgsConstructor
    @Getter
    @ToString
    public static class ExcelImportExceptionInfo {
        private final int row;
        private final String columName;
        private final String value;
    }
}
