package com.alvaro.alvarolab2_2.model

class todomodel {
    private var task: String? = null
    private var id = 0
    private var status = 0

    fun getTask(): String? {
        return task
    }

    fun setTask(task: String?) {
        this.task = task
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getStatus(): Int {
        return status
    }

    fun setStatus(status: Int) {
        this.status = status
    }
}