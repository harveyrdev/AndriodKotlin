package com.joaodev.googlekeep.Models;

public class Response {

    private String code;
    private String msj;
    private int status;
    private Object info;
    private boolean transaccionError;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public boolean isTransaccionError() {
        return transaccionError;
    }

    public void setTransaccionError(boolean transaccionError) {
        this.transaccionError = transaccionError;
    }
}
