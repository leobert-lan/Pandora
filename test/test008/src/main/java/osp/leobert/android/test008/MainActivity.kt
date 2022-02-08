package osp.leobert.android.test008

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import osp.leobert.android.pandora.Pandora
import osp.leobert.android.pandora.rv.DataSet
import osp.leobert.android.pandora.rv.PandoraRealRvDataSet

class MainActivity : AppCompatActivity() {

    val dataSet:DataSet<DataSet.Data> = PandoraRealRvDataSet<DataSet.Data>(Pandora.real())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSet.getItem(0)
    }
}