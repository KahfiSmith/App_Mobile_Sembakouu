package com.example.sembakomobile.Model.Retrofit;

import java.util.List;

public class ResponseKeranjang {
        private byte kode;
        private String message;
        private List<DataKeranjang> data;

        public ResponseKeranjang(byte kode, String message, List<DataKeranjang> data) {
            this.kode = kode;
            this.message = message;
            this.data = data;
        }

        public byte getKode() {
            return kode;
        }

        public void setKode(byte kode) {
            this.kode = kode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<DataKeranjang> getData() {
            return data;
        }

        public void setData(List<DataKeranjang> data) {
            this.data = data;
        }

    }

