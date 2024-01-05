package com.example.l5_andro;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

val humanoids = arrayOf("Human", "NPC", "Orc")

@Entity(tableName = "item_table")
class DBItem {
    @PrimaryKey(autoGenerate=true)
    var id=0



    @ColumnInfo(name = "name")
    var text_name : String = "Default person name"

    @ColumnInfo(name = "specification")
    var text_spec : String = "Default specification"

    var item_strength : Float = Random.nextInt(0, 6).toFloat()
    var item_type : String = humanoids[Random.nextInt(0, 3)]
    var dangerous : Boolean = Random.nextBoolean()

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }


    constructor()

    constructor(num: Int) : this() {
        text_name = "Default person name "+num
        if (item_type=="Orc"){
            dangerous=true
        }
    }
    constructor(name: String, spec:String, strength:Float, type:String, danger:Boolean) : this() {
        text_name = name
        text_spec = spec
        item_strength = strength
        item_type = type
        dangerous = danger

    }

    constructor(iden:Int, name: String, spec:String, strength:Float, type:String, danger:Boolean) : this() {
        id = iden
        text_name = name
        text_spec = spec
        item_strength = strength
        item_type = type
        dangerous = danger

    }
}




