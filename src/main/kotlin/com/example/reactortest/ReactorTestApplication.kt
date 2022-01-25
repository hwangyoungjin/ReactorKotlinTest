package com.example.reactortest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactorTestApplication

fun main(args: Array<String>) {
	runApplication<ReactorTestApplication>(*args){
	}
}
