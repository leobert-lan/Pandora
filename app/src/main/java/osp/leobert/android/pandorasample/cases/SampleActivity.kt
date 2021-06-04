package osp.leobert.android.pandorasample.cases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import osp.leobert.android.pandora.Pandora
import osp.leobert.android.pandora.rv.DataSet
import osp.leobert.android.pandora.rv.PandoraRealRvDataSet
import osp.leobert.android.pandorasample.R
import osp.leobert.android.pandorasample.RvAdapter
import osp.leobert.android.pandorasample.widget.CollectionsVHCreator
import osp.leobert.android.pandorasample.widget.CollectionsVO2

class SampleActivity : AppCompatActivity() {

    private val dataSet by lazy { PandoraRealRvDataSet<DataSet.Data<*, *>>(Pandora.real()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)


        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        initDataSet()

        val adapter = RvAdapter<PandoraRealRvDataSet<DataSet.Data<*, *>>>(dataSet)
        Pandora.bind2RecyclerViewAdapter(dataSet.getRealDataSet(), adapter)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        decoration.setDrawable(resources.getDrawable(R.color.colorAccent))
        recyclerView.addItemDecoration(decoration)
    }

    private fun initDataSet() {
        dataSet.registerDVRelation(CollectionsVO2.Impl::class.java, CollectionsVHCreator(
                itemInteract = null
        ))

        Pandora.addAll(dataSet.getRealDataSet(), arrayListOf(
                CollectionsVO2.Impl(),
                CollectionsVO2.Impl(),
                CollectionsVO2.Impl(),
                CollectionsVO2.Impl()
        ))
    }
}