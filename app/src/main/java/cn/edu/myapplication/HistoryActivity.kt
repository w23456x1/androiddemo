package cn.edu.myapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_history.*
import java.util.ArrayList

private val contactsList = ArrayList<String>()
private lateinit var adapter: ArrayAdapter<String>

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, contactsList)
        contactsView.adapter = adapter
        contactsList.clear()
        readHistory()
    }
    private fun readHistory() {
        val uri = Uri.parse("content://cn.edu.myapplication.provider/history")
        val cursor = contentResolver.query(uri,null,null,null,null)
        cursor?.apply {
            while (moveToNext()) {
                val num1 = getString(getColumnIndex("num1"))
                val add="+"
                val num2 = getString(getColumnIndex("num2"))
                val equal="="
                var result = getString(getColumnIndex("result"))
                Log.d("Result", "num1 = $num1 ,num2 = $num2,result = $result")
                contactsList.add("$num1 $add $num2 $equal $result")
                Log.d("长度","${contactsList.size}")
                for (i in 1 .. (contactsList.size-2)){
                    if(contactsList[i].equals(contactsList[contactsList.size-1])){
                        contactsList.removeAt(contactsList.size-1)
                    }
                }
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }
}
