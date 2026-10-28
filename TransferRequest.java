package com.example.demo.dto;

public class TransferRequest {


        private int fromCustomerId;
        private int toCustomerId;
        private double amount;

        public TransferRequest() {
        }

        public int getFromCustomerId() {
            return fromCustomerId;
        }

        public void setFromCustomerId(int fromCustomerId) {
            this.fromCustomerId = fromCustomerId;
        }

        public int getToCustomerId() {
            return toCustomerId;
        }

        public void setToCustomerId(int toCustomerId) {
            this.toCustomerId = toCustomerId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

