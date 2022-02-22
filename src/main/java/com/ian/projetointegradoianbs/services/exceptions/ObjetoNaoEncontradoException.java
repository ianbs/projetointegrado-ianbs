package com.ian.projetointegradoianbs.services.exceptions;

public class ObjetoNaoEncontradoException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public ObjetoNaoEncontradoException(String msg) {
        super(msg);
    }

    public ObjetoNaoEncontradoException(String msg, Throwable causeThrowable) {
        super(msg, causeThrowable);
    }
}
