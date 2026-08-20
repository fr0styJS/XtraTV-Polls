package com.github.andreyasadchy.xtra.model.gql.chat

import com.github.andreyasadchy.xtra.model.gql.Error
import kotlinx.serialization.Serializable

@Serializable
class VoteInPollResponse(
    val errors: List<Error>? = null,
    val data: Data? = null,
) {
    @Serializable
    class Data(
        val voteInPoll: VoteInPoll? = null,
    )

    @Serializable
    class VoteInPoll(
        val error: PayloadError? = null,
    )
}

@Serializable
class MakePredictionResponse(
    val errors: List<Error>? = null,
    val data: Data? = null,
) {
    @Serializable
    class Data(
        val makePrediction: MakePrediction? = null,
    )

    @Serializable
    class MakePrediction(
        val error: PayloadError? = null,
    )
}

@Serializable
class RedeemCustomRewardResponse(
    val errors: List<Error>? = null,
    val data: Data? = null,
) {
    @Serializable
    class Data(
        val redeemCommunityPointsCustomReward: RedeemCustomReward? = null,
    )

    @Serializable
    class RedeemCustomReward(
        val error: PayloadError? = null,
        val balance: Int? = null,
    )
}

@Serializable
class PayloadError(
    val code: String? = null,
    val message: String? = null,
)
