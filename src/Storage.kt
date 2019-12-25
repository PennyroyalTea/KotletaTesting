package com.kotletaTesting

object Storage {
    public val problems : MutableMap<Int, Problem> = mutableMapOf()

    private fun getUnusedId() : Int {
        for (i in 0..Integer.MAX_VALUE) {
            if (!problems.containsKey(i)) return i
        }
        return -1
    }

    fun addProblem(title : String, description : String) {
        val id = getUnusedId()
        problems.put(id, Problem(id, title, description))
    }
}