package parsing_excel.models

data class ScenarioStep(
    val time: Int,
    val values: ArrayList<Int>,
    val text: String,
    val comment: String = ""
)
