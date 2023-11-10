package com.demo.boo.service.customer.adaptor.`in`.web.dto

data class CreateCustomerRequest(
    val username: String,
    var password: String,
    var nickname: String,
    var name: String,
    var birth: String,
) {

}
