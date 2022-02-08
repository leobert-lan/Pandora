package osp.leobert.android.pandora.rv

import org.junit.Before
import org.junit.Test
import osp.leobert.android.pandora.Pandora

/**
 *
 * **Package:** osp.leobert.android.pandora.rv
 *
 * **Project:** Pandora
 *
 * **Classname:** PandoraRealRvDataSetTest
 *
 * **Description:** TODO
 * Created by leobert on 2022/2/8.
 */
class PandoraRealRvDataSetTest {
    private var dataSet: DataSet<DataSet.Data>? = null

    internal class Item : DataSet.Data {
        override fun setToViewHolder(viewHolder: IViewHolder<DataSet.Data?>) {
            //ignore
        }
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val dataSet = PandoraRealRvDataSet(Pandora.real())
        dataSet.add(Item())
        this.dataSet = dataSet
    }

    @Test
    fun getItem() {
        assert(dataSet!!.getItem(0) is Item)
    }
}