package de.muenchen.oss.praktikumsplaner.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
