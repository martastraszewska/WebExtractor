package pl.straszewska.auction.controller;

public class IncorrectUrlException extends Exception {
    public IncorrectUrlException(String msg) {
        super(msg);
    }
}
