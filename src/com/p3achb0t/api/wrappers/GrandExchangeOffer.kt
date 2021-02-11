package com.p3achb0t.api.wrappers

import com.p3achb0t.api.interfaces.GrandExchangeOffer
import kotlin.experimental.and


open class GrandExchangeOffer(var raw: GrandExchangeOffer, var index: Int) {
    /*
     Internally a GrandExchangeOffer's state is represented as 4 flags
     packed into the lower half of a byte. They are:
    */

    /*
		 Internally a GrandExchangeOffer's state is represented as 4 flags
		 packed into the lower half of a byte. They are:
	*/
    //Set for sell offers, unset for buy offers
    private val IS_SELLING = 1 shl 3 // 0b1000


    /*
	Set for offers that have finished, either because they've
	been filled, or because they were cancelled
	*/
    private val COMPLETED = 1 shl 2 // 0b0100


    /*
	Set for offers that are actually live
	NB: Insta-buy/sell offers will be simultaneously LIVE and LOCAL
	*/
    private val LIVE = 1 shl 1 // 0b0010


    //True for just-made, just-cancelled, completely cancelled, and completed offers
    private val LOCAL = 1
    open fun getState(): GrandExchangeOfferState? {
        val code: Byte = raw.getState()
        val isSelling = (code and IS_SELLING.toByte()).toInt() == IS_SELLING
        val isFinished = (code and COMPLETED.toByte()).toInt() == COMPLETED
        return if (code.toInt() == 0) {
            GrandExchangeOfferState.EMPTY
        } else if (isFinished && raw.getCurrentQuantity() < raw.getTotalQuantity()) {
            if (isSelling) GrandExchangeOfferState.CANCELLED_SELL else GrandExchangeOfferState.CANCELLED_BUY
        } else if (isSelling) {
            if (isFinished) {
                GrandExchangeOfferState.SOLD
            } else  // if isUnfinished
            {
                GrandExchangeOfferState.SELLING
            }
        } else  // if isBuying
        {
            if (isFinished) {
                GrandExchangeOfferState.BOUGHT
            } else  // if isUnfinished
            {
                GrandExchangeOfferState.BUYING
            }
        }
    }
}