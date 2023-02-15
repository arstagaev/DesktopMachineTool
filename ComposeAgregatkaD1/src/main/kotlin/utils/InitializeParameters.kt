package utils

import storage.models.ParameterCommon

fun initialize(params: MutableList<ParameterCommon>) {
    logAct("initialize params")
    params.forEachIndexed { index, parameterCommon ->
        when(parameterCommon.name) {
            "comport" -> COM_PORT = parameterCommon.value
            "baudrate" -> BAUD_RATE = parameterCommon.numeric ?: 115200
            //"is_demo" ->
            "last_operator_id" -> OPERATOR_ID = parameterCommon.value
        }
    }
}