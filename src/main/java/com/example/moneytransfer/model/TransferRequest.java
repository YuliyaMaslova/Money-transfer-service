package com.example.money_transfer_service.model;

public class TransferRequest {

    private String cardFromNumber;
    private String ardFromValidTil;
    private String cardFromCVV;
    private String cardToNumber;

    private Amount amount;

    public TransferRequest() {

    }

    public TransferRequest(String cardFromNumber, String ardFromValidTil, String cardFromCVV, String cardToNumber, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.ardFromValidTil = ardFromValidTil;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }

    public String getArdFromValidTil() {
        return ardFromValidTil;
    }

    public void setArdFromValidTil(String ardFromValidTil) {
        this.ardFromValidTil = ardFromValidTil;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public void setCardFromCVV(String cardFromCVV) {
        this.cardFromCVV = cardFromCVV;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
