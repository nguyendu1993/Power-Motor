package com.example.asm;

public interface VolleyCallback<T> {
    void onReponse(T reponse);

    void onError(String err);
}
