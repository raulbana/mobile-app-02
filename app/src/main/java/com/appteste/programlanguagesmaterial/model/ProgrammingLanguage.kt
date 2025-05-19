package com.appteste.programlanguagesmaterial.model

data class ProgrammingLanguage(
    val id: Int,
    val name: String,
    val typing: String,
    val paradigm: String,
    val learningCurve: String,
    val description: String,
    val imageUrl: Int?,
){
    override fun toString(): String {
        return name
    }
}
