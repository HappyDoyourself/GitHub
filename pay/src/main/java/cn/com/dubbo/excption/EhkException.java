package cn.com.dubbo.excption;

/**
 * Created by fanhongtao
 * Date 2017-01-11 18:21
 */
public class EhkException extends  RuntimeException {

    private String errMessage;


    public EhkException(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "EhkException{" +
                "errMessage='" + errMessage + '\'' +
                '}';
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {

        return errMessage;
    }
}

