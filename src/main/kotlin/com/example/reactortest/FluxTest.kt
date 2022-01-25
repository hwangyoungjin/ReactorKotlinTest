package com.example.reactortest

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class FluxTest : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
//        val flux = text1()
//        flux.subscribe()
//        val flux3 = text3()
//        flux3.subscribe()
//        val mono2 = text2()
//        mono2.subscribe()
        text4().subscribe()
        text5().subscribe()
    }

    fun text1(): Flux<Int> {
        return Flux.range(1, 5)
            .doOnNext { println("intput: $it") } // 1,2,3,4,5 출력된
            .map { it * 2 }
            .doOnNext { println("next Input: $it") } // 2, 4, 6, 8, 10 출력된
    }

    fun text2(): Mono<Int> {
        return Flux.range(1, 5)
            .doOnNext { println("intput: $it") } // 1,2,3,4,5 출력된
            .map { it * 2 }
            // 컬랙션 내의 데이터를 모두 모으는(accumulate) 함수인 reduce(), Return 값은 Mono<>
            .reduce { total, num -> total + num } // total은 계속 더해지는 값이고 num은 하나씩 index를 조회한다.
            // ->   1(total)+2(num),
            //      3(total)+3(num),
            //      6(total)+4(num),
            //      10(total)+5(num) => 15라는 값이 된다.
            .doOnNext { println("total: $it") }
    }

    fun text3(): Flux<Int> {
        return Flux.range(1, 5)
            .doOnNext { println("intput: $it") } // 1,2,3,4,5 출력된다
            .map { if (it % 2 == 0) 0 else 100 }
            .map { it * 2 }
            .doOnNext { println("next Input: $it") } // 바뀐 값에 대해서 200, 0, 200, 0, 200 출력된다
    }

    /**Throw Exception**/
    fun text4(): Flux<Int> {
        return Flux.range(1, 5)
            .doOnNext { println("intput: $it") }
            .map { if (it % 2 == 0) (it / 0) else 100 }
            .map { it * 2 }
            .doOnNext { println("next Input: $it") }
    }

    fun text5(): Flux<Int> {
        return Flux.range(1, 5)
            .doOnNext { println("intput: $it") } // 1,2,3,4,5 출력된다
            .map { if (it % 2 == 0) (it / 0) else 100 }
            .onErrorContinue { t, u -> println("onErrorContinue= $t") }
            .map { it * 2 }
            .doOnNext { println("next Input: $it") } // 바뀐 값에 대해서 200, Exception, 200, Exception, 200 출력된다
    }
}
