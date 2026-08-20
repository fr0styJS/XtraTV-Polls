package com.github.andreyasadchy.xtra.model.gql.chat

import com.github.andreyasadchy.xtra.model.gql.Error
import kotlinx.serialization.Serializable

@Serializable
class ChannelPointRewardsResponse(
    val errors: List<Error>? = null,
    val data: Data? = null,
) {
    @Serializable
    class Data(
        val community: Community? = null,
    )

    @Serializable
    class Community(
        val channel: Channel? = null,
    )

    @Serializable
    class Channel(
        val self: Self? = null,
        val communityPointsSettings: CommunityPointsSettings? = null,
    )

    @Serializable
    class Self(
        val communityPoints: Points? = null,
    )

    @Serializable
    class Points(
        val balance: Int? = null,
    )

    @Serializable
    class CommunityPointsSettings(
        val customRewards: List<ChannelPointCustomReward>? = null,
    )
}

@Serializable
class ChannelPointCustomReward(
    val id: String? = null,
    val title: String? = null,
    val prompt: String? = null,
    val pricingType: String? = null,
    val cost: Int? = null,
    val isEnabled: Boolean? = null,
    val isPaused: Boolean? = null,
    val isInStock: Boolean? = null,
    val isUserInputRequired: Boolean? = null,
)
