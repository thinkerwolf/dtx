package com.thinkerwolf.dtx.common;

public class OpResult {

    private int state;

    private String msg;

    private Object data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static OpResult ok() {
        OpResult op = new OpResult();
        op.setState(1);
        op.setMsg("");
        op.setData("");
        return op;
    }

    public static OpResult ok(Object data) {
        OpResult op = new OpResult();
        op.setState(1);
        op.setMsg("");
        op.setData(data);
        return op;
    }

    public static OpResult fail(String msg) {
        OpResult op = new OpResult();
        op.setState(-1);
        op.setMsg(msg);
        op.setData("");
        return op;
    }
}
