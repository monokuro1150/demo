package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * =========================
 * Spring Bootアプリの起動クラス
 * =========================
 *
 * ・このクラスが「アプリの入口」
 * ・mainメソッドからアプリが起動する
 */
@SpringBootApplication
public class DemoApplication {

    /*
     * =========================
     * アプリ起動処理
     * =========================
     */
    public static void main(String[] args) {

        // Spring Bootアプリを起動する
        SpringApplication.run(DemoApplication.class, args);

    }

}