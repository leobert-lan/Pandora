package osp.leobert.android.pandorasample.kt

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import osp.leobert.android.pandora.Pandora
import osp.leobert.android.pandorasample.R
import osp.leobert.androidkt.pandora.rv.DataSet
import osp.leobert.androidkt.pandora.rv.PandoraRealRvDataSet

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.kt </p>
 * <p><b>Project:</b> Pandora </p>
 * <p><b>Classname:</b> TestKt </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2019/2/19.
 */
class TestKtActivity : Activity() {

    lateinit var dataSet: PandoraRealRvDataSet<DataSet.Data<Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)

        dataSet = PandoraRealRvDataSet(Pandora.real())
        val adapter = KtRvAdapter(dataSet, javaClass.simpleName)
        dataSet.registerDVRelation(TestKtVO.Impl1::class.java, TestKtVH.Creator(null))
        dataSet.registerDVRelation(TestKtVO.Impl2::class.java, TestKtVH.Creator(null))
        Pandora.bind2RecyclerViewAdapter(dataSet.getRealDataSet(), adapter)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        decoration.setDrawable(resources.getDrawable(R.color.colorAccent))
        recyclerView.addItemDecoration(decoration)

        findViewById<Button>(R.id.b1).setOnClickListener { addSection1() }

        findViewById<Button>(R.id.b2).setOnClickListener { addSection2() }

        findViewById<Button>(R.id.b3).setOnClickListener { addSection3() }

    }

    private fun addSection3() {
        dataSet.add(TestKtVO.Impl1() as DataSet.Data<Any>)
        dataSet.add(TestKtVO.Impl2() as DataSet.Data<Any>)
    }

    private fun addSection2() {
        dataSet.add(TestKtVO.Impl1() as DataSet.Data<Any>)
        dataSet.add(TestKtVO.Impl2() as DataSet.Data<Any>)

    }

    private fun addSection1() {
        dataSet.add(TestKtVO.Impl1() as DataSet.Data<Any>)
        dataSet.add(TestKtVO.Impl2() as DataSet.Data<Any>)
    }

}