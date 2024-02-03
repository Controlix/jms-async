package be.ict.mb.jmsasync

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "rss-hungry-svc")
interface ResourceClient {

    @GetMapping("/stats/{id}")
    fun findStatsForMessage(@PathVariable("id") id: String?, @RequestParam(name = "safe") safe: Boolean = true): List<Int>?
}