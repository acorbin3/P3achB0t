package com.p3achb0t.client.util

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


class SMS(accountSID: String, authToken: String) {
    init {
        Twilio.init(accountSID, authToken)
    }

    fun sendSMS(text: String, from: String, to: String) {
        val message: Message = Message.creator(PhoneNumber(to), PhoneNumber(from), text).create()
        System.out.println(message.getSid())
    }
}