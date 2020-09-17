package com.thinkerwolf.dtx.common;

import java.io.Serializable;

public class OpResult implements Serializable {

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

    /**
     * 操作是否成功
     *
     * @return true or false
     */
    public boolean suc() {
        return this.state == 1;
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
