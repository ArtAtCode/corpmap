package edu.scu.corpmap.exception;

/**
 * Created by Vicent_Chen on 2018/6/20.
 */
public class HotCorpException extends RuntimeException {
    public static final String EMPTY_MESSAGE = "公司对象为空";
    public static final String EMPTY_ID_MESSAGE = "ID为空";
    public static final String EMPTY_NAME_MESSAEG = "名称为空";
    public static final String UNKNOWN_ERROR_MESSAGE = "未知错误";

    public HotCorpException() {
        super(UNKNOWN_ERROR_MESSAGE);
    }

    public HotCorpException(String message) {
        super(message);
    }
}
