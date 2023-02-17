package storage.models

import kotlinx.serialization.Serializable

@Serializable
data class ParamComm(
    val parcom : ArrayList<ParameterCommon>
)

@Serializable
data class ParameterCommon(
    val name: String,
    val value: String,
    val flag: String? = null,
    val numeric: Int? = null
)
